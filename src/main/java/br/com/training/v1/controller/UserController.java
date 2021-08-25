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
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse newUser = this.userService.salvar(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping(value = "/{cpf}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String cpf, @Valid @RequestBody UserRequest userRequest) {
        UserResponse updateUser = this.userService.atualizar(cpf, userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
    }

    @GetMapping(value = "/{id}")

    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = this.userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<UserResponse> getUserByCpf(@PathVariable String cpf) {
        UserResponse userResponse = this.userService.findByCpf(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        UserResponse userResponse = this.userService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUser() {
        List<UserResponse> userResponse = this.userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @DeleteMapping(value = "/{cpf}")
    public ResponseEntity<?> deleteUser(@PathVariable String cpf) {
        this.userService.excluir(cpf);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
