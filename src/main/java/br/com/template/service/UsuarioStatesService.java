package br.com.template.service;

import br.com.template.statemachine.enums.UsuarioEvents;
import br.com.template.statemachine.enums.UsuarioStates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioStatesService {

    public void actionFazerAlgo(StateContext<UsuarioStates, UsuarioEvents> state) {

    }

    public void actionFazerOutraCoisa(StateContext<UsuarioStates, UsuarioEvents> state) {

    }

    public boolean guardFazerAlgo(StateContext<UsuarioStates, UsuarioEvents> state){
        return false;
    }
}
