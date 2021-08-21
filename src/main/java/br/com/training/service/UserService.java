package br.com.training.service;

import br.com.training.model.User;
import br.com.training.model.dto.UserRequest;
import br.com.training.model.dto.UserResponse;
import br.com.training.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse salvar(UserRequest userRequest) {
        User newUser = this.userRepository.save(userRequest.toUserCreate());
        return new UserResponse(newUser);
    }

    public UserResponse atualizar(String cpf, UserRequest userRequest) {
        User user = this.userRepository.findByCpf(cpf);
        if (user != null) {
           User updatedUser = this.userRepository.save(userRequest.toUserUpdate(user.getId()));
            return new UserResponse(updatedUser);
        }
        return null;
    }

    public void excluir(String cpf) {
        User user = this.userRepository.findByCpf(cpf);
        if (user != null) {
            this.userRepository.delete(user);
        }
    }

    public UserResponse findByCpf(String cpf) {
        User user = this.userRepository.findByCpf(cpf);
        return new UserResponse(user);
    }

    public List<UserResponse> findAll() {
        List<User> userList = this.userRepository.findAll();
        return UserResponse.Collection(userList);
    }
}
