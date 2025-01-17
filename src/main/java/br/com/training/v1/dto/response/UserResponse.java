package br.com.training.v1.dto.response;

import br.com.training.model.User;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UserResponse {
    @ApiModelProperty(value = "Id do usuário")
    private Long id;

    @ApiModelProperty(value = "Nome do usuário")
    private String name;

    @ApiModelProperty(value = "Email do usuário")
    private String email;

    @ApiModelProperty(value = "CPF do usuário")
    private String cpf;

    @ApiModelProperty(value = "Data de nascimento do usuário")
    private LocalDate birthDate;

    public UserResponse() {}

    public UserResponse(Long id, String name, String email, String cpf, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.birthDate = birthDate;
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.birthDate = user.getBirthDate();
    }

    public static List<UserResponse> Collection(List<User> users) {
        return users.stream().map(UserResponse::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}
