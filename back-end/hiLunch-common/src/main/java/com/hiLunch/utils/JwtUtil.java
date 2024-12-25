package com.hiLunch.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * jwtを生成
     *　Hs256アルゴリズムを使い、秘密鍵は固定のキーを使用する
     *
     * @param secretKey jwt秘密鍵
     * @param ttlMillis jwt有効期限（ミリ秒）
     * @param claims    設定した情報
     * @return
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 署名を指定する際に使用する署名アルゴリズム、つまりヘッダーの部分
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // jwtの生成日付
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // jwtの中身
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
                .setExpiration(exp);

        return builder.compact();
    }

    /**
     * Token複合化
     *
     * @param secretKey
     * @param token
     * @return
     */
    public static Claims parseJWT(String secretKey, String token) {
        Claims claims = Jwts.parser()
                // 署名の秘密鍵を設定
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                // Token複合化
                .parseClaimsJws(token).getBody();
        return claims;
    }
}
