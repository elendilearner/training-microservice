package com.education.trainingmicroservice;

import com.api.dto.User;
import com.education.trainingmicroservice.model.UserEntity;
import com.education.trainingmicroservice.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RequiredArgsConstructor
class UserCrudControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserEntityRepository userEntityRepository;
    private Long userId;

    @BeforeEach
    void initData() {
        User user = new User();
        user.setUsername("John Doe");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPhone("123-456-7890");

        ResponseEntity<User> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/user",
                user,
                User.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        this.userId = response.getBody().getId();
    }

    @AfterEach
    void clearData() {
        userEntityRepository.deleteAllInBatch();
    }

    @Test
    void testGetUser() {
        ResponseEntity<User> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/user/" + userId,
                User.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals("John Doe", requireNonNull(response.getBody()).getUsername())
        );
    }

    @Test
    void testDeleteUser() {
        restTemplate.delete("http://localhost:" + port + "/api/v1/user/" + userId);

        assertFalse(userEntityRepository.existsById(userId));
    }

    @Test
    void testUpdateUser() {
        User updateUser = new User();
        updateUser.setUsername("Jane Doe");
        updateUser.setFirstName("Jane");
        updateUser.setLastName("Doe");
        updateUser.setEmail("janedoe@example.com");
        updateUser.setPhone("987-654-3210");

        HttpEntity<User> entity = new HttpEntity<>(updateUser);
        ResponseEntity<User> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/user/" + userId,
                HttpMethod.PUT,
                entity,
                User.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals("Jane Doe", requireNonNull(response.getBody()).getUsername()),
                () -> assertEquals("Jane", requireNonNull(response.getBody()).getFirstName()),
                () -> assertEquals("Doe", requireNonNull(response.getBody()).getLastName()),
                () -> assertEquals("janedoe@example.com", requireNonNull(response.getBody()).getEmail()),
                () -> assertEquals("987-654-3210", requireNonNull(response.getBody()).getPhone())
        );

        UserEntity updatedUserEntity = userEntityRepository.findById(userId)
                .orElseThrow(() -> new AssertionError("User should exist"));

        assertAll(
                () -> assertEquals(updateUser.getUsername(), updatedUserEntity.getUsername()),
                () -> assertEquals(updateUser.getFirstName(), updatedUserEntity.getFirstName()),
                () -> assertEquals(updateUser.getLastName(), updatedUserEntity.getLastName()),
                () -> assertEquals(updateUser.getEmail(), updatedUserEntity.getEmail()),
                () -> assertEquals(updateUser.getPhone(), updatedUserEntity.getPhone())
        );
    }

}
