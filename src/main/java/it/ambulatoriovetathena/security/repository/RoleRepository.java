package it.ambulatoriovetathena.security.repository;

import it.ambulatoriovetathena.security.domains.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(String role);

}
