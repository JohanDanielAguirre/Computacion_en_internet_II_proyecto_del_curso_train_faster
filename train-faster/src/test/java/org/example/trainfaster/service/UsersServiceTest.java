package org.example.trainfaster.service;

import jakarta.transaction.Transactional;
import org.example.trainfaster.model.*;
import org.example.trainfaster.repositories.UserRepository;
import org.example.trainfaster.services.implementations.UserServiceImp;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UsersServiceTest {

    @Mock
    private UserRepository usersRepository;

    @InjectMocks
    private UserServiceImp userService;

    @BeforeEach
    public void setUp() {
        Users user = new Users();
        user.setUsername("mrzen");
        user.setPassword("123");
        user.setEmail("si@gmail.com");
        user.setName("johan");
        user.setLastName("aguirre");
        usersRepository.save(user);

        when(usersRepository.findByUsername("mrzen")).thenReturn(Optional.of(user));
    }


    @Test
    public void testAuthenticationUserCorrect() {
        Optional<Users> user = usersRepository.findByUsername("mrzen");
        assertNotNull(user);
        Users user2 = userService.authenticationUser(user.get().getUsername(), user.get().getPassword());
        assertNotNull(user);
        assertEquals(user.get().getUsername(), user2.getUsername());
        assertEquals(user.get().getPassword(), user2.getPassword());
        assertEquals(user.get().getEmail(), user2.getEmail());
        assertEquals(user.get().getName(), user2.getName());
        assertEquals(user.get().getLastName(), user2.getLastName());
    }

    @Test
    public void testAuthenticationUserIncorrect() {
        Users user2 = userService.authenticationUser("fundidonegro", "siry");
        assertNull(user2);
    }
    @Test
    public void testAuthenticationUserIncorrectSqlInjection() {
        Users user3 = userService.authenticationUser("rafaela sofia", "siry' OR '1'='1");
        assertNull(user3);
    }
}
