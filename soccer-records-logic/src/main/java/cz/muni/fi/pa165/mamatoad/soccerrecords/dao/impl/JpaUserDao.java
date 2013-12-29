package cz.muni.fi.pa165.mamatoad.soccerrecords.dao.impl;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.UserDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.User;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Maros Klimovsky
 */
@Repository("userDao")
public class JpaUserDao implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }
        if (user.getId() != null) {
            throw new IllegalEntityException("user.id is already set ( " + user.getId() +" )");
        }
        if(findByLogin(user.getLogin()) != null)
        {
            throw new IllegalEntityException("User login already exist.");
        }
        em.persist(user);
    }

    @Override
    public void update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }

        if (em.find(User.class, user.getId()) == null) {
            throw new IllegalEntityException("User does not exist.");
        }

        em.merge(user);
    }

    @Override
    public void delete(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }

        if (em.find(User.class, user.getId()) == null) {
            throw new IllegalEntityException("User does not exist.");
        }

        //em.remove(em.merge(user));
        User target = em.merge(user);
        em.remove(target);
    }

    @Override
    public User getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null.");
        }

        User user = em.find(User.class, id);
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

       // user = em.createQuery("select u from User u where u.login = :login", User.class)
         //           .setParameter("login", login)
         //           .getResultList();
        TypedQuery<User> query = em.createQuery("Select u from User u where u.login = :login", User.class)
                .setParameter("login", login);
        List<User> users = query.getResultList();
        if (! users.isEmpty()) {
            user = users.get(0);
        }
        
        return user;
        
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }
}