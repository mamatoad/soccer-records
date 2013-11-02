package cz.muni.fi.pa165.mamatoad.soccerrecords.goal;

import cz.muni.fi.pa165.mamatoad.soccerrecords.match.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.player.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Matus Nemec
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springConfigTest.xml"})
@Transactional
public class GoalDaoTest {
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private GoalDao goalDao;
    
    private Match match;
    private Match otherMatch;
    private Player player;
    private Player otherPlayer;
    private Team team;
    private Team otherTeam;
    private Goal goal;
    
    @Before
    public void setUp() {
        EntityManager manager = entityManagerFactory.createEntityManager();
        
        manager.getTransaction().begin();
        
        // create Teams
        team = new Team();
        team.setName("FC 1");
        team.setPlayers(new ArrayList<Player>());
        manager.persist(team);
        
        otherTeam = new Team();
        otherTeam.setName("FC 2");
        otherTeam.setPlayers(new ArrayList<Player>());
        manager.persist(otherTeam);

        // create Player
        player = new Player();
        player.setName("Player 1");
        player.setActive(true);
        player.setTeam(team);
        manager.persist(player);
        
        otherPlayer = new Player();
        otherPlayer.setName("Player 2");
        otherPlayer.setActive(true);
        otherPlayer.setTeam(otherTeam);
        manager.persist(otherPlayer);
        
        // create Match
        match = new Match();
        match.setHomeTeam(team);
        match.setVisitingTeam(otherTeam);
        match.setEventDate(LocalDate.now());
        manager.persist(match);
        
        otherMatch = new Match();
        otherMatch.setHomeTeam(otherTeam);
        otherMatch.setVisitingTeam(team);
        otherMatch.setEventDate(LocalDate.now());
        manager.persist(otherMatch);
        
        manager.getTransaction().commit();
        
        //prepare goal
        
        goal = new Goal();
        
        goal.setMatch(match);        
        goal.setPlayer(player);        
        goal.setShootingTime(LocalTime.MIDNIGHT);        
        goal.setTeam(team);
    }
  
