package ru.alexnosevichdev.springhomeworkjwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JWTUtils {

    //ключ для подписи токена
    private SecretKey secretKey;

    //Время действия = 24ч в милисекунжах
    private static final long EXPIRATION_TIME = 86400000L;

    //Конструктор будет вызыван единожды при старте приложения, так как Компонент
    public JWTUtils() {
        //Лучше не хардкод, а в properties писать. Длина минимум 32
        String secretString = "alexnosevichdevartemnosevichismysonoksananosevichismywife";

        //Кодировка в Base64 (из байтов в текст)
        String base64Encoded = Base64.getEncoder().encodeToString(secretString.getBytes());

        //Обратно декод в массив байтов
        byte[] keyBytes = Decoders.BASE64.decode(base64Encoded);

        //Создание крипографтчичесного ключа из байтов для подписи
        //и проверки токенов
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    //Генерация JWT-токена (жизнь - 24 ч)
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                //Установка владельца токена
                .setSubject(userDetails.getUsername())

                //Время выдачи
                .setIssuedAt(new Date(System.currentTimeMillis()))

                //Время гибели токена
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))

                //Подпись токена секретным ключом
                .signWith(secretKey)

                //Преобразуем всю кашу в нормальную строку ааа.ббб.ввв
                .compact();
    }

    //Генерация токена обновления (жизнь - 7 дн)
    //claims - сюда запихну доп данные, которые можем закинуть в ключ
    //например - роль, статус аккаунта и тд
    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                //Добавление доп полей
                .setClaims(claims)

                //Установка владельца токена
                .setSubject(userDetails.getUsername())

                //Время выдачи
                .setIssuedAt(new Date(System.currentTimeMillis()))

                //Время гибели( 7дн = 7*24*3600)
                .setExpiration(new Date(System.currentTimeMillis() + 604800000L))

                //Подпись секретным ключом
                .signWith(secretKey)

                //Норм отображение
                .compact();
    }

    //Извлечение юзернейма из токена
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    //Извлечение любого поля из токена
    private <T>T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(
                Jwts.parserBuilder()

                //Установка ключа проверки
                .setSigningKey(secretKey)
                        .build()

                //Парсинг токена. Если подпись неверна - исключение
                        .parseClaimsJwt(token)

                //Получение тела токена
                        .getBody()
        );
    }

    //Проверка токена на валдиность для конкретного юзера
    //Принцип: username в токене совпадает с username в БД,
    // токен не истек
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername())
        && !isTokenExpired(token);
    }

    //Проверка на срок годности токена
    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
