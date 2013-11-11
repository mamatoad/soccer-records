package cz.muni.fi.pa165.mamatoad.soccerrecords.dao.impl;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.GoalDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Goal;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Adriana Smijakova
 */
@Repository("goalDao")
public class JpaGoalDao implements GoalDao {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void createGoal(Goal goal) {
        if (goal == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }
        
        StringBuilder builder = new StringBuilder("These parameters are wrong: ");
        
        boolean throwEx = validateGoal(goal, builder);
        if(goal.getId() != null){
            builder.append("\n Id should be null.");
            throwEx = true;
        }
        if(throwEx){
            throw new IllegalEntityException(builder.toString());
        }

        em.persist(goal);
    }

    @Override
    public void updateGoal(Goal goal) {
        if (goal == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }
        
        StringBuilder builder = new StringBuilder("These parameters are wrong: ");
        
        boolean throwEx = validateGoal(goal, builder);
        if(goal.getId() == null){
            builder.append("id ");
            throwEx = true;
        }
        if(throwEx){
            throw new IllegalEntityException(builder.toString());
        }

        if(em.find(Goal.class, goal.getId()) == null){
            throw new IllegalEntityException("Goal is not in database");
        }
        
        em.merge(goal);
    }

    @Override
    public void deleteGoal(Goal goal) {
        if (goal == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }
        if(goal.getId() == null){
            throw new IllegalEntityException("Goal id is null");
        }

        if(em.find(Goal.class, goal.getId()) == null){
            throw new IllegalEntityException("Goal is not in database");
        }
        
        Goal toRemove = em.merge(goal);
        em.remove(toRemove);
    }

    @Override
    public Goal retrieveGoalById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Parameter id is null");
        }

        Goal goal = em.find(Goal.class, id);

        return goal;
    }

    @Override
    public List<Goal> retrieveGoalsByMatch(Match match) {
        if (match == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }
        if (match.getId() == null){
            throw new IllegalEntityException("Id of match is null");
        }

        TypedQuery<Goal> goals = em.createQuery(
                "SELECT g FROM Goal g WHERE g.match = :match", Goal.class).setParameter("match", match);

        return goals.getResultList();
    }

    @Override
    public List<Goal> retrieveGoalsByMatchAndTeam(Match match, Team team) {
        if (match == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }
        if (team == null) {
            throw new IllegalArgumentException("Parameter team is null");
        }
        if(match.getId() == null){
            throw new IllegalEntityException("Match id is null");
        }
        if(team.getId() == null){
            throw new IllegalEntityException("Team id is null");
        }

        TypedQuery<Goal> goals = em.createQuery(
                "SELECT g FROM Goal g WHERE g.match = :match AND g.team = :team", Goal.class);
        goals.setParameter("match", match);
        goals.setParameter("team", team);

        return goals.getResultList();
    }

    @Override
    public List<Goal> retrieveGoalsByPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Parameter player is null");
        }
        if(player.getId() == null){
            throw new IllegalEntityException("Player id is null");
        }

        TypedQuery<Goal> goals = em.createQuery(
                "SELECT g FROM Goal g WHERE g.player = :player", Goal.class).setParameter("player", player);

        return goals.getResultList();
    }

    @Override
    public List<Goal> retrieveGoalsByMatchAndPlayer(Match match, Player player) {
       if (match == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }
        if (player == null) {
            throw new IllegalArgumentException("Parameter player is null");
        }
        if(match.getId() == null){
            throw new IllegalEntityException("Match id is null");
        }
        if(player.getId() == null){
            throw new IllegalEntityException("Player id is null");
        }

        TypedQuery<Goal> goals = em.createQuery(
                "SELECT g FROM Goal g WHERE g.match = :match AND g.player = :player", Goal.class);
        goals.setParameter("match", match);
        goals.setParameter("player", player);

        return goals.getResultList();
    }
    
    static private boolean validateGoal(Goal goal, StringBuilder builder) {
        boolean throwEx = false;
        
        if(goal.getMatch() == null || goal.getMatch().getId() == null) {
            builder.append("match ");
            throwEx = true;
        }
        if(goal.getPlayer() == null || goal.getPlayer().getId() == null){
            builder.append("player ");
            throwEx = true;
        }
        if(goal.getTeam() == null || goal.getTeam().getId() == null){
            builder.append("team ");
            throwEx = true;
        }
        if(goal.getShootingTime() == null){
            builder.append("shootingTime ");
            throwEx = true;
        }
        
        return throwEx;
    }
}
