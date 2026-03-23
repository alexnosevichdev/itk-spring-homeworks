package ru.alexnosevichdev.springhomeworkjwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class LoggingFilter extends OncePerRequestFilter {

    //Создние логгера
    private static Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        //Логирование входящего запроса до обработки
        logger.info("Запрос: {}  {}", request.getMethod(), request.getRequestURI());

        //Передача запроса дальше по цепи
        filterChain.doFilter(request, response);

        //Логирование ответа
        logger.info("Ответ: {}", response.getStatus());
    }
}
