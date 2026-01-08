package com.expense.expense_tracker.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
@Service
public class JwtService {
//    Builder = create token
//    Parser = read token
    public static final String SECRET = "357638792F423F4428472B4B6258655368566D597133743677397A2443264629";
    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }
    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }
    public boolean isTokenExpired(String token){
        return  extractExpiration(token).before(new Date());
    }
    public boolean validateToken(String token,UserDetails userDetails){
        String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public String createToken(Map<String, Object> claims,String username ){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*1000*60*1))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSigningKey())// verifies token signature
                .build()
//        parseClaimsJws() validates the JWT and extracts the claims (payload) from it.
                .parseClaimsJws(token)// parses and validates the token
                .getBody();// returns the payload (claims) if valid token
    }
    public String GenerateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }
    //getSigningKey() gives JWT that secret key in the correct format.
    private Key getSigningKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
