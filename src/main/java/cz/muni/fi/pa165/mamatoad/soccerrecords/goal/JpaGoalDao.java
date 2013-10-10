/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.mamatoad.soccerrecords.goal;

import cz.muni.fi.pa165.mamatoad.soccerrecords.match.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.player.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Adriana Smijakova
 */
public class JpaGoalDao implements GoalDao {

    private EntityManagerFactory factory;

    public JpaGoalDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public void createGoal(Goal goal) throws IllegalArgumentException, IllegalEntityException {
        if (goal == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }
        
        StringBuilder builder = new StringBuilder("These parameters are null: ");
        
        boolean throwEx = validateGoal(goal, builder);
        if(goal.getId() != null){
            builder.append("\n Id should be null.");
        }
        if(throwEx){
            throw new IllegalEntityException(builder.toString());
        }

        EntityManager em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(goal);
        em.getTransaction().commit();
    }

    @Override
    public void updateGoal(Goal goal) throws IllegalArgumentException, IllegalEntityException {
        if (goal == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }
        
        StringBuilder builder = new StringBuilder("These parameters are null: ");
        
        boolean throwEx = validateGoal(goal, builder);
        if(goal.getId() == null){
            builder.append("id ");
        }
        if(throwEx){
            throw new IllegalEntityException(builder.toString());
        }

        EntityManager em = factory.createEntityManager();
        
        if(em.find(Goal.class, goal) == null){
            throw new IllegalEntityException("Goal is not in database");
        }
        
        em.merge(goal);
    }

    @Override
    public void deleteGoal(Goal goal) throws IllegalArgumentException, IllegalEntityException {
        if (goal == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }
        if(goal.getId() == null){
            throw new IllegalEntityException("Goal id is null");
        }

        EntityManager em = factory.createEntityManager();
        
        if(em.find(Goal.class, goal) == null){
            throw new IllegalEntityException("Goal is not in database");
        }
        
        em.getTransaction().begin();
        em.remove(goal);
        em.getTransaction().commit();
    }

    @Override
    public Goal retrieveGoalById(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }

        EntityManager em = factory.createEntityManager();
        Goal goal = em.find(Goal.class, id);

        return goal;
    }

    @Override
    public List<Goal> retrieveGoalsByMatch(Match match) throws IllegalArgumentException, IllegalEntityException {
        if (match == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }
        if (match.getId() == null){
            throw new IllegalEntityException("Id of match is null");
        }

        EntityManager em = factory.createEntityManager();
        TypedQuery<Goal> goals = em.createQuery(
                "SELECT g FROM Goal g WHERE g.match = :match", Goal.class).setParameter("match", match);

        return goals.getResultList();
    }

    @Override
    public List<Goal> retrieveGoalsByMatchAndTeam(Match match, Team team) throws IllegalArgumentException, IllegalEntityException {
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

        EntityManager em = factory.createEntityManager();
        TypedQuery<Goal> goals = em.createQuery(
                "SELECT g FROM Goal g WHERE g.match = :match AND g.team = :team", Goal.class);
        goals.setParameter("match", match);
        goals.setParameter("team", team);

        return goals.getResultList();
    }

    @Override
    public List<Goal> retrieveGoalsByPlayer(Player player) throws IllegalArgumentException, IllegalEntityException {
        if (player == null) {
            throw new IllegalArgumentException("Parameter player is null");
        }
        if(player.getId() == null){
            throw new IllegalEntityException("Player id is null");
        }

        EntityManager em = factory.createEntityManager();
        TypedQuery<Goal> goals = em.createQuery(
                "SELECT g FROM Goal g WHERE g.player = :player", Goal.class).setParameter("player", player);

        return goals.getResultList();
    }

    @Override
    public List<Goal> retrieveGoalsByMatchAndPlayer(Match match, Player player) throws IllegalArgumentException, IllegalEntityException {
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

        EntityManager em = factory.createEntityManager();
        TypedQuery<Goal> goals = em.createQuery(
                "SELECT g FROM Goal g WHERE g.match = :match AND g.player = :player", Goal.class);
        goals.setParameter("match", match);
        goals.setParameter("player", player);

        return goals.getResultList();
    }
    
    static private boolean validateGoal(Goal goal, StringBuilder builder){
        boolean throwEx = false;
        
        if(goal.getMatch() == null) {
            builder.append("match ");
            throwEx = true;
        }
        if(goal.getPlayer() == null){
            builder.append("player ");
            throwEx = true;
        }
        if(goal.getTeam() == null){
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
