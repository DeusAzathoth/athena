package it.ambulatoriovetathena.security.services;

import it.ambulatoriovetathena.security.domains.User;
import it.ambulatoriovetathena.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

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

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    /*
    public List<User> fetchUsers(int offset, int limit) {
        return userRepository.fetchUsers(offset, limit);
    }

    public int getUserCount() {
        return userRepository.getUserCount();
    }
     */

}
