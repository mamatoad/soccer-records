package cz.muni.fi.pa165.mamatoad.soccerrecords.player;

import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
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
 * @author Tomas Livora
 */
public class PlayerDaoTest {
    
    private static String persistanceName = "pa165";
    private static Map<String,String> properties = new HashMap<>();
    
    private static EntityManagerFactory emf;
    private static PlayerDao playerDao;
    
    private Player player;
    private Player player2;

    @BeforeClass
    public static void setUpClass() {
        properties.put("hibernate.connection.driver_class", "org.apache.derby.jdbc.EmbeddedDriver");
        properties.put("hibernate.connection.url", "jdbc:derby:memory:PlayerTestsDB;create=true");
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
        
        player2 = new Player();
        player2.setName("John Doe");
        player2.setTeam(null);
        player2.setActive(true);
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
    
    @Test
    public void deletePlayer_playerDeleted_playerNotFound() throws IllegalEntityException {
        playerDao.createPlayer(player);
        playerDao.deletePlayer(player);
        
        Player storedPlayer = playerDao.retrievePlayerById(player.getId());
        assertNull(storedPlayer);
    }
    
    // retrievePlayerById()
    
    @Test(expected = IllegalArgumentException.class)
    public void retrievePlayerById_idNull_exceptionThrown() throws IllegalEntityException {
        playerDao.retrievePlayerById(null);
    }
    
    @Test
    public void retrievePlayerById_notExistingId_playerNotFound() throws IllegalEntityException {
        assertNull(playerDao.retrievePlayerById(Long.MIN_VALUE));
    }
    
    @Test
    public void retrievePlayerById_existingId_playerFound() throws IllegalEntityException {
        playerDao.createPlayer(player);
        Long id = player.getId();
        assertNotNull(playerDao.retrievePlayerById(id));
    }
    
    // retrievePlayersByName()
    
    @Test(expected = IllegalArgumentException.class)
    public void retrievePlayersByName_nameNull_exceptionThrown() throws IllegalEntityException {
        playerDao.retrievePlayersByName(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void retrievePlayersByName_nameEmpty_exceptionThrown() throws IllegalEntityException {
        playerDao.retrievePlayersByName("");
    }
    
    @Test
    public void retrievePlayersByName_nameNotExist_playerNotFound() throws IllegalEntityException {
        assertTrue(playerDao.retrievePlayersByName("John Doe").isEmpty());
    }
    
    @Test
    public void retrievePlayersByName_nameExist_playerFound() throws IllegalEntityException {
        playerDao.createPlayer(player);
        Long id = player.getId();
        List<Player> players = playerDao.retrievePlayersByName("John Doe");
        assertFalse(players.isEmpty());
        assertEquals(1, players.size());
        assertEquals(id, players.get(0).getId());
    }
    
    @Test
    public void retrievePlayersByName_twoPlayersWithSameName_playersFound() throws IllegalEntityException {
        playerDao.createPlayer(player);
        playerDao.createPlayer(player2);
        Long id = player.getId();
        Long id2 = player2.getId();
        List<Player> players = playerDao.retrievePlayersByName("John Doe");
        assertFalse(players.isEmpty());
        assertEquals(2, players.size());
    }
    
    // retrievePlayersByTeam()
    
    @Test(expected = IllegalArgumentException.class)
    public void retrievePlayersByTeam_teamNull_exceptionThrown() throws IllegalEntityException {
        playerDao.retrievePlayersByTeam(null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void retrievePlayersByTeam_teamIdNull_exceptionThrown() throws IllegalEntityException {
        playerDao.retrievePlayersByTeam(new Team());
    }
    
    @Test(expected = IllegalEntityException.class)
    public void retrievePlayersByTeam_teamNotExist_exceptionThrown() throws IllegalEntityException {
        Team team = new Team();
        team.setId(Long.MIN_VALUE);
        playerDao.retrievePlayersByTeam(team);
    }
    
    @Test
    public void retrievePlayersByTeam_teamWithoutPlayers_playersNotFound() throws IllegalEntityException {
        Team team = new Team();
        team.setName("FCB");
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(team);
        em.getTransaction().commit();
        em.close();
        
        List<Player> players = playerDao.retrievePlayersByTeam(team);
        assertTrue(players.isEmpty());
    }
    
    @Test
    public void retrievePlayersByTeam_teamWithOnePlayer_playerFound() throws IllegalEntityException {
        Team team = new Team();
        team.setName("FCB");
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(team);
        em.getTransaction().commit();
        em.close();
        
        player.setTeam(team);
        playerDao.createPlayer(player);
        Long id = player.getId();
        
        List<Player> players = playerDao.retrievePlayersByTeam(team);
        assertFalse(players.isEmpty());
        assertEquals(1, players.size());
        assertEquals(id, players.get(0).getId());
    }
    
    // retrievePlayersByActivity()
    
    @Test
    public void retrievePlayersByActivity_noStoredPlayer_playerNotFound() throws IllegalEntityException {
        List<Player> players = playerDao.retrievePlayersByActivity(true);
        assertTrue(players.isEmpty());
    }
    
    @Test
    public void retrievePlayersByActivity_findActivePlayer_playerFound() throws IllegalEntityException {
        playerDao.createPlayer(player);
        Long id = player.getId();
        List<Player> players = playerDao.retrievePlayersByActivity(true);
        assertFalse(players.isEmpty());
        assertEquals(1, players.size());
        assertEquals(id, players.get(0).getId());
    }
    
    @Test
    public void retrievePlayersByActivity_findNotActivePlayer_playerNotFound() throws IllegalEntityException {
        playerDao.createPlayer(player);
        List<Player> players = playerDao.retrievePlayersByActivity(false);
        assertTrue(players.isEmpty());
    }
    
}
