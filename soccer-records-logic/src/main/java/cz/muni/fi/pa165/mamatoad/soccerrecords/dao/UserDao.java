package cz.muni.fi.pa165.mamatoad.soccerrecords.dao;

import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.User;
import java.util.List;

/**
 * Interface of DAO object of entity User
 * 
 * @author Maros Klimovsky
 */
public interface UserDao
{
    /**
     * Creates new user
     * @param user 
     */
    public void create(User user);
    
    /**
     * Updates user
     * @param user 
     */
    public void update(User user);
    
    /**
     * Deletes user
     * @param user 
     */
    public void delete(User user);
    
    /**
     * Retrieves user by id
     * @param id
     * @return user with associated id
     */
    public User getById(Long id);
    
    /**
     * Finds User by login
     * 
     * @param login
     * @return user with associated login
     */
    public User findByLogin(String login);
     
    /**
     *  Retrieves all users
     * @return list of all users 
     */
    public List<User> findAll();
    
}