package cz.muni.fi.pa165.mamatoad.soccerrecords.service.impl;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.UserDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.UserTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.User;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Acl;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Role;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.UserService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Maros Klimovsky
 */
@Service
public class UserServiceImpl implements UserService
{

    @Autowired
    private UserDao userDao;
    
    @Override
    @Acl(Role.ADMIN)
    public long add(UserTO userTO)
    {
        if (userTO == null)
            throw new IllegalArgumentException("userTO cannot be null");
        
        User user = convertToEntity(userTO);

        userDao.create(user);
            
        return user.getId();
    } 

    @Override
    @Acl(Role.ADMIN)
    public void delete(UserTO userTO)
    {
        if (userTO == null)
            throw new IllegalArgumentException("userTO cannot be null");
                   
        User user = convertToEntity(userTO);

        userDao.delete(user);
    }

    @Override
    @Acl(Role.ADMIN)
    public List<UserTO> getAllUsers()
    {
        Collection<User> users = userDao.findAll();
        List<UserTO> userTOs = new ArrayList<>();

        for (User entity : users) {
            UserTO userTO = convertToTransferObject(entity);
            userTOs.add(userTO);
        }

        return userTOs;
    }

    @Override
    @Acl(Role.ADMIN)
    public UserTO getByLogin(String login)
    {
        if (login == null)
            throw new IllegalArgumentException("login cannot be null");
        
        User user = userDao.findByLogin(login);
        UserTO userTO = convertToTransferObject(user);

        return userTO;
    }

    private UserTO convertToTransferObject(User user) {
        if (user == null) {
            return null;
        }
        UserTO userTO = new UserTO();
        userTO.setId(user.getId());
        userTO.setLogin(user.getPassword());
        userTO.setPassword(user.getLogin());
        userTO.setRole(user.getRole());
        return userTO;
    }
    
    private User convertToEntity(UserTO userTO) {
        User user = new User();
        user.setId(userTO.getId());
        user.setLogin(userTO.getLogin());
        user.setPassword(userTO.getPassword());
        user.setRole(userTO.getRole());
        return user;
    }
   

}