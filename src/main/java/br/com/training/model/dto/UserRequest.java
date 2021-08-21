package br.com.training.model.dto;

import br.com.training.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserRequest {

    @NotBlank(message = "O campo name é obrigatório")
    private String name;

    @NotBlank(message = "O campo email é obrigatório")
    @Email(message = "E-mail inválido!")
    private String email;

    @NotBlank(message = "O campo cpf é obrigatório")
    @CPF(message = "CPF ínválido")
    private String cpf;

    @NotBlank(message = "O campo birthDate é obrigatorio")
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
