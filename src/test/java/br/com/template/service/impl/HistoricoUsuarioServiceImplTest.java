package br.com.template.service.impl;

import br.com.template.enums.RoleName;
import br.com.template.enums.UsuarioStates;
import br.com.template.model.entity.Role;
import br.com.template.model.entity.Usuario;
import br.com.template.model.entity.UsuarioHistorico;
import br.com.template.repository.HistoricoUsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistoricoUsuarioServiceImplTest {

    @Mock
    HistoricoUsuarioRepository mockHistoricoUsuarioRepository;

    @InjectMocks
    HistoricoUsuarioServiceImpl injectHistoricoUsuarioServiceImpl;

    Usuario mockUsuario;
    UsuarioHistorico mockUsuarioHistorico;

    @BeforeEach
    void setUp() {
        mockUsuario = Usuario.builder()
                .id(1)
                .email("andre@email.com")
                .nome("Andre da Silva")
                .idStatus(UsuarioStates.INICIO.getId())
                .password("andre123456")
                .role(Role.builder()
                        .name(RoleName.ROLE_ADMINISTRATOR)
                        .build())
                .build();

        mockUsuarioHistorico = UsuarioHistorico.builder()
                .id(1000)
                .descricao("Teste Unitario")
                .dataRegistro(LocalDateTime.now())
                .build();
    }

    @Test
    void salvar() {
        String expectMessage = "Salvo com sucesso!";
        when(mockHistoricoUsuarioRepository.save(any(UsuarioHistorico.class))).thenReturn(mockUsuarioHistorico);

        String retorno = injectHistoricoUsuarioServiceImpl.salvar(mockUsuarioHistorico);

        assertEquals(expectMessage, retorno);
    }

    @Test
    void buscarPorId() {
        when(mockHistoricoUsuarioRepository.findById(anyInt())).thenReturn(Optional.ofNullable(mockUsuarioHistorico));

        UsuarioHistorico retorno = injectHistoricoUsuarioServiceImpl.buscarPorId(1000);

        assertEquals(mockUsuarioHistorico, retorno);
        assertEquals(1000, retorno.getId());
    }

    @Test
    void buscarTodos() {
        List<UsuarioHistorico> usuarios = new ArrayList<>();
        usuarios.add(mockUsuarioHistorico);
        usuarios.add(mockUsuarioHistorico);
        usuarios.add(mockUsuarioHistorico);
        when(mockHistoricoUsuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioHistorico> retorno = injectHistoricoUsuarioServiceImpl.buscarTodos();

        assertFalse(retorno.isEmpty());
        assertEquals(3, retorno.size());
    }

    @Test
    void excluir() {
        String expectMessage = "Excluido com sucesso!";
        when(mockHistoricoUsuarioRepository.findById(anyInt())).thenReturn(Optional.ofNullable(mockUsuarioHistorico));

        String retorno = injectHistoricoUsuarioServiceImpl.excluir(1000);

        assertEquals(expectMessage, retorno);
    }

    @Test
    void atualizar() {
        String expectMessage = "Atualizado com sucesso!";
        when(mockHistoricoUsuarioRepository.save(any(UsuarioHistorico.class))).thenReturn(mockUsuarioHistorico);
        when(mockHistoricoUsuarioRepository.findById(anyInt())).thenReturn(Optional.ofNullable(mockUsuarioHistorico));

        String retorno = injectHistoricoUsuarioServiceImpl.atualizar(mockUsuarioHistorico);

        assertEquals(expectMessage, retorno);
    }

    @Test
    void buscarPorUsuario() {
        List<UsuarioHistorico> usuarios = new ArrayList<>();
        usuarios.add(mockUsuarioHistorico);
        usuarios.add(mockUsuarioHistorico);
        usuarios.add(mockUsuarioHistorico);
        when(mockHistoricoUsuarioRepository.findByUsuario(any(Usuario.class))).thenReturn(usuarios);

        List<UsuarioHistorico> retorno = injectHistoricoUsuarioServiceImpl.buscarPorUsuario(mockUsuario);

        assertFalse(retorno.isEmpty());
        assertEquals(3, retorno.size());
    }
}