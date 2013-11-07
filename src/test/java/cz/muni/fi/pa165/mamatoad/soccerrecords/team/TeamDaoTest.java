package cz.muni.fi.pa165.mamatoad.soccerrecords.team;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.TeamDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Player;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
 * Tests for TeamDao
 *
 * @author Adriana Smijakova
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springConfigTest.xml"})
@Transactional
public class TeamDaoTest {
    
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private TeamDao teamDao;
    
    private Team testTeam;
  
    @Before
    public void setUp() {
        
        testTeam = new Team();
        testTeam.setName("teamName");
        testTeam.setPlayers(new ArrayList<Player>());
    }
    
    //Tests for createTeam() method
    
    @Test(expected = DataAccessException.class)
    public void test_createTeam_nullTeam_exceptionThrow() {
        testTeam = null;
        
        teamDao.createTeam(testTeam);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_createTeam_nullTeamName_exceptionThrow() {
        testTeam.setName(null);
        
        teamDao.createTeam(testTeam);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_createTeam_emptyTeamName_exceptionThrow() {
        testTeam.setName("");
        
        teamDao.createTeam(testTeam);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_createTeam_setTeamId_exceptionThrow() {
        testTeam.setId(Long.MIN_VALUE);
        
        teamDao.createTeam(testTeam);
    }
    
    @Test
    public void test_createTeam_correct_createTeam() {
        teamDao.createTeam(testTeam);
        Assert.assertNotNull(testTeam.getId());
    }
    
    //Tests for updateTeam() method
    
    @Test(expected = DataAccessException.class)
    public void test_updateTeam_nullTeam_exceptionThrow() {
        testTeam = null;
        teamDao.updateTeam(testTeam);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_updateTeam_teamNotInDatabaseWithId_exceptionThrow() {
        testTeam.setId(Long.MIN_VALUE);
        teamDao.updateTeam(testTeam);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_updateTeam_nullTeamName_exceptionThrow() {
        teamDao.createTeam(testTeam);
        
        testTeam.setName(null);
        
        teamDao.updateTeam(testTeam);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_updateTeam_emptyTeamName_exceptionThrow() {
        teamDao.createTeam(testTeam);
        
        testTeam.setName("");
        
        teamDao.updateTeam(testTeam);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_updateTeam_teamIdNull_exceptionThrow() {
        teamDao.createTeam(testTeam);
        
        testTeam.setId(null);
        
        teamDao.updateTeam(testTeam);
    }
    
    @Test
    public void test_updateTeam_correct_updateTeam() {
        teamDao.createTeam(testTeam);
        
        testTeam.setName("newName");
        
        teamDao.updateTeam(testTeam);
        
        Team actualTeam = em.find(Team.class, testTeam.getId());
        
        Assert.assertEquals("Team was not updated", testTeam, actualTeam);
    }
    
    //Tests for deleteTeam() method
    
    @Test(expected = DataAccessException.class)
    public void test_deleteTeam_teamNull_exceptionThrow() {
        testTeam = null;
        teamDao.deleteTeam(testTeam);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_deleteTeam_teamNotInDatabase_exceptionThrow() {
        testTeam.setId(Long.MIN_VALUE);
        teamDao.deleteTeam(testTeam);
    }
    
    @Test
    public void test_deleteTeam_correct_teamDeleted() {
        teamDao.createTeam(testTeam);
        Long id = testTeam.getId();
        teamDao.deleteTeam(testTeam);
        Assert.assertNull(em.find(Team.class, id));
    }
    
    //retrieve Team
    
    @Test(expected = DataAccessException.class)
    public void test_retrieveTeamById_idNull_exceptionThrow() {
        teamDao.retrieveTeamById(null);
    }
    
    @Test
    public void test_retrieveTeamById_idNotIntDatabase_exceptionThrow() {
        Assert.assertNull(teamDao.retrieveTeamById(Long.MIN_VALUE));
    }
    
    @Test
    public void test_retrieveTeamById_correct_foundTeam() {
        teamDao.createTeam(testTeam);
        Assert.assertEquals(testTeam, teamDao.retrieveTeamById(testTeam.getId()));
    }
    
    @Test(expected = DataAccessException.class)
    public void test_retrieveTeamsByName_nullName_exceptionThrow(){
        teamDao.retrieveTeamsByName(null);
    }
    
    @Test(expected = DataAccessException.class)
    public void test_retrieveTeamsByName_emptyName_exceptionThrow(){
        teamDao.retrieveTeamsByName("");
    }
    
    @Test
    public void test_retrieveTeamsByName_notInDatabase_emptyList(){
        List<Team> teams = teamDao.retrieveTeamsByName("invalidName");
        Assert.assertTrue(teams.isEmpty());
    }
   
    @Test
    public void test_retrieveTeamsByName_find2of3_listOf2Teams() {
        testTeam.setName("specialName");
        teamDao.createTeam(testTeam);
        Team testTeam2 = new Team();
        testTeam2.setName("secondTeam");
        testTeam2.setPlayers(new ArrayList<Player>());
        teamDao.createTeam(testTeam2);
        Team testTeam3 = new Team();
        testTeam3.setName("specialName");
        testTeam3.setPlayers(new ArrayList<Player>());
        teamDao.createTeam(testTeam3);
        
        List<Team> teams = teamDao.retrieveTeamsByName("specialName");
        Assert.assertEquals(2,teams.size());
    }
}