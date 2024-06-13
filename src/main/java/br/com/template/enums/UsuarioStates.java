package br.com.template.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum UsuarioStates {
    INICIO(1),
    ATIVO(2),
    SUSPENSO(3),
    INATIVO(4),
    FIM(5);

    private int id;

    public static UsuarioStates of(int id){
        return Stream.of(UsuarioStates.values())
                .filter( i -> i.getId() == id)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public static JSONArray toJson(){
        JSONArray array = new JSONArray();
        for (UsuarioStates dt : UsuarioStates.values()) {
            JSONObject item = new JSONObject();
            item.put("id", dt.getId());
            item.put("descricao", dt.name());
            array.add(item);
        }
        return array;
    }
}
