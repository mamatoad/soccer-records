package cz.muni.fi.pa165.mamatoad.soccerrecords.test.match;

import cz.muni.fi.pa165.mamatoad.soccerrecords.goal.Goal;
import cz.muni.fi.pa165.mamatoad.soccerrecords.match.JpaMatchDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.match.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.match.MatchDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.player.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;


/**
 *
 * @author Maros Klimovsky
 */

public class MatchDaoTests {
    private MatchDao matchDao;
    private static Map<String,String> properties = new HashMap<>();
    private static EntityManagerFactory entityManagerFactory;
    private Match match;
    private Player player;
    private Goal goal;
    private Team team;
    private Team otherTeam;
    private Team thirdTeam;
    @BeforeClass 
    public static void setupDbProperties(){
        
          properties.put("hibernate.connection.driver_class", "org.apache.derby.jdbc.ClientDriver");
          properties.put("hibernate.connection.url", "jdbc:derby://localhost:1527/memory:MatchTestsDB;create=true");
          properties.put("hibernate.hbm2ddl.auto", "create-drop");
          
    }
    @Before
    public void initialize() throws SQLException {
        
       entityManagerFactory = Persistence.createEntityManagerFactory("pa165",properties);
       
        matchDao = new JpaMatchDao(entityManagerFactory);
        EntityManager manager = entityManagerFactory.createEntityManager();
        // create Player
        manager.getTransaction().begin();
        player = new Player();
        player.setName("John Kopachka");
        player.setActive(true);
        manager.persist(player);
        
        // create Teams
        team = new Team();
        team.setName("FC Ballerinas");
        team.setPlayers(new ArrayList<Player>());
        manager.persist(team);
        
        otherTeam = new Team();
        otherTeam.setName("FC Skulkers");
        otherTeam.setPlayers(new ArrayList<Player>());
        manager.persist(otherTeam);
        
        thirdTeam = new Team();
        thirdTeam.setName("FC Noobs");
        thirdTeam.setPlayers(new ArrayList<Player>());
        manager.persist(thirdTeam);
        // create Goal
        goal = new Goal();
        manager.persist(goal);
        
        manager.getTransaction().commit();
        // create Match
             
        match = new Match();
        match.setHomeTeam(team);
        match.setVisitingTeam(otherTeam);
        match.setEventDate(LocalDate.now());
              
    }
    
    @After
    public void tearDown() {
      entityManagerFactory.close();
    }
    
    @AfterClass
    public static void tearDownClass() {
        
    }
    
