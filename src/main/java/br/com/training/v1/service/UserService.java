package br.com.training.v1.service;

import br.com.training.model.User;
import br.com.training.v1.dto.request.UserRequest;
import br.com.training.v1.dto.response.UserResponse;
import br.com.training.repository.UserRepository;
import br.com.training.v1.service.exceptions.UserException;
import br.com.training.v1.constant.MessagesConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserResponse salvar(UserRequest userRequest) {
        try {
            if (this.userRepository.findByCpf(userRequest.getCpf()).isPresent()) {
                throw new UserException(MessagesConstant.ERROR_USER_CPF_REGISTERED.getValor(), HttpStatus.BAD_REQUEST);
            }
            if (this.userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
                throw new UserException(MessagesConstant.ERROR_USER_EMAIL_REGISTERED.getValor(), HttpStatus.BAD_REQUEST);
            }
            return this.userRepository.save(userRequest.toUserCreate()).toResponse();
        } catch (UserException userException) {
            throw userException;
        } catch (Exception exception) {
            throw new UserException(MessagesConstant.ERROR_GENERIC.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public UserResponse atualizar(String cpf, UserRequest userRequest) {
        try {
            UserResponse user = this.findByCpf(cpf);
            boolean userCpf = this.userRepository.findByCpf(userRequest.getCpf()).isPresent();
            boolean userEmail = this.userRepository.findByEmail(userRequest.getEmail()).isPresent();

            if (userCpf && !user.getCpf().equals(userRequest.getCpf())) {
                throw new UserException(MessagesConstant.ERROR_USER_CPF_REGISTERED.getValor(), HttpStatus.BAD_REQUEST);
            }
            if (userEmail && !user.getEmail().equals(userRequest.getEmail())) {
                throw new UserException(MessagesConstant.ERROR_USER_EMAIL_REGISTERED.getValor(), HttpStatus.BAD_REQUEST);
            }
            return this.userRepository.save(userRequest.toUserUpdate(user.getId())).toResponse();
        } catch (UserException userException) {
            throw userException;
        } catch (Exception exception) {
            throw new UserException(MessagesConstant.ERROR_GENERIC.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void excluir(String cpf) {
        try {
            UserResponse user = this.findByCpf(cpf);
            this.userRepository.deleteById(user.getId());
        } catch (UserException userException) {
            throw userException;
        } catch (Exception exception) {
            throw new UserException(MessagesConstant.ERROR_GENERIC.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        try {
            User user = this.userRepository.findById(id)
                    .orElseThrow(() -> new UserException(MessagesConstant.ERROR_USER_NOT_FOUND.getValor(), HttpStatus.NOT_FOUND));
            return new UserResponse(user);
        } catch (UserException userException) {
            throw userException;
        } catch (Exception exception) {
            throw new UserException(MessagesConstant.ERROR_GENERIC.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public UserResponse findByCpf(String cpf) {
        try {
            User user = this.userRepository.findByCpf(cpf)
                    .orElseThrow(() -> new UserException(MessagesConstant.ERROR_USER_NOT_FOUND.getValor(), HttpStatus.NOT_FOUND));
            return new UserResponse(user);
        } catch (UserException userException) {
            throw userException;
        } catch (Exception exception) {
            throw new UserException(MessagesConstant.ERROR_GENERIC.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public UserResponse findByEmail(String email) {
        try {
            User user = this.userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserException(MessagesConstant.ERROR_USER_NOT_FOUND.getValor(), HttpStatus.NOT_FOUND));
            return new UserResponse(user);
        } catch (UserException userException) {
            throw userException;
        } catch (Exception exception) {
            throw new UserException(MessagesConstant.ERROR_GENERIC.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        try {
            List<User> userList = this.userRepository.findAll();
            return UserResponse.Collection(userList);
        } catch (Exception exception) {
            throw new UserException(MessagesConstant.ERROR_GENERIC.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
