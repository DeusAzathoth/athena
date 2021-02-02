package it.ambulatoriovetathena.security.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SSUser extends User {

    private final it.ambulatoriovetathena.security.domains.User user;

    public SSUser(String username, String password,
                  boolean enabled, boolean accountNonExpired,
                  boolean credentialsNonExpired, boolean accountNonLocked,
                  Collection<? extends GrantedAuthority> authorities,
                  it.ambulatoriovetathena.security.domains.User user) {

        super(username, password,
                enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked,
                authorities);

        this.user = user;

    }

    public it.ambulatoriovetathena.security.domains.User getUser() {
        return user;
    }

}
