package cz.muni.fi.pa165.mamatoad.soccerrecords.service;

import cz.muni.fi.pa165.mamatoad.soccerrecords.service.impl.GoalServiceImpl;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.GoalTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.GoalService;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.GoalDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Goal;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.MatchDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.PlayerDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.TeamDao;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert.*;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
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
 * @author Maros Klimovsky
 */
@RunWith(MockitoJUnitRunner.class)
public class GoalServiceTest {
    @Mock
    private GoalDao goalDao;
    @Mock
    private PlayerDao playerDao;
    @Mock
    private MatchDao matchDao;
    @Mock
    private TeamDao teamDao;
    @Autowired
    @InjectMocks
    private GoalService goalService = new GoalServiceImpl();
    private Match match;
    private Team team;
    private Team otherTeam;
    private Player player;
    private Goal goal;
    GoalTO goalTo;
    
    @Before
    public void setup(){
       
        team = new Team();
        team.setId(13L);
        team.setName("Balerinas");
        team.setPlayers(null);
        
        otherTeam = new Team();
        otherTeam.setId(66L);
        otherTeam.setName("Bleeders");
        otherTeam.setPlayers(null);
        
        match = new Match();
        match.setId(96L);
        match.setHomeTeam(team);
        match.setVisitingTeam(otherTeam);
        match.setEventDate(LocalDate.now());
        
        player = new Player();
        player.setId(254L);
        player.setActive(true);
        player.setName("Joe Toothless");
        player.setTeam(team);
        
        goal = new Goal();
        goal.setMatch(match);
        goal.setPlayer(player);
        goal.setTeam(team);
        goal.setShootingTime(LocalTime.MIDNIGHT);
        
        goalTo = new GoalTO();
        goalTo.setTeamId(goal.getTeam().getId());
        goalTo.setTeamName(goal.getTeam().getName());
        goalTo.setMatchId(goal.getMatch().getId());
        goalTo.setPlayerId(goal.getPlayer().getId());
        goalTo.setPlayerName(goal.getPlayer().getName());
        goalTo.setTime(goal.getShootingTime());
        
        
        stub(teamDao.retrieveTeamById(13L)).toReturn(team);

        stub(teamDao.retrieveTeamById(66L)).toReturn(otherTeam);

        stub(matchDao.retrieveMatchById(96L)).toReturn(match);
        
        stub(playerDao.retrievePlayerById(254L)).toReturn(player);

        when(goalDao.retrieveGoalById(5L)).thenAnswer(new Answer<Goal>() {
             @Override
             public Goal answer(InvocationOnMock invocation) throws Throwable {
                      goal.setId(5L);
                      return goal;
                   }
               });
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void test_add_nullGoalTO_throwsIllegalArgumentException(){
        goalTo = null;
        goalService.add(goalTo);
        
    }
    
    @Test
    public void test_add_validGoalTO_addsGoal(){
        
        goalService.add(goalTo);
        ArgumentCaptor<Goal> argument = ArgumentCaptor.forClass(Goal.class);
        Mockito.verify(goalDao).createGoal(argument.capture());
        goalEquals(goal,argument.getValue());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void test_update_nullGoalTO_throwsIllegalArgumentException(){
        goalTo = null;
        goalService.update(goalTo);
    }
    
    @Test
    public void test_update_validGoalTO_updatesGoal(){
        goalTo.setGoalId(5L);
        goalTo.setTeamId(otherTeam.getId());
        goalTo.setTeamName(otherTeam.getName());
        goalService.update(goalTo);
        ArgumentCaptor<Goal> argument = ArgumentCaptor.forClass(Goal.class);
        Mockito.verify(goalDao).updateGoal(argument.capture());
        
        Assert.assertEquals(otherTeam.getId(),argument.getValue().getTeam().getId());
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void test_remove_nullGoalTO_throwsIllegalArgumentException(){
        goalTo = null;
        goalService.remove(goalTo);
    }
    
    @Test
    public void test_remove_validGoalTO_removesGoal(){
        goalService.remove(goalTo);
        ArgumentCaptor<Goal> argument = ArgumentCaptor.forClass(Goal.class);
        Mockito.verify(goalDao).deleteGoal(argument.capture());
        goalEquals(goal,argument.getValue());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void test_getGoalsByMatchId_nullId_throwsIllegalArgumentException(){
        Long id = null;
        goalService.getGoalsByMatchId(id);
    }
    
    @Test
    public void test_getGoalsByMatchId_validId_returnsGoal(){
        when(goalDao.retrieveGoalsByMatch(match)).thenAnswer(new Answer<List<Goal>>() {
             @Override
             public List<Goal> answer(InvocationOnMock invocation) throws Throwable {
                      goal.setId(5L);
                      List<Goal> goals = new ArrayList<>();
                      goals.add(goal);
                      return goals;
                   }
               });
        List<GoalTO> retrievedGoalTOList = goalService.getGoalsByMatchId(match.getId());
        List<GoalTO> expected = new ArrayList<>();
        goalTo.setGoalId(goal.getId());
        expected.add(goalTo);
        
        goalTOEquals(expected.get(0),retrievedGoalTOList.get(0));
    }
    
    private void goalTOEquals(GoalTO expected, GoalTO returned){
        Assert.assertEquals("Ids don't match",expected.getGoalId(), returned.getGoalId());
        Assert.assertEquals("Matches id doesn't match",expected.getMatchId(), returned.getMatchId());
        Assert.assertEquals("Player's id doesn't match",expected.getPlayerId(), returned.getPlayerId());
        Assert.assertEquals("Player's name doesn't match",expected.getPlayerName(), returned.getPlayerName());
        Assert.assertEquals("Team id doesn't match",expected.getTeamId(), returned.getTeamId());
        Assert.assertEquals("Team name doesn't match",expected.getTeamName(), returned.getTeamName());
        Assert.assertEquals("Time doesn't match",expected.getTime(), returned.getTime());
        
        
    }
    
    private void goalEquals(Goal expected, Goal returned){
        Assert.assertEquals("Id doesn't match",expected.getId(), returned.getId());
        Assert.assertEquals("Match doesn't match",expected.getMatch(), returned.getMatch());
        Assert.assertEquals("Player doesn't match",expected.getPlayer(), returned.getPlayer());
        Assert.assertEquals("Team doesn't match",expected.getTeam(), returned.getTeam());
        Assert.assertEquals("Time doesn't match",expected.getShootingTime(), returned.getShootingTime());
        
        
    }
}
