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
     * Finds User by login
     * 
     * @param login login name
     * @return User with assoctiated login
     */
    public void create(User user);
    
    public void update(User user);
    
    public void delete(User user);
    
    public User getById(Long id);
    
    public User findByLogin(String login);
     
    public List<User> findAll();
    
}
