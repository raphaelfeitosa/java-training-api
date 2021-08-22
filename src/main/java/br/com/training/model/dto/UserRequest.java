package br.com.training.model.dto;

import br.com.training.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;

public class UserRequest {

    @NotEmpty(message = "O campo name não pode ser vazio")
    @NotNull(message = "O campo name não pode ser nulo")
    private String name;

    @NotEmpty(message = "O campo email não pode ser vazio")
    @NotNull(message = "O campo email não pode ser nulo")
    @Email(message = "E-mail inválido!")
    private String email;

    @NotEmpty(message = "O campo cpf é não pode ser vazio")
    @NotNull(message = "O campo cpf é não pode ser nulo")
    @CPF(message = "CPF ínválido")
    private String cpf;

    @NotEmpty(message = "O campo birthDate não pode ser vazio. formato: yyyy-MM-dd")
    @NotNull(message = "O campo birthDate não pode ser nulo. formato: yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String birthDate;

    public User toUserCreate() {
        return new User(name, email, cpf, new User.LocalDateSpringConverter().convert(birthDate));
    }

    public User toUserUpdate(Long id) {
        User updateUser = toUserCreate();
        updateUser.setId(id);

        return updateUser;
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

    public String getBirthDate() {
        return birthDate;
    }
}
