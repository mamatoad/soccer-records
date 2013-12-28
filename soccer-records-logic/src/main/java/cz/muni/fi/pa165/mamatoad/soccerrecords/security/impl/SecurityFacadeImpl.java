package cz.muni.fi.pa165.mamatoad.soccerrecords.security.impl;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.UserTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Acl;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Role;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.SecurityFacade;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.UserService;
import java.lang.reflect.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 *
 * @author Maros Klimovsky
 */
@Component
public class SecurityFacadeImpl implements SecurityFacade
{    
    @Autowired
    UserStorage storage;
    
    @Autowired
    UserService userService;
    
    
    @Override
    public void setUser(UserTO user)
    {
        storage.setUser(user);
    }
    
    @Override
    public UserTO getUser()
    {
        return storage.getUser();
    }
    
    @Override
    public void login(String login, String password)
    {
        
        
        if(storage.getUser() != null)
        {
            throw new IllegalStateException("Some user is already logged in.");
        }
        
        // create defaut root admin
        if( userService.getByLogin("admin") == null
                && "admin".equals(login)
                && "password".equals(password))
        {
            //quick admin authentication
            UserTO user = new UserTO();

            user.setLogin("admin");
            user.setRole(Role.ADMIN);
            user.setPassword(DigestUtils.md5DigestAsHex("password".getBytes()));
            
            storage.setUser(user); 
            
            return;
        }
		
		// create rest user
        if( userService.getByLogin("rest") == null
                && "rest".equals(login)
                && "rest".equals(password))
        {
            //quick admin authentication
            UserTO user = new UserTO();

            user.setLogin("rest");
            user.setRole(Role.USER);
            user.setPassword(DigestUtils.md5DigestAsHex("rest".getBytes()));
            
            storage.setUser(user); 
            
            return;
        }
        
        //authenticate
        UserTO user = userService.getByLogin(login);
        String hashedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        if(user == null || !hashedPassword.equals(user.getPassword()))
        {
            //failure
            throw new
            IllegalArgumentException("Invalid user login ("+login
                    +") or password.");
        }
        // ok

        storage.setUser(user);   
    }

    
    @Override
    public void logout()
    {
        
        storage.setUser(null);    
    }

    @Override
    public UserTO getCurrentLoggedInUser()
    {
        return storage
                .getUser();
                
    }

    @Override
    public boolean authorize(Method method)
    {  
        
        // get Acl annotations
        Acl acl = method.getAnnotation(Acl.class);
        Role role = null;
        if(acl == null)
        {
            role = Role.NONE;
        }
        else
        {
            role = acl.value();
            
        }
        
        Role userRole = Role.NONE;
        if(this.getCurrentLoggedInUser() != null)
        {
            userRole = getCurrentLoggedInUser().getRole();
        }
        
         
        if(role == userRole || userRole.hasParent(role))
        {
            return true;
        }
        return false;
    }
}