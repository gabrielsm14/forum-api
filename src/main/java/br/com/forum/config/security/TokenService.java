package br.com.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.forum.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}")  // vai até o properties vai ver quem é esse propriedade e vai pegar o valor dela 
	private String expiration;
	
	@Value("${forum.jwt.secret}")
	private String secret;


	// Gerar o token
	public String gerarToken(Authentication authentication) {
		Usuario logado = (Usuario) authentication.getPrincipal();
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration)); // getTime vai pegar o Mili segundos da variavel hoje e somar com expiration, e criar a nova data(daqui 1 dia)
						
		return Jwts.builder().setIssuer("API do Forum")
				.setSubject(logado.getId().toString())
				.setIssuedAt(hoje)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, secret) // dizer quem é o algoritmo de criptografia e a senha da aplicação
				.compact(); // compactar e transformar em uma string 
	}


	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	public Long getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
		 
	}

}
