package ru.alexandernosevich.springoauthhw.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandernosevich.springoauthhw.entity.User;
import ru.alexandernosevich.springoauthhw.enums.Role;
import ru.alexandernosevich.springoauthhw.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class SocialAppService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(SocialAppService.class);

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        //делаем так, чтобы спринг сам загружал данные
        OAuth2User oAuth2User = super.loadUser(userRequest);

        //получение логика из БД гита
        String login = oAuth2User.getAttribute("login");

        User user = userRepository.findByLogin(login)
                .orElseGet(()->
                {
                    User newUser = new User();
                    newUser.setLogin(login);

                    //избегаем null, тк юзернейм может быть не заполнен на гите
                    String name = oAuth2User.getAttribute("name");
                    newUser.setName(name != null ? name : login);

                    //та же история с почтой, может быть скрыта
                    String email = oAuth2User.getAttribute("email");
                    newUser.setEmail(email != null ? email : "");

                    //Если вдруг в БД пусто, то первый юзер станет админом
                    boolean isFirstUser = userRepository.count() == 0;
                    newUser.setRole(isFirstUser ? Role.ADMIN : Role.USER);

                    logger.info("ЗАрегистрирован пользователь: " +
                            "login={}, role={}", login, newUser.getRole());
                    return userRepository.save(newUser);
                });
        logger.info("Вход на сайт: login={}, role={}", login, user.getRole());
        return oAuth2User;
    }
}
