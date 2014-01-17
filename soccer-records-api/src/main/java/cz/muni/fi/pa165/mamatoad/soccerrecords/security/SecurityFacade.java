package cz.muni.fi.pa165.mamatoad.soccerrecords.security;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.UserTO;
import java.lang.reflect.Method;

/**
 *
 * @author Maros Klimovsky
 */
public interface SecurityFacade
{
    /**
     * retrieve user credentials to be saved in the upper layer
     * for example in httpsession to preserve user login session between
     * requests
     * 
     * @return
     */
    public UserTO getUser();
    
    /**
     * set user which was retrieved from httpsession in the presentation layer
     * @param user
     */
    public void setUser(UserTO user);
    
    /**
     * 
     * @param login
     * @param password
     * @throws IllegalArgumentException when user does not exist or authentication fails
     * @throws IllegalStateException when another user is already logged in
     */
    public void login(String login, String password);
    
    /**
     * @throws IllegalStateException when no one is logged in
     */
    public void logout();
    
    /**
     * 
     * @return current user or null when no one is logged in
     */
    public UserTO getCurrentLoggedInUser();
    
    /**
     * can current user invoke given method?
     * 
     * @param method
     * @return is user authorized to use service method?
     */
    public boolean authorize(Method method);
    
    /**
     * create hash from given plaintext
     * 
     * @param plaintext
     * @return hash from plaintext
     */
    public String createHash(String plaintext);
}