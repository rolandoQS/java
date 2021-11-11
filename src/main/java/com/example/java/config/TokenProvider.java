package com.example.java.config;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.java.common.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.example.java.common.Constants.SIGNING_KEY;

@Component
public class TokenProvider implements Serializable {

   public String getUsernameFromToken(String token) {
      return getClaimFromToken(token, Claims::getSubject);
   }

   public Date getExpirationDateFromToken(String token) {
      return getClaimFromToken(token, Claims::getExpiration);
   }

   public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
      final Claims claims = getAllClaimsFromToken(token);
      return claimsResolver.apply(claims);
   }

   private Claims getAllClaimsFromToken(String token) {
      return Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
   }

   private Boolean isTokenExpired(String token) {
      final Date expiration = getExpirationDateFromToken(token);
      return expiration.before(new Date());
   }

   public String generateToken(Authentication authentication) {
      JwtBuilder jwt = Jwts
            .builder()
            .setSubject(authentication.getName())
            .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000));
      return jwt.compact();
   }

   public Boolean validateToken(String token, UserDetails userDetails) {
      final String username = getUsernameFromToken(token);
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
   }

   UsernamePasswordAuthenticationToken getAuthentication(final String token, final UserDetails userDetails) {

      final JwtParser jwtParser = Jwts.parser().setSigningKey(SIGNING_KEY);

      final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

      final Claims claims = claimsJws.getBody();

      final Collection<? extends GrantedAuthority> authorities = Arrays
            .stream(claims.get("sub").toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

      return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
   }

}
