package it.ambulatoriovetathena.security.services;

import it.ambulatoriovetathena.security.domains.Role;
import it.ambulatoriovetathena.security.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        System.out.println("UserDetails - loadUserByUsername");
        User user = userService.findUserByUsername(s);
        List<GrantedAuthority> authorityList = getAuthorityList(user.getRoles());
        return buildUserForAuthentication(user, authorityList);
        
    }

    private List<GrantedAuthority> getAuthorityList(Set<Role> roles) {

        List<GrantedAuthority> grantedAuthorityList;
        if (!roles.isEmpty()) {
            System.out.println("Roles not empty");
            Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
            for (Role role : roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
            }
            grantedAuthorityList = new ArrayList<>(grantedAuthorities);
        } else {
            System.out.println("Roles empty");
            grantedAuthorityList = new ArrayList<>();
            grantedAuthorityList.add(new SimpleGrantedAuthority("user"));
        }
        return grantedAuthorityList;

    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorityList) {

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), true, true, true, true,
                authorityList);

    }
}
