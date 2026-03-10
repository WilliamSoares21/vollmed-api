package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.AutenticacaoDTO;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.TokenService;
import med.voll.api.infra.security.TokenJWTDTO;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

  @Autowired
  private AuthenticationManager manager;

  @Autowired
  private TokenService tokenService;

  @PostMapping
  public ResponseEntity efetuarLogin(@RequestBody @Valid AutenticacaoDTO dados) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
    var authentication = manager.authenticate(authenticationToken);

    System.out.println("Token gerado para o usuário: " + ((Usuario) authentication.getPrincipal()).getLogin()
        + " token: " + tokenService.gerarToken((Usuario) authentication.getPrincipal()));
    var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
    return ResponseEntity.ok(new TokenJWTDTO(tokenJWT));
  }
}
