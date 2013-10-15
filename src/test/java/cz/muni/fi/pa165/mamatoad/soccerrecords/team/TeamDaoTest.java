/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.mamatoad.soccerrecords.team;

import cz.muni.fi.pa165.mamatoad.soccerrecords.player.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for TeamDao
 *
 * @author Adriana Smijakova
 */
public class TeamDaoTest {
    
    private static Map<String,String> properties;
    private static String persistanceName = "pa165";
    private static EntityManagerFactory factory;
    private TeamDao teamDao;
    
    private Team testTeam;
    
    public TeamDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        properties = new HashMap<>();
        properties.put("hibernate.connection.driver_class","org.apache.derby.jdbc.EmbeddedDriver");
	properties.put("hibernate.connection.url","jdbc:derby:memory:TeamTestsDB;create=true");
	properties.put("hibernate.hbm2ddl.auto", "create-drop");
        
    }
    
    @AfterClass
    public static void tearDownClass() {
        
    }
    
    @Before
    public void setUp() {
        factory = Persistence.createEntityManagerFactory(persistanceName, properties);
        teamDao = new JpaTeamDao(factory);
        
        testTeam = new Team();
        testTeam.setName("teamName");
        testTeam.setPlayers(new ArrayList<Player>());
    }
    
    @After
    public void tearDown() {
        factory.close();
    }
    
    //Tests for createTeam() method
    
    @Test(expected = IllegalArgumentException.class)
    public void createTeam_nullTeam_exceptionThrow() throws IllegalEntityException {
        testTeam = null;
        
        teamDao.createTeam(testTeam);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createTeam_nullTeamName_exceptionThrow() throws IllegalEntityException {
        testTeam.setName(null);
        
        teamDao.createTeam(testTeam);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createTeam_emptyTeamName_exceptionThrow() throws IllegalEntityException {
        testTeam.setName("");
        
        teamDao.createTeam(testTeam);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createTeam_setTeamId_exceptionThrow() throws IllegalEntityException {
        testTeam.setId(Long.MIN_VALUE);
        
        teamDao.createTeam(testTeam);
    }
    
    @Test
    public void createTeam_correct_createTeam() throws IllegalEntityException {
        teamDao.createTeam(testTeam);
        Assert.assertNotNull(testTeam.getId());
    }
    
    //Tests for updateTeam() method
    
    @Test(expected = IllegalArgumentException.class)
    public void updateTeam_nullTeam_exceptionThrow() throws IllegalEntityException {
        testTeam = null;
        teamDao.updateTeam(testTeam);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateTeam_teamNotInDatabaseWithId_exceptionThrow() throws IllegalEntityException {
        testTeam.setId(Long.MIN_VALUE);
        teamDao.updateTeam(testTeam);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateTeam_nullTeamName_exceptionThrow() throws IllegalEntityException {
        teamDao.createTeam(testTeam);
        
        testTeam.setName(null);
        
        teamDao.updateTeam(testTeam);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateTeam_emptyTeamName_exceptionThrow() throws IllegalEntityException {
        teamDao.createTeam(testTeam);
        
        testTeam.setName("");
        
        teamDao.updateTeam(testTeam);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updateTeam_teamIdNull_exceptionThrow() throws IllegalEntityException {
        teamDao.createTeam(testTeam);
        
        testTeam.setId(null);
        
        teamDao.updateTeam(testTeam);
    }
    
    @Test
    public void updateTeam_correct_updateTeam() throws IllegalEntityException {
        teamDao.createTeam(testTeam);
        
        testTeam.setName("newName");
        
        teamDao.updateTeam(testTeam);
        
        EntityManager em = factory.createEntityManager();
        Team actualTeam = em.find(Team.class, testTeam.getId());
        
        Assert.assertEquals("Team was not updated", testTeam, actualTeam);
    }
    
    //Tests for deleteTeam() method
    
    @Test(expected = IllegalArgumentException.class)
    public void deleteTeam_teamNull_exceptionThrow() throws IllegalEntityException{
        testTeam = null;
        teamDao.deleteTeam(testTeam);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void deleteTeam_teamNotInDatabase_exceptionThrow() throws IllegalEntityException{
        testTeam.setId(Long.MIN_VALUE);
        teamDao.deleteTeam(testTeam);
    }
    
    @Test
    public void deleteTeam_correct_teamDeleted() throws IllegalEntityException{
        teamDao.createTeam(testTeam);
        Long id = testTeam.getId();
        teamDao.deleteTeam(testTeam);
        EntityManager em = factory.createEntityManager();
        Assert.assertNull(em.find(Team.class, id));
    }
    
    //retrieve Team
    
    @Test(expected = IllegalArgumentException.class)
    public void retrieveTeamById_idNull_exceptionThrow() throws IllegalEntityException{
        teamDao.retrieveTeamById(null);
    }
    
    @Test
    public void retrieveTeamById_idNotIntDatabase_exceptionThrow() throws IllegalEntityException{
        Assert.assertNull(teamDao.retrieveTeamById(Long.MIN_VALUE));
    }
    
    @Test
    public void retrieveTeamById_correct_foundTeam() throws IllegalEntityException{
        teamDao.createTeam(testTeam);
        Assert.assertEquals(testTeam, teamDao.retrieveTeamById(testTeam.getId()));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void retrieveTeamsByName_nullName_exceptionThrow(){
        teamDao.retrieveTeamsByName(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void retrieveTeamsByName_emptyName_exceptionThrow(){
        teamDao.retrieveTeamsByName("");
    }
    
    @Test
    public void retrieveTeamsByName_notInDatabase_emptyList(){
        List<Team> teams = teamDao.retrieveTeamsByName("name");
        Assert.assertTrue(teams.isEmpty());
    }
   
    @Test
    public void retrieveTeamsByName_find2of3_listOf2Teams() throws IllegalEntityException{
        teamDao.createTeam(testTeam);
        Team testTeam2 = new Team();
        testTeam2.setName("secondTeam");
        testTeam2.setPlayers(new ArrayList<Player>());
        teamDao.createTeam(testTeam2);
        Team testTeam3 = new Team();
        testTeam3.setName("teamName");
        testTeam3.setPlayers(new ArrayList<Player>());
        teamDao.createTeam(testTeam3);
        
        List<Team> teams = teamDao.retrieveTeamsByName("teamName");
        Assert.assertEquals(2,teams.size());
    }
}