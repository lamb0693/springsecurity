package com.example.securitytest.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
@Log4j2
public class TokenService {
    private final String SECRET_KEY = "tsIo#1sd320956$eAawuet9934r341ret#ytretyghpoiuabldksfdkwerwertwer";

    public String createToken(int expTimeMin, String id){
        if(expTimeMin <= 0) {
            throw new RuntimeException("WrongExpTimeOfToken");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] byteSecretKey = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key sigingKey = new SecretKeySpec(byteSecretKey, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(id)
                .signWith(sigingKey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis()+ (long)expTimeMin*1000))
                .compact();

    }

    public String getIdFromToken(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();

        log.info("##### getIdFromToken@TokenService");
        log.info("### expiration " + claims.getExpiration());
        log.info("#### issuedAt "  +claims.getIssuedAt());
        log.info("#### issuer " + claims.getIssuer());

        return claims.getSubject();
    }

    public Map<String, Object> validate(String token){
        Map<String, Object> claims;

        claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }
}
