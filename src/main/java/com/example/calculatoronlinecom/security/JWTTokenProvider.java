package com.example.calculatoronlinecom.security;

import com.example.calculatoronlinecom.entity.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTTokenProvider {
    public static final Logger LOG = LoggerFactory.getLogger(JWTTokenProvider.class);

    public String generateToken(Authentication authentication){         //Здесь генерируется токен для входа на сайт
        User user = (User) authentication.getPrincipal();               //в котором есть конкретный пользователь
        Date now = new Date(System.currentTimeMillis());                //текущая дата для создания токена
        Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);          //Время истечения токена
        String userId = Long.toString(user.getId());

        Map<String, Object> claimsMap = new HashMap<>();    //создается новая хашмапа, в которую добавляются поля ниже
        claimsMap.put("id", userId);
        claimsMap.put("username", user.getEmail());
        claimsMap.put("firstname", user.getName());
        claimsMap.put("lastname", user.getLastname());

        return Jwts.builder()                           //Здесь строится и шифруется токен исходя из наших данных и хешмапы сверху
                .setSubject(userId)
                .addClaims(claimsMap)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)   //алгоритм ассиметричного шифрования, можно использовать  HS512, RS512, ES256, ES512
                .compact();
    }
    public boolean validateToken(String token){                 //здесь проверяется полученный токен от пользователя, если не ок - выдает ошибку в лог
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token);
            return true;
        }catch (SignatureException |
                MalformedJwtException |
                ExpiredJwtException |
                UnsupportedJwtException|
                IllegalArgumentException ex) {
    LOG.error(ex.getMessage());
    return false;
        }

    }
    public Long getUserIdFromToken(String token){           //здесь мы получаем токен юзера и извлекаем из него необходимые нам данные, которые будем использовать в дальнейшем
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();
        String id = (String) claims.get("id");              //но здесь нам почему-то нужен только айдишник, который видимо будет куда-то выводиться, например в окно успешной авторизации
        return Long.parseLong(id);

    }
}
