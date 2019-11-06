package tcc.PluriGame.domain.security.jwt;

import static tcc.PluriGame.domain.security.model.Constants.*;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;


public class JwtAuthTokenFilter extends OncePerRequestFilter {
	 
	  @Autowired
	  private JwtProvider jwtTokenUtil;
	 
	  @Autowired
	  private UserDetailsService UserDetailsService;
	 
	 
	  @Override
	  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	      throws ServletException, IOException {
		  
		  	String header = request.getHeader(HEADER_STRING);
			String username = null;
			String authToken = null;
			
			if(header != null && header.startsWith(TOKEN_PREFIX)) {
				authToken = header.replace(TOKEN_PREFIX, "");
				try {
					username = jwtTokenUtil.getUsernameFromToken(authToken);
				}catch (IllegalArgumentException e) {
					logger.error("Ocorreu algum erro ao tentar pegar o usuario do token", e);
				}catch (ExpiredJwtException e) {
					logger.warn("O token está vencido, logo não é mais valido" , e);
				}catch (SignatureException e) {
					logger.error("Falha na autenticação. Usuario ou senha invalidos", e);
				}
			} else {
				logger.warn("Não foi encontrado o token no cabeçalho, Acesso negado");
			}
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				
				UserDetails userDetails = UserDetailsService.loadUserByUsername(username);
				
				if(jwtTokenUtil.validateToken(authToken, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					logger.info("Usuario autenticado: " + username +  ", acesso liberado");
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
			filterChain.doFilter(request, response);
			
		}
	  
}

