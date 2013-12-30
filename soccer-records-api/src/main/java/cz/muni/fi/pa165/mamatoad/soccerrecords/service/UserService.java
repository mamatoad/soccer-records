package cz.muni.fi.pa165.mamatoad.soccerrecords.service;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.UserTO;
import java.util.List;

/**
 * Interface for managing users
 * @author Maros Klimovsky
 */
public interface UserService
{
    /**
     * This method adds new user
     * @param userTO 
     * @throws DataAccessException for errors on persistence layer
     */
    public long add(UserTO userTO);

    /**
     * This method removes given user
     * @param userTO
     * @throws DataAccessException for errors on persistence layer
     */
    public void delete(UserTO userTO);
    
    public void update(UserTO userTO);

    /**
     * This method returns all users
     * @return List<userDao>
     * @throws DataAccessException for errors on persistence layer
     */
    public List<UserTO> getAllUsers();

    public UserTO getByLogin(String login);
    
    public UserTO getById(Long id);
}