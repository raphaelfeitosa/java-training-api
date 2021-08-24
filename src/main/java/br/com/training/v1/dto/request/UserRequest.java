package br.com.training.v1.dto.request;

import br.com.training.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class UserRequest {

    @NotBlank(message = "O campo name é obrigatório")
    private String name;

    @NotBlank(message = "O campo email é obrigatório")
//    @UniqueValue(fieldName = "email", domainClass = User.class, message = "Email já cadastrado!")
    @Email(message = "E-mail inválido!")
    private String email;

    @NotBlank(message = "O campo cpf é obrigatório")
//    @UniqueValue(fieldName = "cpf", domainClass = User.class, message = "CPF já cadastrado!")
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "O campo birthDate é obrigatório. formato: yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private String birthDate;

    public UserRequest(){
    }

    public UserRequest(String name, String email, String cpf, String birthDate) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.birthDate = birthDate;
    }

    public  User toUserCreate() {
        return new User(this);
    }

    public User toUserUpdate(Long id) {
        User updateUser = toUserCreate();
        updateUser.setId(id);

        return updateUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
