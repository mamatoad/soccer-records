package cz.muni.fi.pa165.mamatoad.soccerrecords.test.goal;

import cz.muni.fi.pa165.mamatoad.soccerrecords.goal.Goal;
import cz.muni.fi.pa165.mamatoad.soccerrecords.goal.GoalDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.goal.JpaGoalDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.match.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.player.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
    private static Goal goal;
    private static Map<String, String> properties;
    
    public GoalDaoTests() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        properties = new HashMap<>();
        
        properties.put("hibernate.connection.driver_class",
            "org.apache.derby.jdbc.ClientDriver");
	properties.put("hibernate.connection.url",
            "jdbc:derby://localhost:1527/memory:MatchTestsDB;create=true");
	properties.put("hibernate.hbm2ddl.auto", "create-drop");
        
        emf = Persistence.createEntityManagerFactory("pa165", properties);
        
        goalDao = new JpaGoalDao(emf);
        
        goal = new Goal();
        
        Match match = new Match();
        match.setId(Long.MIN_VALUE);
        goal.setMatch(match);
        
        Player player = new Player();
        player.setId(Long.MIN_VALUE);
        goal.setPlayer(player);
        
        goal.setShootingTime(LocalTime.MIDNIGHT);
        
        Team team = new Team();
        team.setId(Long.MIN_VALUE);
        goal.setTeam(team);
    }
    
    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }
    
    @Before
    public void setUp() {
        
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
        Assert.assertNotNull(goal.getId());
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
    public void updateGoal_ValidGoal_UpdatesGoal() throws IllegalArgumentException, IllegalEntityException {        
        goalDao.createGoal(goal);
        
        Goal goal2 = goal;
        
        Match match = new Match();
        goal2.setMatch(match);
        Player player = new Player();
        goal2.setPlayer(player);
        goal2.setShootingTime(LocalTime.MIDNIGHT);
        Team team = new Team();
        goal2.setTeam(team);
        goalDao.updateGoal(goal2);
        
        Assert.assertEquals(goal2.getId(), goal.getId());
        Assert.assertEquals(goal2.getMatch(), match);
        Assert.assertEquals(goal2.getPlayer(), player);
        Assert.assertEquals(goal2.getShootingTime(), LocalTime.MIDNIGHT);
        Assert.assertEquals(goal2.getTeam(), team);
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
        Assert.assertNull(result);
    }
    
    @Test
    public void retrieveGoalById_GoalExists_ReturnGoal() throws IllegalArgumentException, IllegalEntityException { 
        goalDao.createGoal(goal);
        Goal result = goalDao.retrieveGoalById(goal.getId());
        Assert.assertTrue(goal.equals(result));
    }
}
