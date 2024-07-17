package br.com.template.exception.advice;

import java.time.LocalDateTime;

import br.com.template.exception.CampoObrigatorioException;
import br.com.template.exception.SenhaException;
import br.com.template.exception.UsuarioNotFoundException;
import br.com.template.model.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
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
        return new ErrorDTO(HttpStatus.NOT_FOUND.value(),"Usuario não encontrado.",LocalDateTime.now());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CampoObrigatorioException.class)
    public ErrorDTO handleModuloNotFound(CampoObrigatorioException campoObrigatorioException){
        return new ErrorDTO(HttpStatus.NOT_FOUND.value(),"Campo obrigatorio não informado.",LocalDateTime.now());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SenhaException.class)
    public ErrorDTO handleModuloNotFound(SenhaException senhaException){
        return new ErrorDTO(HttpStatus.NOT_FOUND.value(),"A senha deve ter no minimo 6 caracteres",LocalDateTime.now());
    }
}
