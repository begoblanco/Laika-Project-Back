package dev.bego.laika.users;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.bego.laika.roles.Role;
import dev.bego.laika.roles.RoleService;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder().id(1L).username("testUser").build();
        role = new Role(1L, "USER"); 
    }

    @Test
    void testFindById_UserExists() {
        // Preparar el mock
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Llamar al método a probar
        Optional<User> foundUser = userService.findById(1L);

        // Verificar los resultados
        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_UserDoesNotExist() {
        // Preparar el mock
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Llamar al método a probar
        Optional<User> foundUser = userService.findById(1L);

        // Verificar que no se encontró el usuario
        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByUsername_UserExists() {
        // Preparar el mock
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        // Llamar al método a probar
        Optional<User> foundUser = userService.findByUsername("testUser");

        // Verificar los resultados
        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void testFindByUsername_UserDoesNotExist() {
        // Preparar el mock
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        // Llamar al método a probar
        Optional<User> foundUser = userService.findByUsername("testUser");

        // Verificar que no se encontró el usuario
        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void testAssignDefaultRole() {
        // Preparar el mock para obtener el rol
        when(roleService.getById(1L)).thenReturn(role);

        // Llamar al método a probar
        Set<Role> roles = userService.assignDefaultRole();

        // Verificar que se ha asignado el rol correctamente
        assertEquals(1, roles.size());
        assertTrue(roles.contains(role));
        verify(roleService, times(1)).getById(1L);
    }
}
