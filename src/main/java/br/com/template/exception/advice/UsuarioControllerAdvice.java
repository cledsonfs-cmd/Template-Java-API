package br.com.template.exception.advice;

import br.com.template.exception.CampoObrigatorioException;
import br.com.template.exception.SenhaException;
import br.com.template.exception.UsuarioAutenticadoException;
import br.com.template.exception.UsuarioNotFoundException;
import br.com.template.model.dto.ErrorDTO;
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
    public ErrorDTO handleModuloNotFound(UsuarioNotFoundException usuarioNotFoundException){
        return new ErrorDTO("Usuario não encontrado!");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CampoObrigatorioException.class)
    public ErrorDTO handleModuloNotFound(CampoObrigatorioException campoObrigatorioException){
        return new ErrorDTO("Campo obrigatorio não informado!");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SenhaException.class)
    public ErrorDTO handleModuloNotFound(SenhaException senhaException){
        return new ErrorDTO("A senha deve ter no minimo 6 caracteres!");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsuarioAutenticadoException.class)
    public ErrorDTO handleModuloNotFound(UsuarioAutenticadoException usuarioAutenticadoException){
        return new ErrorDTO("Usuário não autenticado!");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AuthenticationException.class)
    public ErrorDTO handleModuloNotFound(AuthenticationException exception){
        return new ErrorDTO(exception.getMessage());
    }


}
