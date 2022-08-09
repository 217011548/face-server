package com.coding.utils;

import com.google.common.collect.Sets;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * felix
 */
@Slf4j
public class JwtTokenUtils {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "jwtsecretdemo";
    private static final String ISS = "echisan";

    private static final String ROLE_CLAIMS = "roles";

    
    private static final long EXPIRATION_REMEMBER = 604800L;

    // create token
    public static String createToken(String userId, String role) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, role);
        Date exp = new Date(System.currentTimeMillis() + EXPIRATION_REMEMBER * 1000);
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS512, SECRET).setClaims(map).setIssuer(ISS)
                .setSubject(userId).setIssuedAt(new Date()).setExpiration(exp).compact();
        log.debug("token:{}:{}", exp, token);
        return token;
    }

    // get user name from token
    public static String getUsername(String token) {
        try {
            token = token.replace(TOKEN_PREFIX, "");
            return getTokenBody(token).getSubject();
        } catch (Exception e) {
            log.error("Get userName fail", e);
            return null;
        }
    }

    // get role
    public static Set<String> getUserRole(String token) {
        token = token.replace(TOKEN_PREFIX, "");
        String roles = (String) getTokenBody(token).get(ROLE_CLAIMS);
        return Sets.newHashSet(roles.split(","));
    }

    // check validated time 
    public static boolean isExpiration(String token) {
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (Exception e) {
            log.info("already validated:{}", token);
            return true;
        }
    }

    private static Claims getTokenBody(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    public static boolean isToken(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        token = token.replace(TOKEN_PREFIX, "");
        return token.split("\\.").length == 3;
    }
}
