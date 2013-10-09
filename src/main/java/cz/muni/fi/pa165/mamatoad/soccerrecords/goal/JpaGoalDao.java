/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.mamatoad.soccerrecords.goal;

import cz.muni.fi.pa165.mamatoad.soccerrecords.match.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.player.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Adriana
 */
public class JpaGoalDao implements GoalDao {

    private EntityManagerFactory factory;

    public JpaGoalDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public Goal createGoal(Goal goal) throws IllegalArgumentException {
        if (goal == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(goal);
        em.getTransaction().commit();

        return goal;
    }

    @Override
    public Goal updateGoal(Goal goal) throws IllegalArgumentException {
        if (goal == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(goal);
        em.getTransaction().commit();

        return goal;
    }

    @Override
    public void deleteGoal(Goal goal) throws IllegalArgumentException {
        if (goal == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }

        EntityManager em = factory.createEntityManager();
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
    public List<Goal> retrieveGoalsByMatch(Match match) throws IllegalArgumentException {
        if (match == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }

        EntityManager em = factory.createEntityManager();
        TypedQuery<Goal> goals = em.createQuery(
                "SELECT g FROM Goal g WHERE g.match = :match", Goal.class).setParameter("match", match);

        return goals.getResultList();
    }

    @Override
    public List<Goal> retrieveGoalsByMatchAndTeam(Match match, Team team) throws IllegalArgumentException {
        if (match == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }
        if (team == null) {
            throw new IllegalArgumentException("Parameter team is null");
        }

        EntityManager em = factory.createEntityManager();
        TypedQuery<Goal> goals = em.createQuery(
                "SELECT g FROM Goal g WHERE g.match = :match AND g.team = :team", Goal.class);
        goals.setParameter("match", match);
        goals.setParameter("team", team);

        return goals.getResultList();
    }

    @Override
    public List<Goal> retrieveGoalsByPlayer(Player player) throws IllegalArgumentException {
        if (player == null) {
            throw new IllegalArgumentException("Parameter player is null");
        }

        EntityManager em = factory.createEntityManager();
        TypedQuery<Goal> goals = em.createQuery(
                "SELECT g FROM Goal g WHERE g.player = :player", Goal.class).setParameter("player", player);

        return goals.getResultList();
    }

    @Override
    public List<Goal> retrieveGoalsByMatchAndPlayer(Match match, Player player) throws IllegalArgumentException {
       if (match == null) {
            throw new IllegalArgumentException("Parameter goal is null");
        }
        if (player == null) {
            throw new IllegalArgumentException("Parameter player is null");
        }

        EntityManager em = factory.createEntityManager();
        TypedQuery<Goal> goals = em.createQuery(
                "SELECT g FROM Goal g WHERE g.match = :match AND g.player = :player", Goal.class);
        goals.setParameter("match", match);
        goals.setParameter("player", player);

        return goals.getResultList();
    }
}
