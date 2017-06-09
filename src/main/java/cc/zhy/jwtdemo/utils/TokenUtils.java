package cc.zhy.jwtdemo.utils;

import cc.zhy.jwtdemo.security.SecurityConfig;
import io.jsonwebtoken.*;

import java.util.Date;

/**
 * Created by zhy on 6/9/17.
 */
public class TokenUtils {
    public static String getIdFromToken(String jws) {
        return (String) Jwts.parser()
                .setSigningKey(SecurityConfig.KEY)
                .parseClaimsJws(jws).getBody().get("id");
    }
    public static String generateToken(String id) {
        return Jwts.builder()
                .claim("id", id)
                .setExpiration(new Date(System.currentTimeMillis() + 604800))
                .signWith(SignatureAlgorithm.HS256, SecurityConfig.KEY)
                .compact();
    }
    public static boolean validateToken(String jws) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(SecurityConfig.KEY)
                    .parseClaimsJws(jws);
            System.out.println(System.currentTimeMillis());
            System.out.println(claimsJws.getBody().getExpiration().getTime());
            if (System.currentTimeMillis() > claimsJws.getBody().getExpiration().getTime()) {
                return false;
            }
        }catch (JwtException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
