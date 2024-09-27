package dev.bego.laika.users;

import java.util.*;

import org.springframework.stereotype.Service;

import dev.bego.laika.auth.RegisterDto;
import dev.bego.laika.facades.EncoderFacade;
import dev.bego.laika.implementations.IEncryptFacade;
import dev.bego.laika.roles.Role;
import dev.bego.laika.roles.RoleService;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final IEncryptFacade encoderFacade;

    public UserService(UserRepository userRepository, RoleService roleService, EncoderFacade encoderFacade) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.encoderFacade = encoderFacade;
    }

    public User save(RegisterDto newRegisterDto) {
        String passwordDecoded = encoderFacade.decode("base64", newRegisterDto.getPassword());
        String passwordEncoded = encoderFacade.encode("bcrypt", passwordDecoded);

        User user = new User(newRegisterDto.getUsername(), passwordEncoded);
        user.setRoles(assignDefaultRole());

        User savedUser = userRepository.save(user);

        return savedUser;
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