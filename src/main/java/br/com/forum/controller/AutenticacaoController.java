package br.com.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.forum.config.security.TokenService;
import br.com.forum.controller.dto.TokenDto;
import br.com.forum.controller.form.LoginForm;

@RestController
@RequestMapping("/auth")
@Profile("prod")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager athuManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		
		
		try { // cliente vai mandar o usuario e a senha, eu chamo o athuManager para disparar o processo de autenticação... 
			Authentication authentication = athuManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication); 
			return ResponseEntity.ok(new TokenDto(token, "Bearer")); //
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build(); // se não tiver ok caiu no catch
		}
	}
}
