package cz.muni.fi.pa165.mamatoad.soccerrecords.dto;

/**
 * User Data Transfer Object.
 * @author Maros Klimovsky
 */
public class UserTO
{
    private long id;
    private String login;
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    
    
    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + login + "]";
    }
}