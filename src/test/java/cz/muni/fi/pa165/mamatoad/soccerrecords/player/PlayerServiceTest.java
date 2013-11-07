/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.mamatoad.soccerrecords.player;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.PlayerTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.impl.PlayerServiceImpl;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.PlayerService;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.PlayerDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Goal;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.GoalDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.TeamDao;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Adriana Smijakova
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest {
    
    @Autowired
    @InjectMocks
    private PlayerService playerService = new PlayerServiceImpl();
    @Mock
    private PlayerDao playerDao;
    @Mock
    private TeamDao teamDao;
    @Mock
    private GoalDao goalDao;
    private PlayerTO playerTo;
    private PlayerTO playerTo2;
    private Team team;
    private Goal goal;
    
    public PlayerServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        //playerDao = mock(PlayerDao.class);
        //teamDao = mock(TeamDao.class);
        //goalDao = mock(GoalDao.class);
        //playerService = new PlayerServiceImpl(playerDao, teamDao, goalDao);
        
        playerTo2 = new PlayerTO(50L,"José" , true, 1L, 12L, "Zero");
        playerTo = new PlayerTO(2L, "Pedro", true, 0L, 12L, "Zero");
        
        goal = new Goal();
        goal.setId(Long.MIN_VALUE);
        goal.setPlayer(convertToEntity(playerTo2));
        
        team = new Team();
        team.setId(12L);
        team.setName("Zero");
       
        when(goalDao.retrieveGoalsByPlayer(convertToEntity(playerTo2))).thenAnswer(new Answer<List<Goal>>() {
             @Override
             public List<Goal> answer(InvocationOnMock invocation) throws Throwable {
                      List<Goal> goals = new ArrayList<>();
                      goals.add(goal);
                      return goals;
                   }
               });
        when(playerDao.retrieveAllPlayers()).thenAnswer(new Answer<List<Player>>() {
             @Override
             public List<Player> answer(InvocationOnMock invocation) throws Throwable {
                      List<Player> players = new ArrayList<>();
                      players.add(convertToEntity(playerTo));
                      players.add(convertToEntity(playerTo2));
                      return players;
                   }
               });
        when(playerDao.retrievePlayerById(2L)).thenAnswer(new Answer<Player>() {
             @Override
             public Player answer(InvocationOnMock invocation) throws Throwable {
                      return convertToEntity(playerTo);
                   }
               });
        when(playerDao.retrievePlayersByTeam(team)).thenAnswer(new Answer<List<Player>>() {
             @Override
             public List<Player> answer(InvocationOnMock invocation) throws Throwable {
                      List<Player> players = new ArrayList<>();
                      players.add(convertToEntity(playerTo));
                      players.add(convertToEntity(playerTo2));
                      return players;
                   }
               });
        when(teamDao.retrieveTeamById(team.getId())).thenAnswer(new Answer<Team>(){
             @Override
             public Team answer(InvocationOnMock invocation) throws Throwable {
                      return team;
                   }
               });
    }
    
    @After
    public void tearDown() {
    }
    //add
    @Test(expected = IllegalArgumentException.class)
    public void test_add_nullPlayer_exceptionThrow() {
        playerTo = null;
        playerService.add(playerTo);
    }
    
    @Test
    public void test_add_correct_addPlayer(){
        ArgumentCaptor<Player> argument = ArgumentCaptor.forClass(Player.class);
        playerService.add(playerTo);
        Mockito.verify(playerDao).createPlayer(argument.capture());
        playerEquals(argument.getValue(), convertToEntity(playerTo));
    }
    
    //update
    @Test(expected = IllegalArgumentException.class)
    public void test_update_nullPlayer_exceptionThrow() {
        playerTo = null;
        playerService.update(playerTo);
    }
    
    @Test
    public void test_update_correct_updatePlayer(){
        //playerTo.setPlayerId(0L);
        ArgumentCaptor<Player> argument = ArgumentCaptor.forClass(Player.class);
        playerService.update(playerTo);
        Mockito.verify(playerDao).updatePlayer(argument.capture());
        playerEquals(argument.getValue(), convertToEntity(playerTo));
    }
    
    //remove
    @Test(expected = IllegalArgumentException.class)
    public void test_remove_nullPlayer_exceptionThrow() {
        playerTo = null;
        playerService.remove(playerTo);
    }
    
    @Test
    public void test_remove_correct_removePlayer(){
        ArgumentCaptor<Player> argument = ArgumentCaptor.forClass(Player.class);
        playerService.remove(playerTo);
        Mockito.verify(playerDao).deletePlayer(argument.capture());
        playerEquals(argument.getValue(), convertToEntity(playerTo));
    }
    
    //getPlayerById
    @Test(expected = IllegalArgumentException.class)
    public void test_getPlayerById_nullId_exceptionThrow() {
        playerService.getPlayerById(null);
    }
    
    @Test
    public void test_getPlayerById_correct_getPlayer(){
        PlayerTO actual = playerService.getPlayerById(playerTo.getPlayerId());
        playerToEquals(playerTo, actual);
    }
    
    //getPlayersByTeamId
    @Test(expected = IllegalArgumentException.class)
    public void test_getPlayersByTeamId_nullId_exceptionThrow() {
        playerService.getPlayersByTeamId(null);
    }
    
    @Test
    public void test_getPlayersByTeamId_correct_getListOfPlayers(){
        
        List<PlayerTO> actual = playerService.getPlayersByTeamId(team.getId());
        
        List<PlayerTO> expected = new ArrayList<>();
        expected.add(playerTo);
        expected.add(playerTo2);
        
        playerToListEquals(expected, actual);
    }
    //getAllPlayers
    
    @Test
    public void test_getAllPlayers_correct_getPlayers(){
        
        List<PlayerTO> actual = playerService.getAllPlayers();
        
        List<PlayerTO> expected = new ArrayList<>();
        expected.add(playerTo);
        expected.add(playerTo2);
        
        playerToListEquals(expected, actual);
    }
    
    
    //convert player with null team
    private Player convertToEntity(PlayerTO playerTO) {
        Player player = new Player();
        player.setActive(playerTO.isPlayerActive());
        player.setId(playerTO.getPlayerId());
        player.setName(playerTO.getPlayerName());
        
        Team team = new Team();
        team.setId(playerTO.getTeamId());
        team.setName(playerTO.getTeamName());
        
        player.setTeam(team);
        return player;
    }
    
    private void playerEquals(Player expected, Player actual){
        Assert.assertEquals("Id doesn't match",expected.getId(), actual.getId());
        Assert.assertEquals("Activity doesn't match",expected.getActive(), actual.getActive());
        Assert.assertEquals("Name doesn't match",expected.getName(), actual.getName());
        if(expected.getTeam() == null){
            Assert.assertNull(actual.getTeam());
        }else{
            Assert.assertEquals("Id of team doesn't match",expected.getTeam().getId(), actual.getTeam().getId());
            Assert.assertEquals("Name of team doesn't match",expected.getTeam().getName(), actual.getTeam().getName());
        }
    }
    
    private void playerToEquals(PlayerTO expected, PlayerTO actual){
        Assert.assertEquals("Player goals scored doesn't match",expected.getPlayerGoalsScored(),
                actual.getPlayerGoalsScored());
        Assert.assertEquals("Player id doesn't match",expected.getPlayerId(), actual.getPlayerId());
        Assert.assertEquals("Player name doesn't match",expected.getPlayerName(), actual.getPlayerName());
        Assert.assertEquals("Player teamId doesn't match",expected.getTeamId(), actual.getTeamId());
        Assert.assertEquals("Player teamName doesn't match",expected.getTeamName(), actual.getTeamName());
    }
    
    private void playerToListEquals(List<PlayerTO> expected, List<PlayerTO> actual){
        Assert.assertEquals("Number of results doesn't match", expected.size(), actual.size());
        for(int i = 0; i<expected.size(); i++){
            playerToEquals(expected.get(i), actual.get(i));
        }
    }
}