package br.com.training.v1.controller;

import br.com.training.shared.config.SwaggerConfig;
import br.com.training.v1.dto.request.UserRequest;
import br.com.training.v1.dto.response.UserResponse;
import br.com.training.v1.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = SwaggerConfig.User)
@RestController
@RestControllerAdvice
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Cadastrar um novo usuário",
            response = UserResponse.class,
            notes = "Essa operação salva um novo registro de um usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuário criado com sucesso"),
            @ApiResponse(code = 400, message = "Erro na requisição enviada pelo cliente"),
            @ApiResponse(code = 500, message = "Erro interno no serviço")
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse newUser = this.userService.salvar(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @ApiOperation(value = "Atualizar um usuário",
            response = UserResponse.class,
            notes = "Essa operação atualiza um registro de um usuário já existente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Erro na requisição enviada pelo cliente"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @PutMapping(value = "/{cpf}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String cpf, @Valid @RequestBody UserRequest userRequest) {
        UserResponse updateUser = this.userService.atualizar(cpf, userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
    }

    @ApiOperation(value = "Consultar usuário por id",
            response = UserResponse.class,
            notes = "Essa operação retorna o registro de um usuário consultado pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = this.userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @ApiOperation(value = "Consultar usuário por cpf",
            response = UserResponse.class,
            notes = "Essa operação retorna o registro de um usuário consultado pelo cpf")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @GetMapping(value = "/cpf/{cpf}", produces = "application/json")
    public ResponseEntity<UserResponse> getUserByCpf(@PathVariable String cpf) {
        UserResponse userResponse = this.userService.findByCpf(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @ApiOperation(value = "Consultar usuário por e-mail",
            response = UserResponse.class,
            notes = "Essa operação retorna o registro de um usuário consultado pelo email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @GetMapping(value = "/email/{email}", produces = "application/json")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        UserResponse userResponse = this.userService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @ApiOperation(value = "Listar todas os usuários",
            response = UserResponse.class,
            notes = "Essa operação retorna o registro de todos usuários cadastrados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de usuários exibida com sucesso"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserResponse>> getAllUser() {
        List<UserResponse> userResponse = this.userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @ApiOperation(value = "Excluir um usuário",
            response = UserResponse.class,
            notes = "Essa operação deleta o registro de um usuário pelo seu cpf")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Usuário excluído com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @DeleteMapping(value = "/{cpf}", produces = "application/json")
    public ResponseEntity<?> deleteUser(@PathVariable String cpf) {
        this.userService.excluir(cpf);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
