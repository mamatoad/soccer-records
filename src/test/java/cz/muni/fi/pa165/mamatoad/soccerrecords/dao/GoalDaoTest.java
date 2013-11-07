package cz.muni.fi.pa165.mamatoad.soccerrecords.dao;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.GoalDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Goal;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Team;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    
    @PersistenceContext
    private EntityManager em;
    
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
        // create Teams
        team = new Team();
        team.setName("FC 1");
        team.setPlayers(new ArrayList<Player>());
        em.persist(team);
        
        otherTeam = new Team();
        otherTeam.setName("FC 2");
        otherTeam.setPlayers(new ArrayList<Player>());
        em.persist(otherTeam);

        // create Player
        player = new Player();
        player.setName("Player 1");
        player.setActive(true);
        player.setTeam(team);
        em.persist(player);
        
        otherPlayer = new Player();
        otherPlayer.setName("Player 2");
        otherPlayer.setActive(true);
        otherPlayer.setTeam(otherTeam);
        em.persist(otherPlayer);
        
        // create Match
        match = new Match();
        match.setHomeTeam(team);
        match.setVisitingTeam(otherTeam);
        match.setEventDate(LocalDate.now());
        em.persist(match);
        
        otherMatch = new Match();
        otherMatch.setHomeTeam(otherTeam);
        otherMatch.setVisitingTeam(team);
        otherMatch.setEventDate(LocalDate.now());
        em.persist(otherMatch);
        
        //prepare goal
        goal = new Goal();
        
        goal.setMatch(match);        
        goal.setPlayer(player);        
        goal.setShootingTime(LocalTime.MIDNIGHT);        
        goal.setTeam(team);
    }
  
    //createGoal tests
    @Test(expected = DataAccessException.class)
    public void test_createGoal_GoalIsNull_ThrowIllegalArgumentException() { 
        goalDao.createGoal(null);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_createGoal_IdIsNotNull_ThrowIllegalEntityException() { 
        goal.setId(Long.MIN_VALUE);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_createGoal_MatchIsNull_ThrowIllegalEntityException() { 
        goal.setMatch(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_createGoal_PlayerIsNull_ThrowIllegalEntityException() { 
        goal.setPlayer(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_createGoal_ShootingTimeIsNull_ThrowIllegalEntityException() { 
        goal.setShootingTime(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_createGoal_TeamIsNull_ThrowIllegalEntityException() { 
        goal.setTeam(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_createGoal_MatchIdIsNull_ThrowIllegalEntityException() { 
        goal.getMatch().setId(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_createGoal_PlayerIdIsNull_ThrowIllegalEntityException() { 
        goal.getPlayer().setId(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_createGoal_TeamIdIsNull_ThrowIllegalEntityException() { 
        goal.getTeam().setId(null);
        goalDao.createGoal(goal);
    }
    
    @Test
    public void test_createGoal_ValidGoal_CreatesGoal() {        
        goalDao.createGoal(goal);
        Assert.assertNotNull("Id was not assigned.", goal.getId());
    }
    
    @Test
    public void test_getGoal_goalDoesNotExist_EmptyList() {        
        List<Goal> goals = goalDao.retrieveGoalsByPlayer(player);
        Assert.assertEquals("List of goals is not empty.", 0, goals.size());
    }
    
    //updateGoal tests
    @Test(expected = DataAccessException.class)
    public void test_updateGoal_GoalIsNull_ThrowIllegalArgumentException() { 
        goalDao.updateGoal(null);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_updateGoal_GoalDoesNotExist_ThrowIllegalEntityException() { 
        goal.setId(Long.MIN_VALUE);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_updateGoal_IdIsNull_ThrowIllegalEntityException() { 
        goalDao.createGoal(goal);
        goal.setId(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_updateGoal_MatchIsNull_ThrowIllegalEntityException() { 
        goalDao.createGoal(goal);
        goal.setMatch(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_updateGoal_PlayerIsNull_ThrowIllegalEntityException() { 
        goalDao.createGoal(goal);
        goal.setPlayer(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_updateGoal_ShootingTimeIsNull_ThrowIllegalEntityException() { 
        goalDao.createGoal(goal);
        goal.setShootingTime(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_updateGoal_TeamIsNull_ThrowIllegalEntityException() { 
        goalDao.createGoal(goal);
        goal.setTeam(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_updateGoal_MatchIdIsNull_ThrowIllegalEntityException() { 
        goalDao.createGoal(goal);
        goal.getMatch().setId(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_updateGoal_PlayerIdIsNull_ThrowIllegalEntityException() { 
        goalDao.createGoal(goal);
        goal.getPlayer().setId(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_updateGoal_TeamIdIsNull_ThrowIllegalEntityException() { 
        goalDao.createGoal(goal);
        goal.getTeam().setId(null);
        goalDao.updateGoal(goal);
    }
    
    @Test
    public void test_updateGoal_ChangeTeam_UpdatesGoal() {        
        goalDao.createGoal(goal);
        
        goal.setTeam(otherTeam);
        goalDao.updateGoal(goal);
        
        Assert.assertEquals("Team didn't update.", em.find(Goal.class, goal.getId()).getTeam(), otherTeam);
    }
    
    @Test
    public void test_updateGoal_ChangePlayer_UpdatesGoal() {        
        goalDao.createGoal(goal);
        
        goal.setPlayer(otherPlayer);
        goalDao.updateGoal(goal);
        
        Assert.assertEquals("Player didn't update.", em.find(Goal.class, goal.getId()).getPlayer(), otherPlayer);
    }
    
    @Test
    public void test_updateGoal_ChangeMatch_UpdatesGoal() {        
        goalDao.createGoal(goal);
        
        goal.setMatch(otherMatch);
        goalDao.updateGoal(goal);
        
        Assert.assertEquals("Match didn't update.", em.find(Goal.class, goal.getId()).getMatch(), otherMatch);
    }
    
    @Test
    public void test_updateGoal_ChangeShootingTime_UpdatesGoal() {        
        goalDao.createGoal(goal);
        
        LocalTime newTime = LocalTime.now();
        goal.setShootingTime(newTime);
        goalDao.updateGoal(goal);
        
        Assert.assertEquals("Time didn't update.", em.find(Goal.class, goal.getId()).getShootingTime(), newTime);
    }
    
    //deleteGoal tests
    @Test(expected = DataAccessException.class)
    public void test_deleteGoal_GoalIsNull_ThrowIllegalArgumentException() { 
        goalDao.deleteGoal(null);
    }

    @Test(expected = DataAccessException.class)
    public void test_deleteGoal_GoalDoesNotExist_ThrowIllegalEntityException() { 
        goal.setId(Long.MAX_VALUE);
        goalDao.deleteGoal(goal);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_deleteGoal_GoalIdIsNull_ThrowIllegalEntityException() { 
        goal.setId(null);
        goalDao.deleteGoal(goal);
    }
    
    @Test
    public void test_deleteGoal_GoalExists_DeletesGoal() { 
        goalDao.createGoal(goal);
        goalDao.deleteGoal(goal);
    }
    
    //retrieveGoalById tests
    @Test(expected = DataAccessException.class)
    public void test_retrieveGoalById_IdIsNull_ThrowIllegalArgumentException() { 
        goalDao.retrieveGoalById(null);
    }   
    
    @Test
    public void test_retrieveGoalById_GoalDoesNotExist_ReturnNull() { 
        Goal result = goalDao.retrieveGoalById(Long.MAX_VALUE);
        Assert.assertNull("Null expected.", result);
    }
    
    @Test
    public void test_retrieveGoalById_GoalExists_ReturnGoal() { 
        goalDao.createGoal(goal);
        Goal result = goalDao.retrieveGoalById(goal.getId());
        Assert.assertTrue("The correct goal wasn't retrieved.", goal.equals(result));
    }
    
    //retrieveGoalsByMatch tests
    @Test(expected = DataAccessException.class)
    public void test_retrieveGoalsByMatch_MatchIsNull_ThrowIllegalArgumentException() { 
        goalDao.retrieveGoalsByMatch(null);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_retrieveGoalsByMatch_MatchIdIsNull_IllegalEntityException() { 
        goalDao.retrieveGoalsByMatch(new Match());
    }
    
    @Test
    public void test_retrieveGoalsByMatch_MatchExists_ReturnGoals() { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatch(match);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertFalse("List of goals was empty.", result.isEmpty());
    }
    
    @Test
    public void test_retrieveGoalsByMatch_MatchHasNoGoals_ReturnEmptyList() { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatch(otherMatch);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List of goals was not empty.", result.isEmpty());
    }
    
    //retrieveGoalsByMatchAndTeam
    @Test(expected = DataAccessException.class)
    public void test_retrieveGoalsByMatchAndTeam_MatchIsNull_ThrowIllegalArgumentException() { 
        goalDao.retrieveGoalsByMatchAndTeam(null, team);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_retrieveGoalsByMatchAndTeam_MatchIdIsNull_IllegalEntityException() { 
        goalDao.retrieveGoalsByMatchAndTeam(new Match(), team);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_retrieveGoalsByMatchAndTeam_TeamIsNull_ThrowIllegalArgumentException() { 
        goalDao.retrieveGoalsByMatchAndTeam(match, null);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_retrieveGoalsByMatchAndTeam_TeamIdIsNull_IllegalEntityException() { 
        goalDao.retrieveGoalsByMatchAndTeam(match, new Team());
    }
    
    @Test
    public void test_retrieveGoalsByMatchAndTeam_ValidSearch_RetrieveGoals() { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatchAndTeam(match, team);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List does not contain the goal.", result.contains(goal));
    }
    
    //retrieveGoalsByPlayer tests
    @Test(expected = DataAccessException.class)
    public void test_retrieveGoalsByPlayer_PlayerIsNull_ThrowIllegalArgumentException() { 
        goalDao.retrieveGoalsByPlayer(null);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_retrieveGoalsByPlayer_PlayerIdIsNull_IllegalEntityException() { 
        goalDao.retrieveGoalsByPlayer(new Player());
    }
    
    @Test
    public void test_retrieveGoalsByPlayer_PlayerExists_ReturnGoals() { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByPlayer(player);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List does not contain the goal.", result.contains(goal));
    }
    
    @Test
    public void test_retrieveGoalsByPlayer_PlayerHasNoGoals_ReturnEmptyGoals() { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByPlayer(otherPlayer);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List of goals was not empty.", result.isEmpty());
    }
    
    //retrieveGoalsByMatchAndPlayer tests
    @Test(expected = DataAccessException.class)
    public void test_retrieveGoalsByMatchAndPlayer_PlayerIsNull_ThrowIllegalArgumentException() { 
        goalDao.retrieveGoalsByMatchAndPlayer(match, null);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_retrieveGoalsByMatchAndPlayer_PlayerIdIsNull_IllegalEntityException() { 
        goalDao.retrieveGoalsByMatchAndPlayer(match, new Player());
    }
    
    @Test(expected = DataAccessException.class)
    public void test_retrieveGoalsByMatchAndPlayer_MatchIsNull_ThrowIllegalArgumentException() { 
        goalDao.retrieveGoalsByMatchAndPlayer(null, player);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_retrieveGoalsByMatchAndPlayer_MatchIdIsNull_IllegalEntityException() { 
        goalDao.retrieveGoalsByMatchAndPlayer(new Match(), player);
    }
    
    @Test
    public void test_retrieveGoalsByMatchAndPlayer_Valid_ReturnGoals() { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatchAndPlayer(match, player);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List does not contain the goal.", result.contains(goal));
    }
    
    @Test
    public void test_retrieveGoalsByMatchAndPlayer_PlayerHasNoGoalsInTheMatch_ReturnEmptyGoals() { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatchAndPlayer(otherMatch, player);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List of goals was not empty.", result.isEmpty());
    }
    
    @Test
    public void test_retrieveGoalsByMatchAndPlayer_PlayerHasNoGoals_ReturnEmptyGoals() { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatchAndPlayer(otherMatch, otherPlayer);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List of goals was not empty.", result.isEmpty());
    }
}
