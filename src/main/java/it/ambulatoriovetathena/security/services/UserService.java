package it.ambulatoriovetathena.security.services;

import it.ambulatoriovetathena.security.domains.User;
import it.ambulatoriovetathena.security.repository.RoleRepository;
import it.ambulatoriovetathena.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        //this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByUsername(String username) {
        System.out.println("UserService - findByUsername: " + username);
        User user = userRepository.findByUsername(username);
        System.out.println("User found: " + user.toString());
        return user;
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
