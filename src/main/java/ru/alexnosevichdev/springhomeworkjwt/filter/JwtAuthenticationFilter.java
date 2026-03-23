package ru.alexnosevichdev.springhomeworkjwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.alexnosevichdev.springhomeworkjwt.service.OurUserDetailedService;
import ru.alexnosevichdev.springhomeworkjwt.utils.JWTUtils;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;
    private final OurUserDetailedService ourUserDetailedService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
            ) throws ServletException, IOException {

        //Выхватываем заголовок от клиента - иначе если нет = нет токена
        final String authHeader = request.getHeader("Authorization");

        //Чек на то, начинается ли заголовок с "Bearer "
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //Иначе выхватываем токен из заголовка (Bearer длиной 7 символов)
        final String token = authHeader.substring(7);

        //Получаем username из токена
        final String username = jwtUtils.extractUsername(token);

        //Защита от повторной аутентификации
        if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //загрузка данных юзера из БД
            UserDetails userDetails = ourUserDetailedService.loadUserByUsername(username);

            //Проверка срока действия токена и валидности юзернейма
            if(jwtUtils.isTokenValid(token, userDetails)) {

                //создание нового объекта аутентификации
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                //Добавление деталей запроса (Айпишник, АйДи сессии)
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                //Добавление аутентификации в контекст, чтобы спринг знал, от кого поступил запрос
                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        // передача запроса дальше по цепи
        filterChain.doFilter(request, response);
    }
}