    //createGoal tests
    @Test(expected = DataAccessException.class)
    public void createGoal_GoalIsNull_ThrowIllegalArgumentException() { 
        goalDao.createGoal(null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createGoal_IdIsNotNull_ThrowIllegalEntityException() { 
        goal.setId(Long.MIN_VALUE);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createGoal_MatchIsNull_ThrowIllegalEntityException() { 
        goal.setMatch(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createGoal_PlayerIsNull_ThrowIllegalEntityException() { 
        goal.setPlayer(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createGoal_ShootingTimeIsNull_ThrowIllegalEntityException() { 
        goal.setShootingTime(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createGoal_TeamIsNull_ThrowIllegalEntityException() { 
        goal.setTeam(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createGoal_MatchIdIsNull_ThrowIllegalEntityException() { 
        goal.getMatch().setId(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createGoal_PlayerIdIsNull_ThrowIllegalEntityException() { 
        goal.getPlayer().setId(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createGoal_TeamIdIsNull_ThrowIllegalEntityException() { 
        goal.getTeam().setId(null);
        goalDao.createGoal(goal);
    }
    
    @Test
    public void createGoal_ValidGoal_CreatesGoal() {        
        goalDao.createGoal(goal);
        Assert.assertNotNull("Id was not assigned.", goal.getId());
    }
    
    @Test
    public void getGoal_goalDoesNotExist_EmptyList() {        
        List<Goal> goals = goalDao.retrieveGoalsByPlayer(player);
        Assert.assertEquals("List of goals is not empty.", 0, goals.size());
    }
    
    //updateGoal tests
    @Test(expected = DataAccessException.class)
    public void updateGoal_GoalIsNull_ThrowIllegalArgumentException() { 
        goalDao.updateGoal(null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_GoalDoesNotExist_ThrowIllegalEntityException() { 
        goal.setId(Long.MIN_VALUE);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_IdIsNull_ThrowIllegalEntityException() { 
        goalDao.createGoal(goal);
        goal.setId(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_MatchIsNull_ThrowIllegalEntityException() { 
        goalDao.createGoal(goal);
        goal.setMatch(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_PlayerIsNull_ThrowIllegalEntityException() { 
        goalDao.createGoal(goal);
        goal.setPlayer(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_ShootingTimeIsNull_ThrowIllegalEntityException() { 
        goalDao.createGoal(goal);
        goal.setShootingTime(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_TeamIsNull_ThrowIllegalEntityException() { 
        goalDao.createGoal(goal);
        goal.setTeam(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_MatchIdIsNull_ThrowIllegalEntityException() { 
        goalDao.createGoal(goal);
        goal.getMatch().setId(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_PlayerIdIsNull_ThrowIllegalEntityException() { 
        goalDao.createGoal(goal);
        goal.getPlayer().setId(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_TeamIdIsNull_ThrowIllegalEntityException() { 
        goalDao.createGoal(goal);
        goal.getTeam().setId(null);
        goalDao.updateGoal(goal);
    }
    
    @Test
    public void updateGoal_ChangeTeam_UpdatesGoal() {        
        goalDao.createGoal(goal);
        
        goal.setTeam(otherTeam);
        goalDao.updateGoal(goal);
        
        Assert.assertEquals(
               "Team didn't update.", entityManagerFactory.createEntityManager().find(Goal.class, goal.getId()).getTeam(), otherTeam);
    }
    
    @Test
    public void updateGoal_ChangePlayer_UpdatesGoal() {        
        goalDao.createGoal(goal);
        
        goal.setPlayer(otherPlayer);
        goalDao.updateGoal(goal);
        
        Assert.assertEquals(
                "Player didn't update.", entityManagerFactory.createEntityManager().find(Goal.class, goal.getId()).getPlayer(), otherPlayer);
    }
    
    @Test
    public void updateGoal_ChangeMatch_UpdatesGoal() {        
        goalDao.createGoal(goal);
        
        goal.setMatch(otherMatch);
        goalDao.updateGoal(goal);
        
        Assert.assertEquals(
                "Match didn't update.", entityManagerFactory.createEntityManager().find(Goal.class, goal.getId()).getMatch(), otherMatch);
    }
    
    @Test
    public void updateGoal_ChangeShootingTime_UpdatesGoal() {        
        goalDao.createGoal(goal);
        
        LocalTime newTime = LocalTime.now();
        goal.setShootingTime(newTime);
        goalDao.updateGoal(goal);
        
        Assert.assertEquals(
                "Time didn't update.", entityManagerFactory.createEntityManager().find(Goal.class, goal.getId()).getShootingTime(), newTime);
    }
    
    //deleteGoal tests
    @Test(expected = DataAccessException.class)
    public void deleteGoal_GoalIsNull_ThrowIllegalArgumentException() { 
        goalDao.deleteGoal(null);
    }

    @Test(expected = IllegalEntityException.class)
    public void deleteGoal_GoalDoesNotExist_ThrowIllegalEntityException() { 
        goal.setId(Long.MAX_VALUE);
        goalDao.deleteGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void deleteGoal_GoalIdIsNull_ThrowIllegalEntityException() { 
        goal.setId(null);
        goalDao.deleteGoal(goal);
    }
    
    @Test
    public void deleteGoal_GoalExists_DeletesGoal() { 
        goalDao.createGoal(goal);
        goalDao.deleteGoal(goal);
    }
    
    //retrieveGoalById tests
    @Test(expected = DataAccessException.class)
    public void retrieveGoalById_IdIsNull_ThrowIllegalArgumentException() { 
        goalDao.retrieveGoalById(null);
    }   
    
    @Test
    public void retrieveGoalById_GoalDoesNotExist_ReturnNull() { 
        Goal result = goalDao.retrieveGoalById(Long.MAX_VALUE);
        Assert.assertNull("Null expected.", result);
    }
    
    @Test
    public void retrieveGoalById_GoalExists_ReturnGoal() { 
        goalDao.createGoal(goal);
        Goal result = goalDao.retrieveGoalById(goal.getId());
        Assert.assertTrue("The correct goal wasn't retrieved.", goal.equals(result));
    }
    
    //retrieveGoalsByMatch tests
    @Test(expected = DataAccessException.class)
    public void retrieveGoalsByMatch_MatchIsNull_ThrowIllegalArgumentException() { 
        goalDao.retrieveGoalsByMatch(null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void retrieveGoalsByMatch_MatchIdIsNull_IllegalEntityException() { 
        goalDao.retrieveGoalsByMatch(new Match());
    }
    
    @Test
    public void retrieveGoalsByMatch_MatchExists_ReturnGoals() { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatch(match);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertFalse("List of goals was empty.", result.isEmpty());
    }
    
    @Test
    public void retrieveGoalsByMatch_MatchHasNoGoals_ReturnEmptyList() { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatch(otherMatch);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List of goals was not empty.", result.isEmpty());
    }
    
    //retrieveGoalsByMatchAndTeam
    @Test(expected = DataAccessException.class)
    public void retrieveGoalsByMatchAndTeam_MatchIsNull_ThrowIllegalArgumentException() { 
        goalDao.retrieveGoalsByMatchAndTeam(null, team);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void retrieveGoalsByMatchAndTeam_MatchIdIsNull_IllegalEntityException() { 
        goalDao.retrieveGoalsByMatchAndTeam(new Match(), team);
    }
    
    @Test(expected = DataAccessException.class)
    public void retrieveGoalsByMatchAndTeam_TeamIsNull_ThrowIllegalArgumentException() { 
        goalDao.retrieveGoalsByMatchAndTeam(match, null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void retrieveGoalsByMatchAndTeam_TeamIdIsNull_IllegalEntityException() { 
        goalDao.retrieveGoalsByMatchAndTeam(match, new Team());
    }
    
    @Test
    public void retrieveGoalsByMatchAndTeam_ValidSearch_RetrieveGoals() { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatchAndTeam(match, team);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List does not contain the goal.", result.contains(goal));
    }
    
    //retrieveGoalsByPlayer tests
    @Test(expected = DataAccessException.class)
    public void retrieveGoalsByPlayer_PlayerIsNull_ThrowIllegalArgumentException() { 
        goalDao.retrieveGoalsByPlayer(null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void retrieveGoalsByPlayer_PlayerIdIsNull_IllegalEntityException() { 
        goalDao.retrieveGoalsByPlayer(new Player());
    }
    
    @Test
    public void retrieveGoalsByPlayer_PlayerExists_ReturnGoals() { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByPlayer(player);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List does not contain the goal.", result.contains(goal));
    }
    
    @Test
    public void retrieveGoalsByPlayer_PlayerHasNoGoals_ReturnEmptyGoals() { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByPlayer(otherPlayer);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List of goals was not empty.", result.isEmpty());
    }
    
    //retrieveGoalsByMatchAndPlayer tests
    @Test(expected = DataAccessException.class)
    public void retrieveGoalsByMatchAndPlayer_PlayerIsNull_ThrowIllegalArgumentException() { 
        goalDao.retrieveGoalsByMatchAndPlayer(match, null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void retrieveGoalsByMatchAndPlayer_PlayerIdIsNull_IllegalEntityException() { 
        goalDao.retrieveGoalsByMatchAndPlayer(match, new Player());
    }
    
    @Test(expected = DataAccessException.class)
    public void retrieveGoalsByMatchAndPlayer_MatchIsNull_ThrowIllegalArgumentException() { 
        goalDao.retrieveGoalsByMatchAndPlayer(null, player);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void retrieveGoalsByMatchAndPlayer_MatchIdIsNull_IllegalEntityException() { 
        goalDao.retrieveGoalsByMatchAndPlayer(new Match(), player);
    }
    
    @Test
    public void retrieveGoalsByMatchAndPlayer_Valid_ReturnGoals() { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatchAndPlayer(match, player);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List does not contain the goal.", result.contains(goal));
    }
    
    @Test
    public void retrieveGoalsByMatchAndPlayer_PlayerHasNoGoalsInTheMatch_ReturnEmptyGoals() { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatchAndPlayer(otherMatch, player);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List of goals was not empty.", result.isEmpty());
    }
    
    @Test
    public void retrieveGoalsByMatchAndPlayer_PlayerHasNoGoals_ReturnEmptyGoals() { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatchAndPlayer(otherMatch, otherPlayer);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List of goals was not empty.", result.isEmpty());
    }
}
