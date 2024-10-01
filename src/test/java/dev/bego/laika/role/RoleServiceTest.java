package dev.bego.laika.role;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.bego.laika.roles.Role;
import dev.bego.laika.roles.RoleRepository;
import dev.bego.laika.roles.RoleService;
import dev.bego.laika.roles.role_exceptions.RoleNotFoundException;

class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        role = new Role(1L, "USER"); 
    }

    @Test
    void testGetById_RoleExists() {
        
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        
        Role foundRole = roleService.getById(1L);

        
        assertNotNull(foundRole);
        assertEquals(role.getId(), foundRole.getId());
        assertEquals(role.getName(), foundRole.getName()); 
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    void testGetById_RoleDoesNotExist() {
       
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        
        assertThrows(RoleNotFoundException.class, () -> roleService.getById(1L));
        verify(roleRepository, times(1)).findById(1L);
    }
}

