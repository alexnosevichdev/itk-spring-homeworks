package ru.alexnosevichdev.springhomeworkjwt.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import ru.alexnosevichdev.springhomeworkjwt.dto.request.LoginRequest;
import ru.alexnosevichdev.springhomeworkjwt.dto.request.RegisterRequest;
import ru.alexnosevichdev.springhomeworkjwt.dto.response.AuthResponse;
import ru.alexnosevichdev.springhomeworkjwt.dto.response.UserDto;
import ru.alexnosevichdev.springhomeworkjwt.entity.User;
import ru.alexnosevichdev.springhomeworkjwt.repository.UserRepository;
import ru.alexnosevichdev.springhomeworkjwt.utils.JWTUtils;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    //Ограничение попыток входа в 5 раз
    private static final int MAX_FAILED_ATTEMPTS = 5;

    //Регистрация
    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())

                .build();

        userRepository.save(user);

        //логирование факта регистрации
        logger.info("Зарегистрирован пользователь: {}", user.getUsername());

        //Генерация токенов и возврат их клиенту
        return buildAuthResponse(user);
    }

    //Вход
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Пользователь " + request.username() + " не найден"
                ));
        if(!user.isAccountNonExpired()) {
            logger.warn("Пытался войти заблокированный пользователь " + request.username());
            throw new LockedException("Аккаунт забанен");
        }

        //Проверка пароля и логина
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );
        } catch (BadCredentialsException e) {

            //увеличиваем счетчик
            handleFailedLogin(user);
            throw e;
        }

        //при успешном вводе сбрасываем счетчик
        user.setShitLoginAttempts(0);
        userRepository.save(user);

        logger.info("Успешный вход: {}", user.getUsername());

        return buildAuthResponse(user);
    }

    //Получение/отображение профиля
    public UserDto getProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " +
                        username + " не найден"));

        return new UserDto(user.getId(), user.getUsername(), user.getRole());
    }

    //Опция снятия бана для СУперАдмина
    public void unlockAccount(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        user.setAccountNonLocked(true);
        user.setShitLoginAttempts(0);
        userRepository.save(user);

        logger.info("Пользователь разбанен");
    }

    private void handleFailedLogin(User user) {
        int attempts = user.getShitLoginAttempts() + 1;
        user.setShitLoginAttempts(attempts);

        logger.warn("Неудачная попытка входа #{} для: {}", attempts, user.getUsername());

        if(attempts >= MAX_FAILED_ATTEMPTS){
            user.setAccountNonLocked(false);
            logger.warn("Аккаунт заблокирован после {} gопыток входа",
                    attempts, user.getUsername());
        }
        userRepository.save(user);
    }

    private AuthResponse buildAuthResponse(User user) {
        String token = jwtUtils.generateToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

        return new AuthResponse(token, refreshToken);
    }
}
