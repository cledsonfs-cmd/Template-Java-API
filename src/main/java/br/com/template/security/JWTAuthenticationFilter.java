package br.com.template.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Metodo principal onde toda a requisição bate antes de chegar no nosso
    // endpoint.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Pego o token de dentro da requisição.
        String token = obterToken(request);

        // Pego o id do usuário qu está dentro do token.
        Optional<Long> id = jwtService.obterIdDoUuario(token);

        // Se não achou o id, é porque o usuario não mandou o token correto.
        if (id.isPresent()) {

            // Pego o usuario dono do token pelo seu Id.
            UserDetails usuario = customUserDetailsService.obterUsuarioPorId(id.get());

            // Nesse ponto verificamos se o usuario está autenticado ou não.
            // Aqui também poderiamos validar as permissões.
            UsernamePasswordAuthenticationToken autenticacao = new UsernamePasswordAuthenticationToken(usuario, null,
                    Collections.emptyList());

            // Mudando a autenticação para a propria requisição.
            autenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Repasso a autenticação para o contexto o security.
            // A partir de agora o spring toma conta de tudo pra mim.
            SecurityContextHolder.getContext().setAuthentication(autenticacao);
        }

        // Metodo padrão para filtrar as regras do usuário.
        filterChain.doFilter(request, response);

    }

    private String obterToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        // Verifica se veio alguma coisa sem ser espaços em brancos dentro do token.
        if (!StringUtils.hasText(token)) {
            return null;
        }

        return token.substring(7);
        // Bearer as42fgh452.asadfdgfdghdfg.a2511dsadasdasdd
    }

}
