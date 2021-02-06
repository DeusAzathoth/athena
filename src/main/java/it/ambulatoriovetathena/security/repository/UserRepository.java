package it.ambulatoriovetathena.security.repository;

import it.ambulatoriovetathena.security.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByUsername(String username);
    //List<User> fetchUsers(int offset, int limit);
    //int getUserCount();

}
