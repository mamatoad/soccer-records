package cz.muni.fi.pa165.mamatoad.soccerrecords.dao.impl;

import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.MatchDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.List;
import javax.persistence.EntityManager;
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
    private EntityManager em;
    
    @Override
    public void createMatch(Match match) {
        checkMatch(match, true);
        
        em.persist(match);
    }

    @Override
    public void updateMatch(Match match) {
        checkMatch(match, false);
        
        checkIfMatchExists(match);
        
        em.merge(match);
    }

    @Override
    public void deleteMatch(Match match) {
        checkMatch(match, false);
        
        checkIfMatchExists(match);
        
        Match target = em.merge(match);
        em.remove(target);
    }

    @Override
    public Match retrieveMatchById(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id == null");

        return em.find(Match.class, id);
    }

    @Override
    public List<Match> retrieveMatchesByTeam(Team team) {
        if (team == null) 
            throw new IllegalArgumentException("team == null");
        if (team.getId() == null) 
            throw new IllegalArgumentException("team.id == null (team is not in the db)");

        TypedQuery<Match> matches = em.createQuery(
                "SELECT m FROM Match m WHERE m.homeTeam = :team OR m.visitingTeam = :team", Match.class);
        matches.setParameter("team", team);

        return matches.getResultList();
    }

    @Override
    public List<Match> retrieveMatchesByEventDate(LocalDate eventDate) {
        if (eventDate == null)
            throw new IllegalArgumentException("eventDate == null");

        TypedQuery<Match> matches = em.createQuery(
                "SELECT m FROM Match m WHERE m.eventDate = :eventDate", Match.class);
        matches.setParameter("eventDate", eventDate);

        return matches.getResultList();
    }
    
    
    @Override
    public List<Match> retrieveAllMatches() {
        TypedQuery<Match> matches = em.createQuery(
                "SELECT m FROM Match", Match.class);
        return matches.getResultList();
    }
    
    private void checkMatch(Match match, boolean idShouldBeEqualNull) {
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
    
    private void checkIfMatchExists(Match match) {
        if (retrieveMatchById(match.getId()) == null) {
            throw new IllegalEntityException("Specified match does not exist " + match);
        }
    }

}
