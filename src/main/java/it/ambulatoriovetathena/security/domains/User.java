package it.ambulatoriovetathena.security.domains;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;
    // Username
    private String username;
    // Email
    private String email;
    // Password
    private String password;
    // Name
    private String name;
    // Lastname
    private String lastname;
    // Roles
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String email, String password, String name, String lastname) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return user_id.equals(user.user_id);
    }

    @Override
    public int hashCode() {
        return user_id.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                //", roles=" + roles +
                '}';
    }

}
