package com.thatdrw.customerdetailsservice.service;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.thatdrw.customerdetailsservice.entity.User;
import com.thatdrw.customerdetailsservice.exception.EntityNotFoundException;
import com.thatdrw.customerdetailsservice.repository.UserRepository;
import com.thatdrw.customerdetailsservice.service.impl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @Before
    public void setUp() {
        user = new User("user", "pass");
    }

    @Test
    public void getUserFromRepoByIdTest() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User result = userService.getUser(user.getId());

        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void getUserFromRepoByNameTest() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        User result = userService.getUser(user.getUsername());

        assertEquals(user, result);
    }

    @Test
    public void saveUserTest() {
        when(userRepository.save(user)).thenReturn(user);
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("mockedhashedpass");

        User result = userService.saveUser(user);

        assertEquals(user, result);
    }

    @Test
    public void unwrapUserTest() {
        Optional<User> optuser = Optional.of(user);
        Optional<User> emptyOptUser = Optional.empty();

        User result = UserServiceImpl.unwrapUser(optuser, user.getId());
        assertEquals(user, result);
        assertThrows(EntityNotFoundException.class, () -> {
            UserServiceImpl.unwrapUser(emptyOptUser, -1L);
        });
    }

}