    @Test
    public void create_validMatch_createsMatch() throws IllegalArgumentException, IllegalEntityException {
        matchDao.createMatch(match);
        
        Long id = match.getId();
        Assert.assertNotNull("Id was not assigned.", id);
                      
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void create_nullMatch_ThrowsIllegalArgumentException() throws IllegalArgumentException, IllegalEntityException {
        match = null;
        matchDao.createMatch(match);
        
    }
    
    @Test(expected = IllegalEntityException.class)
    public void create_matchWithId_Throws() throws IllegalArgumentException, IllegalEntityException{
        match.setId(Long.MIN_VALUE);
        matchDao.createMatch(match);
    }
    
    @Test(expected = IllegalEntityException.class)
     public void create_nullMatchHomeTeam_ThrowsIllegalEntityException() throws IllegalArgumentException, IllegalEntityException{
        match.setHomeTeam(null);
        matchDao.createMatch(match);
    }
    
    @Test(expected = IllegalEntityException.class)
     public void create_nullMatchVisitingTeam_ThrowsIllegalEntityException() throws IllegalArgumentException, IllegalEntityException{
        match.setVisitingTeam(null);
        matchDao.createMatch(match);
    }
    
    @Test(expected = IllegalEntityException.class)
     public void create_nullMatchEventDate_ThrowsIllegalEntityException() throws IllegalArgumentException, IllegalEntityException{
        match.setEventDate(null);
        matchDao.createMatch(match);
    }
    
    @Test(expected = IllegalEntityException.class)
     public void create_matchHomeTeamNullId_ThrowsIllegalEntityException() throws IllegalArgumentException, IllegalEntityException{
        Team testTeam = new Team();
        match.setHomeTeam(testTeam);
        matchDao.createMatch(match);
    }
    
    @Test(expected = IllegalEntityException.class)
     public void create_matchVisitingTeamNullId_ThrowsIllegalEntityException() throws IllegalArgumentException, IllegalEntityException{
        Team testTeam = new Team();
        match.setVisitingTeam(testTeam);
        matchDao.createMatch(match);
    }
    
    @Test(expected = IllegalEntityException.class)
     public void create_matchTeamPlayingAgainstItself_ThrowsIllegalEntityException() throws IllegalArgumentException, IllegalEntityException{
       match.setVisitingTeam(match.getHomeTeam());
        matchDao.createMatch(match);
    }
    
    @Test(expected = IllegalArgumentException.class)
     public void update_nullMatch_ThrowsIllegalArgumentException() throws IllegalArgumentException, IllegalEntityException{
        match = null;
        matchDao.updateMatch(match);
    }
    
    @Test(expected = IllegalEntityException.class)
     public void update_matchNullId_ThrowsIllegalEntityException() throws IllegalArgumentException, IllegalEntityException{
        matchDao.updateMatch(match);
    }
    
    @Test(expected = IllegalEntityException.class)
     public void update_nullMatchHomeTeam_ThrowsIllegalEntityException() throws IllegalArgumentException, IllegalEntityException{
        match.setHomeTeam(null);
        matchDao.updateMatch(match);
    }
    
    @Test(expected = IllegalEntityException.class)
     public void update_nullMatchVisitingTeam_ThrowsIllegalEntityException() throws IllegalArgumentException, IllegalEntityException{
        match.setVisitingTeam(null);
        matchDao.updateMatch(match);
    }
    
    @Test(expected = IllegalEntityException.class)
     public void update_nullMatchEventDate_ThrowsIllegalEntityException() throws IllegalArgumentException, IllegalEntityException{
        match.setEventDate(null);
        matchDao.updateMatch(match);
    }
    
    @Test(expected = IllegalEntityException.class)
     public void update_matchHomeTeamNullId_ThrowsIllegalEntityException() throws IllegalArgumentException, IllegalEntityException{
        Team testTeam = new Team();
        match.setHomeTeam(testTeam);
        matchDao.updateMatch(match);
    }
    
    @Test(expected = IllegalEntityException.class)
     public void update_matchVisitingTeamNullId_ThrowsIllegalEntityException() throws IllegalArgumentException, IllegalEntityException{
        Team testTeam = new Team();
        match.setVisitingTeam(testTeam);
        matchDao.updateMatch(match);
    }
    
    @Test(expected = IllegalEntityException.class)
     public void update_matchTeamPlayingAgainstItself_ThrowsIllegalEntityException() throws IllegalArgumentException, IllegalEntityException{
       match.setVisitingTeam(match.getHomeTeam());
        matchDao.updateMatch(match);
    }
    
    @Test
    public void update_validHomeTeam_UpdatesHomeTeam() throws IllegalArgumentException, IllegalEntityException{
       
       matchDao.createMatch(match);
              
       match.setHomeTeam(thirdTeam);
       matchDao.updateMatch(match);
       
       Assert.assertEquals("home team didn't update correctly", thirdTeam, entityManagerFactory.createEntityManager().find(Match.class, match.getId()).getHomeTeam());
    }
     
    @Test
    public void update_validVisitingTeam_UpdatesVisitingTeam() throws IllegalArgumentException, IllegalEntityException{
       
       matchDao.createMatch(match);
            
       match.setVisitingTeam(thirdTeam);
       matchDao.updateMatch(match);
       
       Assert.assertEquals("visiting team didn't update correctly", thirdTeam, entityManagerFactory.createEntityManager().find(Match.class, match.getId()).getVisitingTeam());
    }
    
    @Test
    public void update_validGoals_UpdatesGoals() throws IllegalArgumentException, IllegalEntityException{
       
       matchDao.createMatch(match);
       Long id = match.getId();
       EntityManager m = entityManagerFactory.createEntityManager();
       Goal g = m.find(Goal.class, goal.getId());
       m.getTransaction().begin();
       g.setMatch(match);
       m.getTransaction().commit();
             
       Assert.assertTrue("goals didn't update correctly", entityManagerFactory.createEntityManager().find(Match.class, id).getGoals().contains(goal));
    } 
    
    @Test
    public void update_validEventDate_UpdatesEventDate() throws IllegalArgumentException, IllegalEntityException{
       
       matchDao.createMatch(match);
       LocalDate date = LocalDate.parse("2005-11-12");
       match.setEventDate(date);
       matchDao.updateMatch(match);
       
       Assert.assertEquals("eventDate didn't update correctly", date, entityManagerFactory.createEntityManager().find(Match.class, match.getId()).getEventDate());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void delete_nullMatch_throwsIllegalArgumentException() throws IllegalArgumentException, IllegalEntityException{
        match = null;
        matchDao.deleteMatch(match);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void delete_matchNullId_throwsIllegalEntityException() throws IllegalArgumentException, IllegalEntityException{
        match.setId(null);
        matchDao.deleteMatch(match);
    }
    
    @Test
    public void delete_validMatch_deletesMatch() throws IllegalArgumentException, IllegalEntityException{
        matchDao.createMatch(match);
        Long id = match.getId();
        matchDao.deleteMatch(match);
        
        Assert.assertNull("match was't deleted", entityManagerFactory.createEntityManager().find(Match.class, id));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void retrieve_nullId_ThrowsIllegalArgumentException() throws IllegalArgumentException, IllegalEntityException {
        Long id = null;
        matchDao.retrieveMatchById(id);
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void retrieve_nullTeam_ThrowsIllegalArgumentException() throws IllegalArgumentException, IllegalEntityException {
        matchDao.retrieveMatchesByTeam(null);
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void retrieve_nullEventDate_ThrowsIllegalArgumentException() throws IllegalArgumentException, IllegalEntityException {
        LocalDate date = null;
        matchDao.retrieveMatchesByEventDate(date);
        
    }
    
    @Test
    public void retrieve_validId_retrievesMatch() throws IllegalArgumentException, IllegalEntityException {
        matchDao.createMatch(match);
        Long id = match.getId();
        Assert.assertEquals("match wasn't retrieved",match, matchDao.retrieveMatchById(id)); 
        
    }
    
    @Test
    public void retrieve_validTeam_retrievesMatch() throws IllegalArgumentException, IllegalEntityException {
        matchDao.createMatch(match);
        Team homeTeam = match.getHomeTeam();
        Team visitingTeam = match.getVisitingTeam();
        Long id = match.getId();
                
        Assert.assertTrue("match wasn't retrieved",matchDao.retrieveMatchesByTeam(homeTeam).contains(match)); 
        Assert.assertTrue("match wasn't retrieved",matchDao.retrieveMatchesByTeam(visitingTeam).contains(match)); 
        
    }
    
    @Test
    public void retrieve_validEventDate_retrievesMatch() throws IllegalArgumentException, IllegalEntityException {
        matchDao.createMatch(match);
        LocalDate date = match.getEventDate();
        matchDao.retrieveMatchesByEventDate(date);
        Assert.assertTrue("match wasn't retrieved",matchDao.retrieveMatchesByEventDate(date).contains(match)); 
    }
}
