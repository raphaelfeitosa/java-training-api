package br.com.training.v1.controller;

import br.com.training.v1.dto.request.UserRequest;
import br.com.training.v1.dto.response.UserResponse;
import br.com.training.v1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RestControllerAdvice
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse newUser = this.userService.salvar(userRequest);
        return ResponseEntity.ok().body(newUser);
    }

    @PutMapping(value = "/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> updateUser(@PathVariable String cpf, @Valid @RequestBody UserRequest userRequest) {
        UserResponse updateUser = this.userService.atualizar(cpf, userRequest);
        return ResponseEntity.ok().body(updateUser);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = this.userService.findById(id);
        return ResponseEntity.ok().body(userResponse);
    }
    @GetMapping(value = "/cpf/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getUserByCpf(@PathVariable String cpf) {
        UserResponse userResponse = this.userService.findByCpf(cpf);
        return ResponseEntity.ok().body(userResponse);
    }

    @GetMapping(value = "/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        UserResponse userResponse = this.userService.findByEmail(email);
        return ResponseEntity.ok().body(userResponse);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserResponse>> getAllUser() {
        List<UserResponse> userResponse = this.userService.findAll();
        return ResponseEntity.ok().body(userResponse);
    }

    @DeleteMapping(value = "/{cpf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(@PathVariable String cpf) {
        this.userService.excluir(cpf);
        return ResponseEntity.noContent().build();
    }

}
