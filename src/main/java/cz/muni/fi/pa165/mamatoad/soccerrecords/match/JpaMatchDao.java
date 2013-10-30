package cz.muni.fi.pa165.mamatoad.soccerrecords.match;

import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

/**
 * Implementation of interface MatchDao
 * 
 * @author Matus Nemec
 */
@Repository("matchDao")
public class JpaMatchDao implements MatchDao {
   
    @PersistenceContext
    private EntityManagerFactory entityManagerFactory;

    public JpaMatchDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    
    @Override
    public void createMatch(Match match) throws IllegalArgumentException, IllegalEntityException {
        checkMatch(match, true);
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(match);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateMatch(Match match) throws IllegalArgumentException, IllegalEntityException {
        checkMatch(match, false);
        
        checkIfMatchExists(match);
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(match);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteMatch(Match match) throws IllegalArgumentException, IllegalEntityException {
        checkMatch(match, false);
        
        checkIfMatchExists(match);
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Match target = entityManager.merge(match);
        entityManager.remove(target);
        entityManager.getTransaction().commit();
    }

    @Override
    public Match retrieveMatchById(Long id) throws IllegalArgumentException {
        if (id == null)
            throw new IllegalArgumentException("id == null");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(Match.class, id);
    }

    @Override
    public List<Match> retrieveMatchesByTeam(Team team) throws IllegalArgumentException, IllegalEntityException {
        if (team == null) 
            throw new IllegalArgumentException("team == null");
        if (team.getId() == null) 
            throw new IllegalArgumentException("team.id == null (team is not in the db)");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Match> matches = entityManager.createQuery(
                "SELECT m FROM Match m WHERE m.homeTeam = :team OR m.visitingTeam = :team", Match.class);
        matches.setParameter("team", team);

        return matches.getResultList();
    }

    @Override
    public List<Match> retrieveMatchesByEventDate(LocalDate eventDate) throws IllegalArgumentException {
        if (eventDate == null)
            throw new IllegalArgumentException("eventDate == null");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Match> matches = entityManager.createQuery(
                "SELECT m FROM Match m WHERE m.eventDate = :eventDate", Match.class);
        matches.setParameter("eventDate", eventDate);

        return matches.getResultList();
    }
    
    
    @Override
    public List<Match> retrieveAllMatches() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Match> matches = entityManager.createQuery(
                "SELECT m FROM Match", Match.class);
        return matches.getResultList();
    }
    
    private void checkMatch(Match match, boolean idShouldBeEqualNull) throws IllegalArgumentException, IllegalEntityException {
        if (match == null) throw new IllegalArgumentException("match == null");
        if ((match.getId() != null) && idShouldBeEqualNull) throw new IllegalEntityException("match.id != null");
        if ((match.getId() == null) && !idShouldBeEqualNull) throw new IllegalEntityException("match.id == null");
        if (match.getHomeTeam() == null) throw new IllegalEntityException("match.homeTeam == null");
        if (match.getHomeTeam().getId() == null) 
            throw new IllegalEntityException("match.homeTeam.id == null (homeTeam is not in the db)");
        if (match.getVisitingTeam() == null) throw new IllegalEntityException("match.visitingTeam == null");
        if (match.getVisitingTeam().getId() == null) 
            throw new IllegalEntityException("match.visitingTeam.id == null (visitingTeam is not in the db)");
        if (match.getHomeTeam().equals(match.getVisitingTeam())) 
            throw new IllegalEntityException("match.visitingTeam == match.homeTeam");
        if (match.getEventDate() == null) throw new IllegalEntityException("match.eventDate == null");
        if (match.getGoals() == null) throw new IllegalEntityException("match.goals == null");
    }
    
    private void checkIfMatchExists(Match match) throws IllegalEntityException {
        if (retrieveMatchById(match.getId()) == null) {
            throw new IllegalEntityException("Specified match does not exist " + match);
        }
    }

}
