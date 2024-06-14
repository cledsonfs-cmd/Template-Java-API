package br.com.template.service.impl;
import br.com.template.model.entity.Usuario;
import br.com.template.model.entity.UsuarioHistorico;
import br.com.template.repository.HistoricoUsuarioRepository;
import br.com.template.service.HistoricoUsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoUsuarioServiceImpl implements HistoricoUsuarioService {

    @Autowired
    private HistoricoUsuarioRepository repository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public String salvar(UsuarioHistorico historicoUsuario) {
        if(historicoUsuario == null) {
            throw new RuntimeException("Objeto não informado!");
        }
        repository.save(historicoUsuario);
        return "Salvo com sucesso!";
    }

    @Override
    public UsuarioHistorico buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Nenhum registro encontrado."));
    }

    @Override
    public List<UsuarioHistorico> buscarTodos() {
        return repository.findAll();
    }

    @Override
    public String excluir(Integer id) {
        UsuarioHistorico objeto = buscarPorId(id);
        repository.delete(objeto);
        return "Excluido com sucesso!";
    }


    @Override
    public String atualizar(UsuarioHistorico historicoUsuario) {
        UsuarioHistorico objeto = buscarPorId(historicoUsuario.getId());
        modelMapper.map(historicoUsuario, objeto);
        repository.save(objeto);
        return "Atualizado com sucesso!";
    }

    @Override
    public List<UsuarioHistorico> buscarPorUsuario(Usuario usuario){
        return repository.findByUsuario(usuario);
    }
}