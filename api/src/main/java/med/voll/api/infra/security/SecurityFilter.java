package med.voll.api.infra.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    var tokenJWT = recuperarToken(request);
    System.out.println(tokenJWT);
    if (tokenJWT != null) {
      // 1. Validar o token (usando seu TokenService)
      // 2. Recuperar o usuário do banco
      // 3. Autenticar no contexto do Spring Security
      System.out.println("Token detectado: " + tokenJWT);
    }

    // Continua o fluxo, com ou sem token
    filterChain.doFilter(request, response);

  }

  private String recuperarToken(HttpServletRequest request) {
    var authorizationHeader = request.getHeader("Authorization");

    // Se for nulo, apenas retorna null em vez de explodir o servidor
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.replace("Bearer ", "").trim();
    }

    return null;
  }
}
