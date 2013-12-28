package cz.muni.fi.pa165.mamatoad.soccerrecords.dto;

import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Role;

/**
 * User Data Transfer Object.
 * @author Maros Klimovsky
 */
public class UserTO
{
    private Long id;
    private String login;
    private String password;
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    
    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + login + "]";
    }
}