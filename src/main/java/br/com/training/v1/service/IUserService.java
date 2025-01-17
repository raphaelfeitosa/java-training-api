package br.com.training.v1.service;

import br.com.training.v1.dto.request.UserRequest;
import br.com.training.v1.dto.response.UserResponse;

import java.util.List;

public interface IUserService {
    UserResponse salvar(final UserRequest userRequest);
    UserResponse atualizar(final String cpf, UserRequest userRequest);
    void excluir(final String cpf);
    UserResponse findByCpf(final String cpf);
    UserResponse findById(final Long id);
    UserResponse findByEmail(final String email);
    List<UserResponse> findAll();
}
