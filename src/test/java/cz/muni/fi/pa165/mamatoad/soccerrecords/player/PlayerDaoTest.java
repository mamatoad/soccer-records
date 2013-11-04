package cz.muni.fi.pa165.mamatoad.soccerrecords.player;

import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tests for Player DAO
 *
 * @author Tomas Livora
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springConfigTest.xml"})
@Transactional
public class PlayerDaoTest {
    
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private PlayerDao playerDao;
    
    private Player player;
    private Player player2;
        
    @Before
    public void setUp() {
        player = new Player();
        player.setName("John Doe");
        player.setTeam(null);
        player.setActive(true);
        
        player2 = new Player();
        player2.setName("John Doe");
        player2.setTeam(null);
        player2.setActive(true);
    }
    
    // createPlayer()
    
    @Test(expected = DataAccessException.class)
    public void createPlayer_playerNull_exceptionThrown() {
        playerDao.createPlayer(null);
    }
    
    @Test
    public void createPlayer_playerIdNull_idAssigned() {
        player.setName("Jill Doe");
        playerDao.createPlayer(player);
        assertNotNull(player.getId());
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createPlayer_playerIdNotNull_exceptionThrown() {
        player.setId(Long.MIN_VALUE);
         playerDao.createPlayer(player);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createPlayer_playerNameNull_exceptionThrown() {
        player.setName(null);
        playerDao.createPlayer(player);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createPlayer_playerNameEmpty_exceptionThrown() {
        player.setName("");
        playerDao.createPlayer(player);
    }
    
    // updatePlayer()
    
    @Test(expected = DataAccessException.class)
    public void updatePlayer_playerNull_exceptionThrown() {
        playerDao.updatePlayer(null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updatePlayer_playerIdNull_exceptionThrown() {
        playerDao.createPlayer(player);
        player.setId(null);
        playerDao.updatePlayer(player);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updatePlayer_playerNotStored_exceptionThrown() {
        player.setId(Long.MIN_VALUE);
        playerDao.updatePlayer(player);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updatePlayer_playerNameNull_exceptionThrown() {
        playerDao.createPlayer(player);
        player.setName(null);
        playerDao.updatePlayer(player);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updatePlayer_playerNameEmpty_exceptionThrown() {
        playerDao.createPlayer(player);
        player.setName("");
        playerDao.updatePlayer(player);
    }
    
    @Test
    public void updatePlayer_playerNameChanged_valueStored() {
        player.setName("Joshua Doe");
        playerDao.createPlayer(player);
        String name = "John Van Doe";
        player.setName(name);
        playerDao.updatePlayer(player);
        
        Player storedPlayer = playerDao.retrievePlayerById(player.getId());
        assertEquals(name, storedPlayer.getName());
    }
    
    @Test
    public void updatePlayer_playerActiveChanged_valueStored() {
        player.setName("George Doe");
        playerDao.createPlayer(player);
        player.setActive(false);
        playerDao.updatePlayer(player);
        
        Player storedPlayer = playerDao.retrievePlayerById(player.getId());
        assertFalse(storedPlayer.getActive());
    }
    
    // deletePlayer()
    
    @Test(expected = DataAccessException.class)
    public void deletePlayer_playerNull_exceptionThrown() {
        playerDao.deletePlayer(null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void deletePlayer_playerIdNull_exceptionThrown() {
        playerDao.createPlayer(player);
        player.setId(null);
        playerDao.deletePlayer(player);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void deletePlayer_playerNotStored_exceptionThrown() {
        player.setId(Long.MIN_VALUE);
        playerDao.deletePlayer(player);
    }
    
    @Test
    public void deletePlayer_playerDeleted_playerNotFound() {
        playerDao.createPlayer(player);
        playerDao.deletePlayer(player);
        
        Player storedPlayer = playerDao.retrievePlayerById(player.getId());
        assertNull(storedPlayer);
    }
    
    // retrievePlayerById()
    
    @Test(expected = DataAccessException.class)
    public void retrievePlayerById_idNull_exceptionThrown() {
        playerDao.retrievePlayerById(null);
    }
    
    @Test
    public void retrievePlayerById_notExistingId_playerNotFound() {
        assertNull(playerDao.retrievePlayerById(Long.MIN_VALUE));
    }
    
    @Test
    public void retrievePlayerById_existingId_playerFound() {
        player.setName("Peter Doe");
        playerDao.createPlayer(player);
        Long id = player.getId();
        assertNotNull(playerDao.retrievePlayerById(id));
    }
    
    // retrievePlayersByName()
    
    @Test(expected = DataAccessException.class)
    public void retrievePlayersByName_nameNull_exceptionThrown() {
        playerDao.retrievePlayersByName(null);
    }
    
    @Test(expected = DataAccessException.class)
    public void retrievePlayersByName_nameEmpty_exceptionThrown() {
        playerDao.retrievePlayersByName("");
    }
    
    @Test
    public void retrievePlayersByName_nameNotExist_playerNotFound() {
        assertTrue(playerDao.retrievePlayersByName("Joshua Dow").isEmpty());
    }
    
    @Test
    public void retrievePlayersByName_nameExist_playerFound() {
        player.setName("Richard Doe");
        playerDao.createPlayer(player);
        Long id = player.getId();
        List<Player> players = playerDao.retrievePlayersByName("Richard Doe");
        assertFalse(players.isEmpty());
        assertEquals(1, players.size());
        assertEquals(id, players.get(0).getId());
    }
    
    @Test
    public void retrievePlayersByName_twoPlayersWithSameName_playersFound() {
        player.setName("Richard Dow");
        playerDao.createPlayer(player);
        player2.setName("Richard Dow");
        playerDao.createPlayer(player2);
        Long id = player.getId();
        Long id2 = player2.getId();
        List<Player> players = playerDao.retrievePlayersByName("Richard Dow");
        assertFalse(players.isEmpty());
        assertEquals(2, players.size());
    }
    
    // retrievePlayersByTeam()
    
    @Test(expected = DataAccessException.class)
    public void retrievePlayersByTeam_teamNull_exceptionThrown() {
        playerDao.retrievePlayersByTeam(null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void retrievePlayersByTeam_teamIdNull_exceptionThrown() {
        playerDao.retrievePlayersByTeam(new Team());
    }
    
    @Test(expected = IllegalEntityException.class)
    public void retrievePlayersByTeam_teamNotExist_exceptionThrown() {
        Team team = new Team();
        team.setId(Long.MIN_VALUE);
        playerDao.retrievePlayersByTeam(team);
    }
    
    @Test
    public void retrievePlayersByTeam_teamWithoutPlayers_playersNotFound() {
        Team team = new Team();
        team.setName("FCB");
        
        em.persist(team);
        
        List<Player> players = playerDao.retrievePlayersByTeam(team);
        assertTrue(players.isEmpty());
    }
    
    @Test
    public void retrievePlayersByTeam_teamWithOnePlayer_playerFound() {
        Team team = new Team();
        team.setName("FCB");
        
        em.persist(team);
        
        player.setTeam(team);
        player.setName("James Doe");
        playerDao.createPlayer(player);
        Long id = player.getId();
        
        List<Player> players = playerDao.retrievePlayersByTeam(team);
        assertFalse(players.isEmpty());
        assertEquals(1, players.size());
        assertEquals(id, players.get(0).getId());
    }
    
    // retrievePlayersByActivity()
    
    @Test
    public void retrievePlayersByActivity_findActivePlayer_playerFound() {
        player.setName("Peter Dow");
        playerDao.createPlayer(player);
        Long id = player.getId();
        List<Player> players = playerDao.retrievePlayersByActivity(true);
        assertFalse(players.isEmpty());
        assertTrue(players.contains(player));
    }
    
}
