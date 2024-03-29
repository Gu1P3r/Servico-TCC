package tcc.PluriGame.domain.security.jwt;

import static tcc.PluriGame.domain.security.model.Constants.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtProvider implements Serializable{
	
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token , Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> ClaimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return ClaimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
				   .setSigningKey(SIGNING_KEY)
				   .parseClaimsJws(token)
				   .getBody();
	}
	
	private boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public String generateToken(Authentication authentication) {
		final String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
				return Jwts.builder()
						.setSubject(authentication.getName())
						.claim(AUTHORITIES_KEY, authorities)
						.signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() + ACESS_TOKEN_VALIDITY_SECONDS*1000))
						.compact();
			
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (
				username.equals(userDetails.getUsername()) && !isTokenExpired(token)
				);
				
	}
	
	UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth, final UserDetails userDetails) {
		
		final JwtParser jwtParser = Jwts.parser().setSigningKey(SIGNING_KEY);
		
		final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
		
		final Claims claims = claimsJws.getBody();
		
		final Collection<? extends GrantedAuthority> authorities =
				Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		
		return new UsernamePasswordAuthenticationToken(userDetails,"", authorities);
	}
}