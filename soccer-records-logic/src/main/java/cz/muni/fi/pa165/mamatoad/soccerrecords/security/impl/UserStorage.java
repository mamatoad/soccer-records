package cz.muni.fi.pa165.mamatoad.soccerrecords.security.impl;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.UserTO;
import org.springframework.stereotype.Component;

/**
 *
 * @author Maros Klimovsky
 */
@Component
public class UserStorage
{
    private final ThreadLocal<UserTO> storage = new ThreadLocal<>();

    public UserTO getUser() {
        return storage.get();
    }
    
    public void setUser(UserTO user)
    {
        this.storage.set(user);
    }
}