package br.com.template.security;

import java.util.Date;
import java.util.Optional;

import br.com.template.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTService {
    
    // Chave secreta utilizada pelo JWT para codificar e decodificar o token.
    private static final String chavaPrivadaJWT = "secretKey";

    /**
     * Metodo para gerar o token JWT.
     * @param authentication Autenticação do usuario.
     * @return Token.
     */
    public String gerarToken(Authentication authentication){

        // 1 Dia em milliseconds
        // Aqui pode variar de acordo com a sua regra de negocio.
        int tempoExpiracao = 86400000;

        // Aqui estou crtiando uma data de expiração para o tokem com base no tempo de expiração.
        // Ele pega a data atual e soma mais 1 dia em milliseconds.
        Date dataExpiracao = new Date(new Date().getTime() + tempoExpiracao);

        // Aqui pegamenos o usuario atual da autenticação.
        Usuario usuario = (Usuario) authentication.getPrincipal();

        // Aqui ele pega todos os dados e retorna um token bonito do JWT.
        return Jwts.builder()
                .setSubject(usuario.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS512, chavaPrivadaJWT)
                .compact();
    }

    /**
     * Metodo para retornar o id do usuario dono do token
     * @param token Token do usuário
     * @return id do usuario.
     */
    public Optional<Long> obterIdDoUuario(String token){
        
        try {
            // Retorna as permissões do token.
            Claims claims = parse(token).getBody();

            // Retorna o id de dentro do token se entrar, caso contrario retorna null.
            return Optional.ofNullable(Long.parseLong(claims.getSubject()));

        } catch (Exception e) {
            // Se não encontrar nada, devolve um optional null.
            return Optional.empty();
        }
    }

    // Metodo que sabe descobrir de dentro do tokem com base na chave privada qual as permissões do usuário.
    private Jws<Claims> parse(String token) {
        return Jwts.parser().setSigningKey(chavaPrivadaJWT).parseClaimsJws(token);
    }

}
