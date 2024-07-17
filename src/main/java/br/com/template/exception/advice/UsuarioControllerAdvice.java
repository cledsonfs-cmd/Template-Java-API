package br.com.template.exception.advice;

import br.com.template.exception.CampoObrigatorioException;
import br.com.template.exception.SenhaException;
import br.com.template.exception.UsuarioAutenticadoException;
import br.com.template.exception.UsuarioNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = "br.com.template.controller")
public class UsuarioControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsuarioNotFoundException.class)
    public String handleModuloNotFound(UsuarioNotFoundException usuarioNotFoundException){
        return "Usuario não encontrado!";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CampoObrigatorioException.class)
    public String handleModuloNotFound(CampoObrigatorioException campoObrigatorioException){
        return "Campo obrigatorio não informado!";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SenhaException.class)
    public String handleModuloNotFound(SenhaException senhaException){
        return "A senha deve ter no minimo 6 caracteres!";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsuarioAutenticadoException.class)
    public String handleModuloNotFound(UsuarioAutenticadoException usuarioAutenticadoException){
        return "Usuário não autenticado!";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AuthenticationException.class)
    public String handleModuloNotFound(AuthenticationException exception){
        return exception.getMessage();
    }


}
