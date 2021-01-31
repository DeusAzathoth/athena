package it.ambulatoriovetathena.security.repository;

import it.ambulatoriovetathena.security.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByUsername(String username);

}
