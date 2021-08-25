package br.com.training.user.controller;


import br.com.training.model.User;
import br.com.training.repository.UserRepository;
import br.com.training.v1.dto.request.UserRequest;
import br.com.training.v1.dto.response.UserResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerIntegratedTest {

    private final String urlTemplate = "/v1/users";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private static User user;

    @BeforeEach
    public void init() {
        this.createDataBase();
    }

    @AfterEach
    public void finish() {
        this.userRepository.deleteAll();
    }

    private void createDataBase() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        User userJohn = new User();
        userJohn.setName("John Doe");
        userJohn.setEmail("johndoe@gmail.com");
        userJohn.setCpf("80257566023");
        userJohn.setBirthDate(LocalDate.parse("1989-09-29", formatter));

        User userMaria = new User();
        userMaria.setName("Maria Doe");
        userMaria.setEmail("mariadoe@gmail.com");
        userMaria.setCpf("75652737085");
        userMaria.setBirthDate(LocalDate.parse("1985-08-15", formatter));

        this.userRepository.saveAll(Arrays.asList(userJohn, userMaria));
    }

    @Test
    @Order(1)
    @DisplayName("200 - getUserById")
    void getUserFindByIdWhenShouldReturnStatusCode200() {
        List<User> userList = this.userRepository.findAll();
        Long id = userList.get(0).getId();
        ResponseEntity<UserResponse> userResponse = restTemplate.getForEntity(urlTemplate + "/{id}", UserResponse.class, id);
        Assertions.assertEquals(200, userResponse.getStatusCodeValue());
        Assertions.assertEquals("John Doe", userResponse.getBody().getName());
        Assertions.assertEquals("johndoe@gmail.com", userResponse.getBody().getEmail());
        Assertions.assertEquals("80257566023", userResponse.getBody().getCpf());
        Assertions.assertEquals("1989-09-29", userResponse.getBody().getBirthDate().toString());
    }

    @Test
    @Order(2)
    @DisplayName("404 - getUserById")
    void getUserFindByIdWhenShouldReturnStatusCode404() {
        ResponseEntity<UserResponse> userResponse = restTemplate.getForEntity(urlTemplate + "/{id}", UserResponse.class, -1L);
        Assertions.assertEquals(404, userResponse.getStatusCodeValue());
    }

    @Test
    @Order(3)
    @DisplayName("200 - getUserByCpf")
    void getUserFindByCpfWhenShouldReturnStatusCode200() {
        ResponseEntity<UserResponse> userResponse = restTemplate.getForEntity(urlTemplate + "/cpf/{cpf}", UserResponse.class, "80257566023");
        Assertions.assertEquals(200, userResponse.getStatusCodeValue());
        Assertions.assertEquals("John Doe", userResponse.getBody().getName());
        Assertions.assertEquals("johndoe@gmail.com", userResponse.getBody().getEmail());
        Assertions.assertEquals("80257566023", userResponse.getBody().getCpf());
        Assertions.assertEquals("1989-09-29", userResponse.getBody().getBirthDate().toString());
    }

    @Test
    @Order(4)
    @DisplayName("404 - getUserByCpf")
    void getUserFindByCpfWhenShouldReturnStatusCode404() {
        ResponseEntity<UserResponse> userResponse = restTemplate.getForEntity(urlTemplate + "/cpf/{cpf}", UserResponse.class, "NON_CPF");
        Assertions.assertEquals(404, userResponse.getStatusCodeValue());
    }

    @Test
    @Order(5)
    @DisplayName("200 - getUserByEmail")
    void getUserFindByEmailWhenShouldReturnStatusCode200() {
        ResponseEntity<UserResponse> userResponse = restTemplate.getForEntity(urlTemplate + "/email/{email}", UserResponse.class, "johndoe@gmail.com");
        Assertions.assertEquals(200, userResponse.getStatusCodeValue());
        Assertions.assertEquals("John Doe", userResponse.getBody().getName());
        Assertions.assertEquals("johndoe@gmail.com", userResponse.getBody().getEmail());
        Assertions.assertEquals("80257566023", userResponse.getBody().getCpf());
        Assertions.assertEquals("1989-09-29", userResponse.getBody().getBirthDate().toString());
    }

    @Test
    @Order(6)
    @DisplayName("404 - getUserByEmail")
    void getUserFindByEmailWhenShouldReturnStatusCode404() {
        ResponseEntity<UserResponse> userResponse = restTemplate.getForEntity(urlTemplate + "/email/{email}", UserResponse.class, "johndoe@email.com");
        Assertions.assertEquals(404, userResponse.getStatusCodeValue());
    }

    @Test
    @Order(7)
    @DisplayName("200 - getAllUser")
    void testFindAllUsersWhenShouldReturnStatusCode200() {
        ResponseEntity<List<UserResponse>> userListResponse = restTemplate
                .exchange(urlTemplate, HttpMethod.GET,
                        null, new ParameterizedTypeReference<List<UserResponse>>() {
                        });
        Assertions.assertEquals(200, userListResponse.getStatusCodeValue());
        Assertions.assertEquals(2, userListResponse.getBody().size());
        Assertions.assertNotNull(userListResponse.getBody());

    }

    @Test
    @Order(8)
    @DisplayName("204 - deleteUser")
    void testDeleteUserExistsShouldReturnStatusCode200() {
        ResponseEntity<String> exchange = restTemplate.exchange(urlTemplate + "/{cpf}", HttpMethod.DELETE, null, String.class, "80257566023");
        Assertions.assertEquals(204, exchange.getStatusCodeValue());
    }

    @Test
    @Order(9)
    @DisplayName("404 - deleteUser")
    void testDeleteUserDoesNotExistsShouldReturnStatusCode204() {
        ResponseEntity<String> exchange = restTemplate.exchange(urlTemplate + "/{cpf}", HttpMethod.DELETE, null, String.class, "NON_CPF");
        Assertions.assertEquals(404, exchange.getStatusCodeValue());
    }

    @Test
    @Order(10)
    @DisplayName("200 - createUser")
    void testCreateUserWhenShouldReturnStatusCode201() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        User newUser = new User();
        newUser.setName("Paul Doe");
        newUser.setEmail("pauldoe@gmail.com");
        newUser.setCpf("66921484050");
        newUser.setBirthDate(LocalDate.parse("1985-10-05", formatter));

        ResponseEntity<User> newUserResponse = restTemplate.postForEntity(urlTemplate, newUser, User.class);

        User user = this.userRepository.findByCpf("66921484050").get();

        Assertions.assertEquals(201, newUserResponse.getStatusCodeValue());
        Assertions.assertNotNull(newUserResponse.getBody());
        Assertions.assertEquals(user.getId(), newUserResponse.getBody().getId());
        Assertions.assertEquals("Paul Doe", newUserResponse.getBody().getName());
        Assertions.assertEquals("pauldoe@gmail.com", newUserResponse.getBody().getEmail());
        Assertions.assertEquals("66921484050", newUserResponse.getBody().getCpf());
        Assertions.assertEquals("1985-10-05", newUserResponse.getBody().getBirthDate().toString());
    }

    @Test
    @Order(11)
    @DisplayName("400 - createUser - Fields is Null or Empty")
    void testCreateUserContainsFieldsIsNullOrEmptyWhenShouldReturnStatusCode400() {
        UserRequest newUser = new UserRequest();
        newUser.setName("");
        newUser.setEmail(null);
        newUser.setCpf(null);
        newUser.setBirthDate("");

        ResponseEntity<String> newUserResponse = restTemplate.postForEntity(urlTemplate, newUser, String.class);
        Assertions.assertEquals(400, newUserResponse.getStatusCodeValue());
        Assertions.assertTrue(newUserResponse.getBody().contains("O campo name é obrigatório"));
        Assertions.assertTrue(newUserResponse.getBody().contains("O campo cpf é obrigatório"));
        Assertions.assertTrue(newUserResponse.getBody().contains("O campo email é obrigatório"));
        Assertions.assertTrue(newUserResponse.getBody().contains("O campo birthDate é obrigatório. formato: yyyy-MM-dd"));
    }

    @Test
    @Order(12)
    @DisplayName("400 - createUser - Cpf and Email invalid")
    void testCreateUserContainsFieldsCpfAndEmailInvalidWhenShouldReturnStatusCode400() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        User newUser = new User();
        newUser.setName("John Doe");
        newUser.setEmail("Invalid Email");
        newUser.setCpf("Invalid CPF");
        newUser.setBirthDate(LocalDate.parse("1985-10-05", formatter));

        ResponseEntity<String> newUserResponse = restTemplate.postForEntity(urlTemplate, newUser, String.class);

        Assertions.assertEquals(400, newUserResponse.getStatusCodeValue());
        Assertions.assertTrue(newUserResponse.getBody().contains("CPF inválido"));
        Assertions.assertTrue(newUserResponse.getBody().contains("E-mail inválido!"));

    }

    @Test
    @Order(13)
    @DisplayName("200 - updateUser")
    void testUpdateUserWhenShouldReturnStatusCode201() {
        UserRequest updateUser = new UserRequest();
        updateUser.setName("John Doe de Souza");
        updateUser.setEmail("johndoe@gmail.com");
        updateUser.setCpf("80257566023");
        updateUser.setBirthDate("1985-10-05");

        Long id = this.userRepository.findByCpf("80257566023").get().getId();

        ResponseEntity<UserResponse> updateUserResponse = restTemplate.exchange(urlTemplate + "/80257566023", HttpMethod.PUT, new HttpEntity<>(updateUser), UserResponse.class);

        Assertions.assertEquals(200, updateUserResponse.getStatusCodeValue());
        Assertions.assertNotNull(updateUserResponse.getBody());
        Assertions.assertEquals(id, updateUserResponse.getBody().getId());
        Assertions.assertEquals("John Doe de Souza", updateUserResponse.getBody().getName());
        Assertions.assertEquals("johndoe@gmail.com", updateUserResponse.getBody().getEmail());
        Assertions.assertEquals("80257566023", updateUserResponse.getBody().getCpf());
        Assertions.assertEquals("1985-10-05", updateUserResponse.getBody().getBirthDate().toString());
    }

    @Test
    @Order(14)
    @DisplayName("404 - updateUser - User not found")
    void testUpdateUserCpfURNNotFoundWhenShouldReturnStatusCode404() {
        UserRequest updateUser = new UserRequest();
        updateUser.setName("John Doe de Souza");
        updateUser.setEmail("johndoe@gmail.com");
        updateUser.setCpf("80257566023");
        updateUser.setBirthDate("1985-10-05");
        ResponseEntity<String> updateUserResponse = restTemplate.exchange(urlTemplate + "/cpf_not_found", HttpMethod.PUT, new HttpEntity<>(updateUser), String.class);
        Assertions.assertEquals(404, updateUserResponse.getStatusCodeValue());
        Assertions.assertTrue(updateUserResponse.getBody().contains("User not found."));
    }

    @Test
    @Order(15)
    @DisplayName("400 - updateUser - CPF already registered.")
    void testUpdateUserCpfExistsWhenShouldReturnStatusCode400() {
        UserRequest updateUser = new UserRequest();
        updateUser.setName("Maria Doe de Souza");
        updateUser.setEmail("mariadoe@gmail.com");
        updateUser.setCpf("80257566023");
        updateUser.setBirthDate("1985-10-05");
        ResponseEntity<String> updateUserResponse = restTemplate.exchange(urlTemplate + "/75652737085", HttpMethod.PUT, new HttpEntity<>(updateUser), String.class);
        Assertions.assertEquals(400, updateUserResponse.getStatusCodeValue());
        Assertions.assertTrue(updateUserResponse.getBody().contains("CPF already registered."));
    }

    @Test
    @Order(16)
    @DisplayName("400 - updateUser - Email already registered.")
    void testUpdateUserEmailExistsWhenShouldReturnStatusCode400() {
        UserRequest updateUser = new UserRequest();
        updateUser.setName("Maria Doe de Souza");
        updateUser.setEmail("johndoe@gmail.com");
        updateUser.setCpf("75652737085");
        updateUser.setBirthDate("1985-10-05");
        ResponseEntity<String> updateUserResponse = restTemplate.exchange(urlTemplate + "/75652737085", HttpMethod.PUT, new HttpEntity<>(updateUser), String.class);
        Assertions.assertEquals(400, updateUserResponse.getStatusCodeValue());
        Assertions.assertTrue(updateUserResponse.getBody().contains("Email already registered."));
    }
}
