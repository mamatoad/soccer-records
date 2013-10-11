package cz.muni.fi.pa165.mamatoad.soccerrecords.test.player;

import cz.muni.fi.pa165.mamatoad.soccerrecords.player.JpaPlayerDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.player.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.player.PlayerDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for Player DAO
 *
 * @author livthomas
 */
public class PlayerDaoTests {
    
    private static String persistanceName = "pa165";
    private static Map<String,String> properties = new HashMap<>();
    
    private static EntityManagerFactory emf;
    private static PlayerDao playerDao;
    
    private Player player;

    @BeforeClass
    public static void setUpClass() {
        properties.put("hibernate.connection.driver_class", "org.apache.derby.jdbc.ClientDriver");
        properties.put("hibernate.connection.url", "jdbc:derby://localhost:1527/memory:PlayerTestsDB;create=true");
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
    }
    
    @AfterClass
    public static void tearDownClass() {
        
    }
    
    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory(persistanceName, properties);
        playerDao = new JpaPlayerDao(emf);
        
        player = new Player();
        player.setName("John Doe");
        player.setTeam(null);
        player.setActive(true);
    }
    
    @After
    public void tearDown() {
        emf.close();
    }
    
    // createPlayer()
    
    @Test(expected = IllegalArgumentException.class)
    public void createPlayer_playerNull_exceptionThrown() throws IllegalEntityException {
        playerDao.createPlayer(null);
    }
    
    @Test
    public void createPlayer_playerIdNull_idAssigned() throws IllegalEntityException {
        playerDao.createPlayer(player);
        assertNotNull(player.getId());
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createPlayer_playerIdNotNull_exceptionThrown() throws IllegalEntityException {
        player.setId(Long.MIN_VALUE);
        playerDao.createPlayer(player);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createPlayer_playerNameNull_exceptionThrown() throws IllegalEntityException {
        player.setName(null);
        playerDao.createPlayer(player);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void createPlayer_playerNameEmpty_exceptionThrown() throws IllegalEntityException {
        player.setName("");
        playerDao.createPlayer(player);
    }
    
    // updatePlayer()
    
    @Test(expected = IllegalArgumentException.class)
    public void updatePlayer_playerNull_exceptionThrown() throws IllegalEntityException {
        playerDao.updatePlayer(null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updatePlayer_playerIdNull_exceptionThrown() throws IllegalEntityException {
        playerDao.createPlayer(player);
        player.setId(null);
        playerDao.updatePlayer(player);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updatePlayer_playerNotStored_exceptionThrown() throws IllegalEntityException {
        player.setId(Long.MIN_VALUE);
        playerDao.updatePlayer(player);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updatePlayer_playerNameNull_exceptionThrown() throws IllegalEntityException {
        playerDao.createPlayer(player);
        player.setName(null);
        playerDao.updatePlayer(player);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void updatePlayer_playerNameEmpty_exceptionThrown() throws IllegalEntityException {
        playerDao.createPlayer(player);
        player.setName("");
        playerDao.updatePlayer(player);
    }
    
    @Test
    public void updatePlayer_playerNameChanged_valueStored() throws IllegalEntityException {
        playerDao.createPlayer(player);
        String name = "John Van Doe";
        player.setName(name);
        playerDao.updatePlayer(player);
        
        Player storedPlayer = playerDao.retrievePlayerById(player.getId());
        assertEquals(name, storedPlayer.getName());
    }
    
    @Test
    public void updatePlayer_playerActiveChanged_valueStored() throws IllegalEntityException {
        playerDao.createPlayer(player);
        player.setActive(false);
        playerDao.updatePlayer(player);
        
        Player storedPlayer = playerDao.retrievePlayerById(player.getId());
        assertFalse(storedPlayer.getActive());
    }
    
    // deletePlayer()
    
    @Test(expected = IllegalArgumentException.class)
    public void deletePlayer_playerNull_exceptionThrown() throws IllegalEntityException {
        playerDao.deletePlayer(null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void deletePlayer_playerIdNull_exceptionThrown() throws IllegalEntityException {
        playerDao.createPlayer(player);
        player.setId(null);
        playerDao.deletePlayer(player);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void deletePlayer_playerNotStored_exceptionThrown() throws IllegalEntityException {
        player.setId(Long.MIN_VALUE);
        playerDao.deletePlayer(player);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void deletePlayer_playerNameNull_exceptionThrown() throws IllegalEntityException {
        playerDao.createPlayer(player);
        player.setName(null);
        playerDao.deletePlayer(player);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void deletePlayer_playerNameEmpty_exceptionThrown() throws IllegalEntityException {
        playerDao.createPlayer(player);
        player.setName("");
        playerDao.deletePlayer(player);
    }
    
    @Test
    public void deletePlayer_playerDeleted_playerNotFound() throws IllegalEntityException {
        playerDao.createPlayer(player);
        playerDao.deletePlayer(player);
        
        Player storedPlayer = playerDao.retrievePlayerById(player.getId());
        assertNull(storedPlayer);
    }
    
}
