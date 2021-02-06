package it.ambulatoriovetathena.security.services;

import it.ambulatoriovetathena.security.domains.Role;
import it.ambulatoriovetathena.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;

    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

}
