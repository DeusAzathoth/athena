package it.ambulatoriovetathena.security.domains;

import javax.persistence.*;

@Entity
public class Role {

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long role_id;
    // Role
    private String role;
    // User
    @ManyToOne
    User user;

    public Role() {
    }

    public Role(String role, User user) {
        this.role = role;
        this.user = user;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return role_id.equals(role.role_id);
    }

    @Override
    public int hashCode() {
        return role_id.hashCode();
    }

    @Override
    public String toString() {
        return "Role{" +
                "role_id=" + role_id +
                ", role='" + role + '\'' +
                ", user=" + user +
                '}';
    }

}
