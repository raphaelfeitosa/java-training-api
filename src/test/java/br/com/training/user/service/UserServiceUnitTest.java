package br.com.training.user.service;

import br.com.training.model.User;
import br.com.training.repository.UserRepository;
import br.com.training.v1.constant.MessagesConstant;
import br.com.training.v1.dto.request.UserRequest;
import br.com.training.v1.dto.response.UserResponse;
import br.com.training.v1.service.UserService;
import br.com.training.v1.service.exceptions.UserException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static User user;

    @BeforeAll
    public static void init() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("johndoe@gmail.com");
        user.setCpf("80257566023");
        user.setBirthDate(LocalDate.parse("1989-09-29", formatter));
    }

    /**
     * Inicio dos testes na Camada UserService com sucesso
     */

    @Test
    @Order(1)
    void testListarUserComSucesso() {
        List<User> userList = new ArrayList<>();
        userList.add(user);

        Mockito.when(this.userRepository.findAll()).thenReturn(userList);
        List<UserResponse> userResponseList = this.userService.findAll();

        Assertions.assertNotNull(userResponseList);
        Assertions.assertEquals(1, userResponseList.get(0).getId());
        Assertions.assertEquals("John Doe", userResponseList.get(0).getName());
        Assertions.assertEquals("johndoe@gmail.com", userResponseList.get(0).getEmail());
        Assertions.assertEquals("80257566023", userResponseList.get(0).getCpf());
        Assertions.assertEquals("1989-09-29", userResponseList.get(0).getBirthDate().toString());
    }

    @Test
    @Order(2)
    void testFindByIdUserComSucesso() {
        Mockito.when(this.userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserResponse userResponse = this.userService.findById(1L);

        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals(1L, userResponse.getId());
        Assertions.assertEquals("John Doe", userResponse.getName());
        Assertions.assertEquals("johndoe@gmail.com", userResponse.getEmail());
        Assertions.assertEquals("80257566023", userResponse.getCpf());
        Assertions.assertEquals("1989-09-29", userResponse.getBirthDate().toString());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    @Order(3)
    void testFindByCpfUserComSucesso() {
        Mockito.when(this.userRepository.findByCpf("80257566023")).thenReturn(Optional.of(user));
        UserResponse userResponse = this.userService.findByCpf("80257566023");

        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals(1L, userResponse.getId());
        Assertions.assertEquals("John Doe", userResponse.getName());
        Assertions.assertEquals("johndoe@gmail.com", userResponse.getEmail());
        Assertions.assertEquals("80257566023", userResponse.getCpf());
        Assertions.assertEquals("1989-09-29", userResponse.getBirthDate().toString());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByCpf("80257566023");
    }

    @Test
    @Order(4)
    void testFindByEmailUserComSucesso() {
        Mockito.when(this.userRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.of(user));
        UserResponse userResponse = this.userService.findByEmail("johndoe@gmail.com");

        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals(1L, userResponse.getId());
        Assertions.assertEquals("John Doe", userResponse.getName());
        Assertions.assertEquals("johndoe@gmail.com", userResponse.getEmail());
        Assertions.assertEquals("80257566023", userResponse.getCpf());
        Assertions.assertEquals("1989-09-29", userResponse.getBirthDate().toString());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail("johndoe@gmail.com");
    }

    @Test
    @Order(5)
    void testExluirUserComSucesso() {
        Mockito.when(this.userRepository.findByCpf("80257566023")).thenReturn(Optional.of(user));
        this.userService.excluir("80257566023");

        Mockito.verify(this.userRepository, Mockito.times(1)).findByCpf("80257566023");
        Mockito.verify(this.userRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    @Order(6)
    void testCadastrarComSucesso() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Maria Doe");
        userRequest.setEmail("mariadoe@gmail.com");
        userRequest.setCpf("75652737085");
        userRequest.setBirthDate("1985-08-15");

        Mockito.when(this.userRepository.findByCpf("75652737085")).thenReturn(Optional.ofNullable(null));
        Mockito.when(this.userRepository.findByEmail("mariadoe@gmail.com")).thenReturn(Optional.ofNullable(null));
        Mockito.when(this.userRepository.save(userRequest.toUserCreate())).thenReturn(userRequest.toUserCreate());

        UserResponse newUser = this.userService.salvar(userRequest);

        Assertions.assertNotNull(newUser);
        Assertions.assertEquals("Maria Doe", newUser.getName());
        Assertions.assertEquals("mariadoe@gmail.com", newUser.getEmail());
        Assertions.assertEquals("75652737085", newUser.getCpf());
        Assertions.assertEquals("1985-08-15", newUser.getBirthDate().toString());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByCpf("75652737085");
        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail("mariadoe@gmail.com");
        Mockito.verify(this.userRepository, Mockito.times(1)).save(userRequest.toUserCreate());
    }

    @Test
    @Order(7)
    void testAtualizarComSucesso() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Maria Doe");
        userRequest.setEmail("johndoe@gmail.com");
        userRequest.setCpf("80257566023");
        userRequest.setBirthDate("1989-09-29");

        Mockito.when(this.userRepository.findByCpf("80257566023")).thenReturn(Optional.of(user));
        Mockito.when(this.userRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.of(user));
        Mockito.when(this.userRepository.save(userRequest.toUserUpdate(1L))).thenReturn(userRequest.toUserUpdate(1L));

        UserResponse newUser = this.userService.atualizar("80257566023", userRequest);

        Assertions.assertNotNull(newUser);
        Assertions.assertEquals("Maria Doe", newUser.getName());
        Assertions.assertEquals("johndoe@gmail.com", newUser.getEmail());
        Assertions.assertEquals("80257566023", newUser.getCpf());
        Assertions.assertEquals("1989-09-29", newUser.getBirthDate().toString());

        Mockito.verify(this.userRepository, Mockito.times(2)).findByCpf("80257566023");
        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail("johndoe@gmail.com");
        Mockito.verify(this.userRepository, Mockito.times(1)).save(userRequest.toUserUpdate(1L));
    }

    /**
     * Inicio dos testes na Camada UserService UserException
     */

    @Test
    @Order(8)
    void testCadastrarComCpfExistenteThrowUserException() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setEmail("johndoe@gmail.com");
        userRequest.setCpf("80257566023");
        userRequest.setBirthDate("1989-09-29");

        Mockito.when(this.userRepository.findByCpf("80257566023")).thenReturn(Optional.of(user));

        UserException userException;

        userException = Assertions.assertThrows(UserException.class, () -> {
            this.userService.salvar(userRequest);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, userException.getHttpStatus());
        Assertions.assertEquals(MessagesConstant.ERROR_USER_CPF_REGISTERED.getValor(), userException.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByCpf("80257566023");
        Mockito.verify(this.userRepository, Mockito.times(0)).save(user);
    }

    @Test
    @Order(9)
    void testCadastrarComEmailExistenteThrowUserException() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setEmail("johndoe@gmail.com");
        userRequest.setCpf("80257566023");
        userRequest.setBirthDate("1989-09-29");

        Mockito.when(this.userRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.of(user));

        UserException userException;

        userException = Assertions.assertThrows(UserException.class, () -> {
            this.userService.salvar(userRequest);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, userException.getHttpStatus());
        Assertions.assertEquals(MessagesConstant.ERROR_USER_EMAIL_REGISTERED.getValor(), userException.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail("johndoe@gmail.com");
        Mockito.verify(this.userRepository, Mockito.times(0)).save(user);
    }

    @Test
    @Order(10)
    void testAtualizarComEmailExistenteThrowUserException() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Maria Doe");
        userRequest.setEmail("maria@gmail.com");
        userRequest.setCpf("80257566021");
        userRequest.setBirthDate("1989-09-29");

        Mockito.when(this.userRepository.findByCpf("80257566023")).thenReturn(Optional.of(user));
        Mockito.when(this.userRepository.findByEmail("maria@gmail.com")).thenReturn(Optional.of(userRequest.toUserUpdate(1L)));

        UserException userException;

        userException = Assertions.assertThrows(UserException.class, () -> {
            this.userService.atualizar("80257566023", userRequest);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, userException.getHttpStatus());
        Assertions.assertEquals(MessagesConstant.ERROR_USER_EMAIL_REGISTERED.getValor(), userException.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByCpf("80257566023");
        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail("maria@gmail.com");
        Mockito.verify(this.userRepository, Mockito.times(0)).save(userRequest.toUserUpdate(1L));
    }

    @Test
    @Order(11)
    void testAtualizarComCpfExistenteThrowUserException() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Maria Doe");
        userRequest.setEmail("maria@gmail.com");
        userRequest.setCpf("80257566021");
        userRequest.setBirthDate("1989-09-29");

        Mockito.when(this.userRepository.findByCpf("80257566023")).thenReturn(Optional.of(user));
        Mockito.when(this.userRepository.findByCpf("80257566021")).thenReturn(Optional.of(userRequest.toUserUpdate(1L)));

        UserException userException;

        userException = Assertions.assertThrows(UserException.class, () -> {
            this.userService.atualizar("80257566023", userRequest);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, userException.getHttpStatus());
        Assertions.assertEquals(MessagesConstant.ERROR_USER_CPF_REGISTERED.getValor(), userException.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByCpf("80257566023");
        Mockito.verify(this.userRepository, Mockito.times(1)).findByCpf("80257566021");
        Mockito.verify(this.userRepository, Mockito.times(0)).save(userRequest.toUserUpdate(1L));
    }

    @Test
    @Order(12)
    void testFindByIdThrowUserException() {
        Mockito.when(this.userRepository.findById(1L)).thenReturn(Optional.empty());

        UserException userException;

        userException = Assertions.assertThrows(UserException.class, () -> {
            this.userService.findById(1L);
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, userException.getHttpStatus());
        Assertions.assertEquals(MessagesConstant.ERROR_USER_NOT_FOUND.getValor(), userException.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    @Order(13)
    void testFindByCpfThrowUserException() {
        Mockito.when(this.userRepository.findByCpf("80257566023")).thenReturn(Optional.empty());

        UserException userException;

        userException = Assertions.assertThrows(UserException.class, () -> {
            this.userService.findByCpf("80257566023");
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, userException.getHttpStatus());
        Assertions.assertEquals(MessagesConstant.ERROR_USER_NOT_FOUND.getValor(), userException.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByCpf("80257566023");
    }

    @Test
    @Order(14)
    void testFindByEmailThrowUserException() {
        Mockito.when(this.userRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.empty());

        UserException userException;

        userException = Assertions.assertThrows(UserException.class, () -> {
            this.userService.findByEmail("johndoe@gmail.com");
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, userException.getHttpStatus());
        Assertions.assertEquals(MessagesConstant.ERROR_USER_NOT_FOUND.getValor(), userException.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail("johndoe@gmail.com");
    }

    @Test
    @Order(15)
    void testExcluirThrowUserException() {
        Mockito.when(this.userRepository.findByCpf("80257566023")).thenReturn(Optional.empty());

        UserException userException;

        userException = Assertions.assertThrows(UserException.class, () -> {
            this.userService.excluir("80257566023");
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, userException.getHttpStatus());
        Assertions.assertEquals(MessagesConstant.ERROR_USER_NOT_FOUND.getValor(), userException.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByCpf("80257566023");
        Mockito.verify(this.userRepository, Mockito.times(0)).deleteById(1L);
    }

    /**
     * Inicio dos testes na Camada UserService GenericException
     */

    @Test
    @Order(6)
    void testCadastrarThrowUserGenericException() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setEmail("johndoe@gmail.com");
        userRequest.setCpf("80257566023");
        userRequest.setBirthDate("1989-09-29");

        Mockito.when(this.userRepository.findByCpf("80257566023")).thenReturn(Optional.ofNullable(null));
        Mockito.when(this.userRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.ofNullable(null));
        Mockito.when(this.userRepository.save(userRequest.toUserCreate())).thenThrow(IllegalStateException.class);

        UserException userException;

        userException = Assertions.assertThrows(UserException.class, () -> {
            this.userService.salvar(userRequest);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, userException.getHttpStatus());
        Assertions.assertEquals(MessagesConstant.ERROR_GENERIC.getValor(), userException.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByCpf("80257566023");
        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail("johndoe@gmail.com");
        Mockito.verify(this.userRepository, Mockito.times(1)).save(userRequest.toUserCreate());
    }

    @Test
    @Order(17)
    void testAtualizarThrowUserGenericException() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Maria Doe");
        userRequest.setEmail("johndoe@gmail.com");
        userRequest.setCpf("80257566023");
        userRequest.setBirthDate("1989-09-29");

        Mockito.when(this.userRepository.findByCpf("80257566023")).thenReturn(Optional.of(user));
        Mockito.when(this.userRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.of(user));
        Mockito.when(this.userRepository.save(userRequest.toUserUpdate(1L))).thenThrow(IllegalStateException.class);

        UserException userException;

        userException = Assertions.assertThrows(UserException.class, () -> {
            this.userService.atualizar("80257566023", userRequest);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, userException.getHttpStatus());
        Assertions.assertEquals(MessagesConstant.ERROR_GENERIC.getValor(), userException.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(2)).findByCpf("80257566023");
        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail("johndoe@gmail.com");
        Mockito.verify(this.userRepository, Mockito.times(1)).save(userRequest.toUserUpdate(1L));
    }

    @Test
    @Order(18)
    void testExluirThrowUserGenericException() {
        Mockito.when(this.userRepository.findByCpf("80257566023")).thenReturn(Optional.of(user));
        Mockito.doThrow(IllegalStateException.class).when(this.userRepository).deleteById(1L);

        UserException userException;

        userException = Assertions.assertThrows(UserException.class, () -> {
            this.userService.excluir("80257566023");
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, userException.getHttpStatus());
        Assertions.assertEquals(MessagesConstant.ERROR_GENERIC.getValor(), userException.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByCpf("80257566023");
        Mockito.verify(this.userRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    @Order(19)
    void testFindByEmailThrowUserGenericException() {
        Mockito.doThrow(IllegalStateException.class).when(this.userRepository).findByEmail("johndoe@gmail.com");

        UserException userException;

        userException = Assertions.assertThrows(UserException.class, () -> {
            this.userService.findByEmail("johndoe@gmail.com");
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, userException.getHttpStatus());
        Assertions.assertEquals(MessagesConstant.ERROR_GENERIC.getValor(), userException.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail("johndoe@gmail.com");
    }

    @Test
    @Order(20)
    void testFindByIdThrowUserGenericException() {
        Mockito.doThrow(IllegalStateException.class).when(this.userRepository).findById(1L);

        UserException userException;

        userException = Assertions.assertThrows(UserException.class, () -> {
            this.userService.findById(1L);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, userException.getHttpStatus());
        Assertions.assertEquals(MessagesConstant.ERROR_GENERIC.getValor(), userException.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(1L);
    }

    @Test@Order(21)
    void testFindByCpfThrowUserGenericException() {
        Mockito.doThrow(IllegalStateException.class).when(this.userRepository).findByCpf("80257566023");

        UserException userException;

        userException = Assertions.assertThrows(UserException.class, () -> {
            this.userService.findByCpf("80257566023");
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, userException.getHttpStatus());
        Assertions.assertEquals(MessagesConstant.ERROR_GENERIC.getValor(), userException.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByCpf("80257566023");
    }

    @Test
    @Order(22)
    void testFindAllThrowUserGenericException() {
        Mockito.doThrow(IllegalStateException.class).when(this.userRepository).findAll();

        UserException userException;

        userException = Assertions.assertThrows(UserException.class, () -> {
            this.userService.findAll();
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, userException.getHttpStatus());
        Assertions.assertEquals(MessagesConstant.ERROR_GENERIC.getValor(), userException.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findAll();
    }
}
