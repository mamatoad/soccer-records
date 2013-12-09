package cz.muni.fi.pa165.mamatoad.soccerrecords.dao.impl;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.UserDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.User;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Maros Klimovsky
 */
@Repository("userDao")
public class JpaUserDao implements UserDao {

    
    private EntityManager entityManager;

    @Override
    public void create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }
        if(findByLogin(user.getLogin()) != null)
        {
            throw new IllegalEntityException("User login already exist.");
        }
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }

        if (entityManager.find(User.class, user.getId()) == null) {
            throw new IllegalEntityException("User does not exist.");
        }

        entityManager.merge(user);
    }

    @Override
    public void delete(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }

        if (entityManager.find(User.class, user.getId()) == null) {
            throw new IllegalEntityException("User does not exist.");
        }

        entityManager.remove(entityManager.merge(user));
    }

    @Override
    public User getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null.");
        }

        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new IllegalEntityException("user not in DB");
        }

        return user;
    }

    @Override
    public User findByLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException("login is null");
        }

        User user = null;

        user = entityManager.createQuery("select u from User u where u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        return user;
        
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }
}