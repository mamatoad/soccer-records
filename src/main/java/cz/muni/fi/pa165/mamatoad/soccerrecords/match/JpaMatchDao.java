package cz.muni.fi.pa165.mamatoad.soccerrecords.match;

import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.joda.time.LocalDate;

/**
 * Implementation of interface MatchDao
 * 
 * @author Matus Nemec
 */
public class JpaMatchDao implements MatchDao {

    private EntityManagerFactory entityManagerFactory;

    public JpaMatchDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    
    private void checkMatch(Match match, boolean idShouldBeEqualNull) throws IllegalArgumentException, IllegalEntityException {
        if (match == null) throw new IllegalArgumentException("match == null");
        if ((match.getId() != null) && idShouldBeEqualNull) throw new IllegalEntityException("match.id != null");
        if ((match.getId() == null) && !idShouldBeEqualNull) throw new IllegalEntityException("match.id == null");
        if (match.getHomeTeam() == null) throw new IllegalEntityException("match.homeTeam == null");
        if (match.getVisitingTeam() == null) throw new IllegalEntityException("match.visitingTeam == null");
        if (match.getEventDate() == null) throw new IllegalEntityException("match.eventDate == null");
        if (match.getGoals() == null) throw new IllegalEntityException("match.goals == null");
    }
    
    private void checkIfMatchExists(Match match) throws IllegalEntityException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        if (entityManager.find(Match.class, match.getId()) == null) {
            throw new IllegalEntityException("this match does not exist");
        }
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
        entityManager.remove(match);
        entityManager.getTransaction().commit();
    }

    @Override
    public Match retrieveMatchById(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("id == null");
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Match match = entityManager.find(Match.class, id);

        return match;
    }

    @Override
    public List<Match> retrieveMatchesByTeam(Team team) throws IllegalArgumentException, IllegalEntityException {
        if (team == null) {
            throw new IllegalArgumentException("team == null");
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Match> matches = entityManager.createQuery(
                "SELECT m FROM Match m WHERE m.homeTeam = :team OR m.visitingTeam = :team", Match.class);
        matches.setParameter("team", team);

        return matches.getResultList();
    }

    @Override
    public List<Match> retrieveMatchesByEventDate(LocalDate eventDate) throws IllegalArgumentException {
        if (eventDate == null) {
            throw new IllegalArgumentException("eventDate == null");
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Match> matches = entityManager.createQuery(
                "SELECT m FROM Match m WHERE m.eventdate = :eventDate", Match.class);
        matches.setParameter("eventDate", eventDate);

        return matches.getResultList();
    }
}
