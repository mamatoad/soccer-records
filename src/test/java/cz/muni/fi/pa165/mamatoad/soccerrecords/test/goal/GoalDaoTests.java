package cz.muni.fi.pa165.mamatoad.soccerrecords.test.goal;

import cz.muni.fi.pa165.mamatoad.soccerrecords.goal.Goal;
import cz.muni.fi.pa165.mamatoad.soccerrecords.goal.GoalDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.goal.JpaGoalDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.match.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.player.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Matus Nemec
 */
public class GoalDaoTests {
    private static EntityManagerFactory emf;
    private static GoalDao goalDao;
    private static Map<String, String> properties;
    
    private Match match;
    private Match otherMatch;
    private Player player;
    private Player otherPlayer;
    private Team team;
    private Team otherTeam;
    private Goal goal;
    
    public GoalDaoTests() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        properties = new HashMap<>();
        
        properties.put("hibernate.connection.driver_class",
            "org.apache.derby.jdbc.ClientDriver");
	properties.put("hibernate.connection.url",
            "jdbc:derby://localhost:1527/memory:GoalTestsDB;create=true");
	properties.put("hibernate.hbm2ddl.auto", "create-drop");
    }
    
    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }
    
    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("pa165", properties);
        
        goalDao = new JpaGoalDao(emf);
        
        EntityManager manager = emf.createEntityManager();
        
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
    
    @After
    public void tearDown() {
    }
    
    //createGoal tests
    @Test(expected = IllegalArgumentException.class)
    public void createGoal_GoalIsNull_ThrowIllegalArgumentException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createGoal_IdIsNotNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goal.setId(Long.MIN_VALUE);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createGoal_MatchIsNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goal.setMatch(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createGoal_PlayerIsNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goal.setPlayer(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createGoal_ShootingTimeIsNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goal.setShootingTime(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createGoal_TeamIsNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goal.setTeam(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createGoal_MatchIdIsNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goal.getMatch().setId(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createGoal_PlayerIdIsNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goal.getPlayer().setId(null);
        goalDao.createGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createGoal_TeamIdIsNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goal.getTeam().setId(null);
        goalDao.createGoal(goal);
    }
    
    @Test
    public void createGoal_ValidGoal_CreatesGoal() throws IllegalArgumentException, IllegalEntityException {        
        goalDao.createGoal(goal);
        Assert.assertNotNull("Id was not assigned.", goal.getId());
    }
    
    //updateGoal tests
    @Test(expected = IllegalArgumentException.class)
    public void updateGoal_GoalIsNull_ThrowIllegalArgumentException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.updateGoal(null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_GoalDoesNotExist_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goal.setId(Long.MIN_VALUE);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_IdIsNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        goal.setId(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_MatchIsNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        goal.setMatch(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_PlayerIsNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        goal.setPlayer(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_ShootingTimeIsNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        goal.setShootingTime(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_TeamIsNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        goal.setTeam(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_MatchIdIsNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        goal.getMatch().setId(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_PlayerIdIsNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        goal.getPlayer().setId(null);
        goalDao.updateGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateGoal_TeamIdIsNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        goal.getTeam().setId(null);
        goalDao.updateGoal(goal);
    }
    
    @Test
    public void updateGoal_ChangeTeam_UpdatesGoal() throws IllegalArgumentException, IllegalEntityException {        
        goalDao.createGoal(goal);
        
        goal.setTeam(otherTeam);
        goalDao.updateGoal(goal);
        
        Assert.assertEquals(
                "Team didn't update.", emf.createEntityManager().find(Goal.class, goal.getId()).getTeam(), otherTeam);
    }
    
    @Test
    public void updateGoal_ChangePlayer_UpdatesGoal() throws IllegalArgumentException, IllegalEntityException {        
        goalDao.createGoal(goal);
        
        goal.setPlayer(otherPlayer);
        goalDao.updateGoal(goal);
        
        Assert.assertEquals(
                "Player didn't update.", emf.createEntityManager().find(Goal.class, goal.getId()).getPlayer(), otherPlayer);
    }
    
    @Test
    public void updateGoal_ChangeMatch_UpdatesGoal() throws IllegalArgumentException, IllegalEntityException {        
        goalDao.createGoal(goal);
        
        goal.setMatch(otherMatch);
        goalDao.updateGoal(goal);
        
        Assert.assertEquals(
                "Match didn't update.", emf.createEntityManager().find(Goal.class, goal.getId()).getMatch(), otherMatch);
    }
    
    @Test
    public void updateGoal_ChangeShootingTime_UpdatesGoal() throws IllegalArgumentException, IllegalEntityException {        
        goalDao.createGoal(goal);
        
        LocalTime newTime = LocalTime.now();
        goal.setShootingTime(newTime);
        goalDao.updateGoal(goal);
        
        Assert.assertEquals(
                "Time didn't update.", emf.createEntityManager().find(Goal.class, goal.getId()).getShootingTime(), newTime);
    }
    
    //deleteGoal tests
    @Test(expected = IllegalArgumentException.class)
    public void deleteGoal_GoalIsNull_ThrowIllegalArgumentException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.deleteGoal(null);
    }

    @Test(expected = IllegalEntityException.class)
    public void deleteGoal_GoalDoesNotExist_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goal.setId(Long.MAX_VALUE);
        goalDao.deleteGoal(goal);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void deleteGoal_GoalIdIsNull_ThrowIllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goal.setId(null);
        goalDao.deleteGoal(goal);
    }
    
    @Test
    public void deleteGoal_GoalExists_DeletesGoal() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        goalDao.deleteGoal(goal);
    }
    
    //retrieveGoalById tests
    @Test(expected = IllegalArgumentException.class)
    public void retrieveGoalById_IdIsNull_ThrowIllegalArgumentException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.retrieveGoalById(null);
    }
    
    @Test
    public void retrieveGoalById_GoalDoesNotExist_ReturnNull() throws IllegalArgumentException, IllegalEntityException { 
        Goal result = goalDao.retrieveGoalById(Long.MAX_VALUE);
        Assert.assertNull("Null expected.", result);
    }
    
    @Test
    public void retrieveGoalById_GoalExists_ReturnGoal() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        Goal result = goalDao.retrieveGoalById(goal.getId());
        Assert.assertTrue("The correct goal wasn't retrieved.", goal.equals(result));
    }
    
    //retrieveGoalsByMatch tests
    @Test(expected = IllegalArgumentException.class)
    public void retrieveGoalsByMatch_MatchIsNull_ThrowIllegalArgumentException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.retrieveGoalsByMatch(null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void retrieveGoalsByMatch_MatchIdIsNull_IllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.retrieveGoalsByMatch(new Match());
    }
    
    @Test
    public void retrieveGoalsByMatch_MatchExists_ReturnGoals() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatch(match);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertFalse("List of goals was empty.", result.isEmpty());
    }
    
    @Test
    public void retrieveGoalsByMatch_MatchHasNoGoals_ReturnEmptyList() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatch(otherMatch);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List of goals was not empty.", result.isEmpty());
    }
    
    //retrieveGoalsByMatchAndTeam
    @Test(expected = IllegalArgumentException.class)
    public void retrieveGoalsByMatchAndTeam_MatchIsNull_ThrowIllegalArgumentException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.retrieveGoalsByMatchAndTeam(null, team);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void retrieveGoalsByMatchAndTeam_MatchIdIsNull_IllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.retrieveGoalsByMatchAndTeam(new Match(), team);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void retrieveGoalsByMatchAndTeam_TeamIsNull_ThrowIllegalArgumentException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.retrieveGoalsByMatchAndTeam(match, null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void retrieveGoalsByMatchAndTeam_TeamIdIsNull_IllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.retrieveGoalsByMatchAndTeam(match, new Team());
    }
    
    @Test
    public void retrieveGoalsByMatchAndTeam_ValidSearch_RetrieveGoals() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatchAndTeam(match, team);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List does not contain the goal.", result.contains(goal));
    }
    
    //retrieveGoalsByPlayer tests
    @Test(expected = IllegalArgumentException.class)
    public void retrieveGoalsByPlayer_PlayerIsNull_ThrowIllegalArgumentException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.retrieveGoalsByPlayer(null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void retrieveGoalsByPlayer_PlayerIdIsNull_IllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.retrieveGoalsByPlayer(new Player());
    }
    
    @Test
    public void retrieveGoalsByPlayer_PlayerExists_ReturnGoals() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByPlayer(player);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List does not contain the goal.", result.contains(goal));
    }
    
    @Test
    public void retrieveGoalsByPlayer_PlayerHasNoGoals_ReturnEmptyGoals() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByPlayer(otherPlayer);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List of goals was not empty.", result.isEmpty());
    }
    
    //retrieveGoalsByMatchAndPlayer tests
    @Test(expected = IllegalArgumentException.class)
    public void retrieveGoalsByMatchAndPlayer_PlayerIsNull_ThrowIllegalArgumentException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.retrieveGoalsByMatchAndPlayer(match, null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void retrieveGoalsByMatchAndPlayer_PlayerIdIsNull_IllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.retrieveGoalsByMatchAndPlayer(match, new Player());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void retrieveGoalsByMatchAndPlayer_MatchIsNull_ThrowIllegalArgumentException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.retrieveGoalsByMatchAndPlayer(null, player);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void retrieveGoalsByMatchAndPlayer_MatchIdIsNull_IllegalEntityException() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.retrieveGoalsByMatchAndPlayer(new Match(), player);
    }
    
    @Test
    public void retrieveGoalsByMatchAndPlayer_Valid_ReturnGoals() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatchAndPlayer(match, player);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List does not contain the goal.", result.contains(goal));
    }
    
    @Test
    public void retrieveGoalsByMatchAndPlayer_PlayerHasNoGoalsInTheMatch_ReturnEmptyGoals() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatchAndPlayer(otherMatch, player);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List of goals was not empty.", result.isEmpty());
    }
    
    @Test
    public void retrieveGoalsByMatchAndPlayer_PlayerHasNoGoals_ReturnEmptyGoals() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        List<Goal> result = goalDao.retrieveGoalsByMatchAndPlayer(otherMatch, otherPlayer);
        Assert.assertNotNull("List of goals was null.", result);
        Assert.assertTrue("List of goals was not empty.", result.isEmpty());
    }
}
