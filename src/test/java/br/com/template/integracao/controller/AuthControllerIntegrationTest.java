package br.com.template.integracao.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

import br.com.template.model.dto.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.template.enums.RoleName;
import br.com.template.enums.UsuarioStates;
import br.com.template.integracao.config.TestConfigs;
import br.com.template.model.entity.Role;
import br.com.template.model.entity.Usuario;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import testContainers.AbstractIntegrationTest;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthControllerIntegrationTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static Usuario mockUsuario;
    private static UsuarioDTO mockUsuarioDTO;
    private static CreateUserDto mockCreateUserDTO;
    private static LoginRequestDTO mockLoginRequestDTO;
    private static RecoveryJwtTokenDto Token;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specification = new RequestSpecBuilder()
                .setBasePath("/users/create")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        mockCreateUserDTO = new CreateUserDto("paulo@email.com", "paulo", "paulo123456", UsuarioStates.ATIVO.getId(), RoleName.ROLE_ADMINISTRATOR);
        mockLoginRequestDTO = new LoginRequestDTO("paulo@email.com", "paulo123456");
        mockUsuarioDTO = new UsuarioDTO(1976, "paulo@email.com", UsuarioStates.ATIVO.getId());
    }

    @Order(1)
    @DisplayName("JUnit test integração criação de um usuario")
    @Test
    void createUserTest() throws JsonMappingException, JsonProcessingException {
        specification.basePath("/users");
        String expectMessage = "Usuario criado com sucesso!";

        var response = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(mockCreateUserDTO)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertEquals(expectMessage, response);
    }

    @Order(2)
    @DisplayName("JUnit test de autenticação")
    @Test
    void LoginUserTest() throws JsonMappingException, JsonProcessingException {
        specification.basePath("/users/login");

        var response = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(mockLoginRequestDTO)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();


        LoginResponseDTO found = objectMapper.readValue(response, LoginResponseDTO.class);

        Token = found.token();

        assertNotNull(found);
        assertTrue(found.uuid() > 0);
        assertNotNull(found.token());

    }

    @Order(3)
    @DisplayName("JUnit test localizar um usuario por email")
    @Test
    void localizarUserPorEmailTest() throws JsonMappingException, JsonProcessingException {
        specification.basePath("/users/email");

        var response = given().spec(specification)
                .header("authorization", "Bearer " + Token.token())
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("email", mockCreateUserDTO.email())
                .when()
                .get("{email}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        mockUsuario = objectMapper.readValue(response, Usuario.class);

        assertNotNull(mockUsuario);
    }


    @Order(4)
    @DisplayName("JUnit test atualizar usuario")
    @Test
    void atualizarUserTest() throws JsonMappingException, JsonProcessingException {
        specification.basePath("/users/update");
        String expectMessage = "Usuario atualizado com sucesso!";
        mockUsuario.setNome("Paulo Silva de Amaral Pereira Goz");

        var response = given().spec(specification)
                .header("authorization", "Bearer " + Token.token())
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(mockUsuario)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertEquals(expectMessage, response);

    }

    @Order(5)
    @DisplayName("JUnit test localizar um usuario por id")
    @Test
    void localizarUserPorIdTest() throws JsonMappingException, JsonProcessingException {
        specification.basePath("/users");

        var response = given().spec(specification)
                .header("authorization", "Bearer " + Token.token())
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", mockUsuario.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        mockUsuario = objectMapper.readValue(response, Usuario.class);

        assertNotNull(mockUsuario);

    }

    @Order(6)
    @DisplayName("JUnit test retorna uma lista de usuarios")
    @Test
    void retornaListaUserTest() throws JsonMappingException, JsonProcessingException {
        specification.basePath("/users/all");

        var response = given().spec(specification)
                .header("authorization", "Bearer " + Token.token())
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Usuario[] myArray = objectMapper.readValue(response, Usuario[].class);
        List<Usuario> usuarios = Arrays.asList(myArray);

        Usuario foundUsuarioOne = usuarios.get(0);

        assertNotNull(foundUsuarioOne);

        assertNotNull(foundUsuarioOne.getId());
        assertNotNull(foundUsuarioOne.getEmail());
        assertNotNull(foundUsuarioOne.getNome());
        assertNotNull(foundUsuarioOne.getPassword());

    }

    @Order(7)
    @DisplayName("JUnit test ativar usuario")
    @Test
    void ativarUserTest() throws JsonMappingException, JsonProcessingException {
        specification.basePath("/users/ativar");
        String expectMessage = "Usuario Ativado com sucesso!";

        var response = given().spec(specification)
                .header("authorization", "Bearer " + Token.token())
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(mockUsuarioDTO)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertEquals(expectMessage, response);

    }

    @Order(8)
    @DisplayName("JUnit test suspender usuario")
    @Test
    void suspenderUserTest() throws JsonMappingException, JsonProcessingException {
        specification.basePath("/users/suspender");
        String expectMessage = "Usuario Suspenso com sucesso!";

        var response = given().spec(specification)
                .header("authorization", "Bearer " + Token.token())
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(mockUsuarioDTO)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertEquals(expectMessage, response);

    }

    @Order(9)
    @DisplayName("JUnit test Inativado usuario")
    @Test
    void inativarUserTest() throws JsonMappingException, JsonProcessingException {
        specification.basePath("/users/inativar");
        String expectMessage = "Usuario Inativado com sucesso!";

        var response = given().spec(specification)
                .header("authorization", "Bearer " + Token.token())
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(mockUsuarioDTO)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertEquals(expectMessage, response);

    }

    @Order(10)
    @DisplayName("JUnit test excluir usuario")
    @Test
    void excluirUserTest() throws JsonMappingException, JsonProcessingException {
        specification.basePath("/users/excluir");
        String expectMessage = "Usuario Excluido com sucesso!";

        var response = given().spec(specification)
                .header("authorization", "Bearer " + Token.token())
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(mockUsuarioDTO)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertEquals(expectMessage, response);

    }

    @Order(11)
    @DisplayName("JUnit test autenticacao básica")
    @Test
    void autenticacaoBasicaTest() throws JsonMappingException, JsonProcessingException {
        specification.basePath("/users/test");
        String expectMessage = "Autenticado com sucesso";

        var response = given().spec(specification)
                .header("authorization", "Bearer " + Token.token())
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertEquals(expectMessage, response);

    }

    @Order(12)
    @DisplayName("JUnit test autenticacao customer")
    @Test
    void autenticacaoCustomerTest() throws JsonMappingException, JsonProcessingException {
        specification.basePath("/users/test/customer");
        String expectMessage = "Cliente autenticado com sucesso";

        var response = given().spec(specification)
                .header("authorization", "Bearer " + Token.token())
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertEquals(expectMessage, response);

    }

    @Order(13)
    @DisplayName("JUnit test autenticacao administrator")
    @Test
    void autenticacaoAdministradorTest() throws JsonMappingException, JsonProcessingException {
        specification.basePath("/users/test/administrator");
        String expectMessage = "Administrador autenticado com sucesso";

        var response = given().spec(specification)
                .header("authorization", "Bearer " + Token.token())
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertEquals(expectMessage, response);

    }

}
