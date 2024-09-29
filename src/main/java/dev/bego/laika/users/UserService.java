package dev.bego.laika.users;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import dev.bego.laika.facades.EncoderFacade;
import dev.bego.laika.roles.Role;
import dev.bego.laika.roles.RoleService;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService, EncoderFacade encoderFacade) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Set<Role> assignDefaultRole() {
        Role defaultRole = roleService.getById(1L);

        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);

        return roles;
    }
}