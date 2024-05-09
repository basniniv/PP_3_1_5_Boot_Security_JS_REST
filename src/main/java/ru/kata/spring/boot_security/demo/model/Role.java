package ru.kata.spring.boot_security.demo.model;

import java.util.List;
import java.util.Objects;
import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rolename;

    @Override
    public String toString() {
        return rolename;
    }

    @Transient
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String rolename) {
        this.id = id;
        this.rolename = rolename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return rolename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        if (!Objects.equals(id, role.id)) {
            return false;
        }
        if (!Objects.equals(rolename, role.rolename)) {
            return false;
        }
        return Objects.equals(users, role.users);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (rolename != null ? rolename.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }


}
