package cz.muni.fi.pa165.mamatoad.soccerrecords.team;

import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Class implementing Team DAO using JPA
 *
 * @author Tomas Livora
 */
@Repository("teamDao")
public class JpaTeamDao implements TeamDao {

    private final EntityManagerFactory emf;

    @Autowired
    public JpaTeamDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void createTeam(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("team is null");
        }

        if (team.getId() != null) {
            throw new IllegalEntityException("team.id is not null");
        }

        if (team.getName() == null || team.getName().isEmpty()) {
            throw new IllegalEntityException("team.name is null or empty");
        }

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(team);
        em.getTransaction().commit();
    }

    @Override
    public void updateTeam(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("team is null");
        }

        if (team.getId() == null) {
            throw new IllegalEntityException("team.id is null");
        }

        if (team.getName() == null || team.getName().isEmpty()) {
            throw new IllegalEntityException("team.name is null or empty");
        }

        if (retrieveTeamById(team.getId()) == null) {
            throw new IllegalEntityException("team does not exist");
        }

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(team);
        em.getTransaction().commit();
    }

    @Override
    public void deleteTeam(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("team is null");
        }

        if (team.getId() == null) {
            throw new IllegalEntityException("team.id is null");
        }

        if (team.getName() == null || team.getName().isEmpty()) {
            throw new IllegalEntityException("team.name is null or empty");
        }

        if (retrieveTeamById(team.getId()) == null) {
            throw new IllegalEntityException("team does not exist");
        }

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(team));
        em.getTransaction().commit();
    }

    @Override
    public Team retrieveTeamById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }

        EntityManager em = emf.createEntityManager();
        return em.find(Team.class, id);
    }

    @Override
    public List<Team> retrieveTeamsByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name is null or empty");
        }

        EntityManager em = emf.createEntityManager();
        TypedQuery<Team> query = em.createQuery("SELECT t FROM Team t WHERE name = :name", Team.class)
                .setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Team> retrieveAllTeams() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Team> query = em.createQuery("SELECT t FROM Team t", Team.class);
        return query.getResultList();
    }

}
