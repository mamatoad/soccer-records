package cz.muni.fi.pa165.mamatoad.soccerrecords.team;

import cz.muni.fi.pa165.mamatoad.soccerrecords.goal.Goal;
import cz.muni.fi.pa165.mamatoad.soccerrecords.goal.GoalDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.match.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.match.MatchDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.player.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.player.PlayerDao;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Tests for TeamService.
 *
 * @author Matus Nemec
 */
public class TeamServiceTest {

    private PlayerDao playerDao;
    private MatchDao matchDao;
    private TeamDao teamDao;
    private GoalDao goalDao;
    private TeamService teamService;
    
    Team team;
    Team otherTeam;
    TeamTO teamTO;
    TeamTO otherTeamTO;
    List<Player> players;
    List<Player> otherPlayers;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Match match1;
    Match match2;
    Match match3;
    List<Goal> goals1;
    List<Goal> goals2;
    List<Goal> goals3;

    @Before
    public void setup() {
        playerDao = mock(PlayerDao.class);
        matchDao = mock(MatchDao.class);
        goalDao = mock(GoalDao.class);
        teamDao = mock(TeamDao.class);
        
        teamService = new TeamServiceImpl(playerDao, matchDao, teamDao);

        // first team
        
        team = new Team();
        team.setId(123L);
        team.setName("Test FC");

        players = new ArrayList<>();
        
        player1 = new Player();
        player1.setName("Test Player 1");
        player1.setTeam(team);
        player1.setActive(true);
        players.add(player1);
        
        player2 = new Player();
        player2.setName("Test Player 2");
        player2.setTeam(team);
        player2.setActive(true);
        players.add(player2);

        team.setPlayers(players);
        
        // second team
        
        otherTeam = new Team();
        otherTeam.setId(123L);
        otherTeam.setName("Test FC");

        otherPlayers = new ArrayList<>();
        
        player3 = new Player();
        player3.setName("Test Player 3");
        player3.setTeam(otherTeam);
        player3.setActive(true);
        otherPlayers.add(player3);
        
        player4 = new Player();
        player4.setName("Test Player 4");
        player4.setTeam(otherTeam);
        player4.setActive(true);
        otherPlayers.add(player4);

        otherTeam.setPlayers(otherPlayers);
        
        // matches
        
        // match 1 - tie 2:2
        match1 = new Match();
        match1.setId(111L);
        match1.setEventDate(LocalDate.now().minusWeeks(2));
        match1.setHomeTeam(team);
        match1.setVisitingTeam(otherTeam);
        
        // match 2 - winner team 4:3
        match2 = new Match();
        match2.setId(222L);
        match2.setEventDate(LocalDate.now().minusWeeks(1));
        match2.setHomeTeam(otherTeam);
        match2.setVisitingTeam(team);
        
        // match 3 - winner otherTeam 3:1
        match3 = new Match();
        match3.setId(333L);
        match3.setEventDate(LocalDate.now());
        match3.setHomeTeam(otherTeam);
        match3.setVisitingTeam(team);
        
        // goals
        goals1 = new ArrayList<>();
        goals2 = new ArrayList<>();
        goals3 = new ArrayList<>();
        // goals shot by team in match1: 2
        goals1.add(makeGoal(11111L, match1, player1, LocalTime.now().plusMinutes(12), team));
        goals1.add(makeGoal(22222L, match1, player2, LocalTime.now().plusMinutes(30), team));
        // goals shot by team in match2: 4
        goals2.add(makeGoal(33333L, match2, player1, LocalTime.now().plusMinutes(10), team));
        goals2.add(makeGoal(44444L, match2, player2, LocalTime.now().plusMinutes(15), team));
        goals2.add(makeGoal(55555L, match2, player1, LocalTime.now().plusMinutes(40), team));
        goals2.add(makeGoal(66666L, match2, player2, LocalTime.now().plusMinutes(57), team));
        // goals shot by team in match3: 1
        goals3.add(makeGoal(77777L, match3, player2, LocalTime.now().plusMinutes(70), team));
        
        // goals shot by otherTeam in match1: 2
        goals1.add(makeGoal(88888L, match1, player3, LocalTime.now().plusMinutes(12), otherTeam));
        goals1.add(makeGoal(99999L, match1, player4, LocalTime.now().plusMinutes(30), otherTeam));
        // goals shot by otherTeam in match2: 3
        goals2.add(makeGoal(111110L, match2, player3, LocalTime.now().plusMinutes(10), otherTeam));
        goals2.add(makeGoal(222220L, match2, player4, LocalTime.now().plusMinutes(15), otherTeam));
        goals2.add(makeGoal(333330L, match2, player3, LocalTime.now().plusMinutes(40), otherTeam));
        // goals shot by team in match3: 3
        goals3.add(makeGoal(444440L, match3, player3, LocalTime.now().plusMinutes(5), otherTeam));
        goals3.add(makeGoal(555550L, match3, player3, LocalTime.now().plusMinutes(15), otherTeam));
        goals3.add(makeGoal(666660L, match3, player4, LocalTime.now().plusMinutes(50), otherTeam));
        
        // team TOs
        
        teamTO = new TeamTO();
        teamTO.setTeamId(team.getId());
        teamTO.setTeamName(team.getName());
        teamTO.setNumberOfWins(1);
        teamTO.setNumberOfLoses(1);
        teamTO.setNumberOfTies(1);
        teamTO.setNumberOfGoalsShot(7);
        teamTO.setNumberOfGoalsRecieved(8);
        
        otherTeamTO = new TeamTO();
        otherTeamTO.setTeamId(otherTeam.getId());
        otherTeamTO.setTeamName(otherTeam.getName());
        otherTeamTO.setNumberOfWins(1);
        otherTeamTO.setNumberOfLoses(1);
        otherTeamTO.setNumberOfTies(1);
        otherTeamTO.setNumberOfGoalsShot(8);
        otherTeamTO.setNumberOfGoalsRecieved(7);

        when(teamDao.retrieveTeamById(123L)).thenAnswer(new Answer<Team>() {
            @Override
            public Team answer(InvocationOnMock invocation) throws Throwable {
                return team;
            }
        });
        
        when(playerDao.retrievePlayersByTeam(team)).thenAnswer(new Answer<List<Player>>() {
            @Override
            public List<Player> answer(InvocationOnMock invocation) throws Throwable {
                return players;
            }
        });
        
        when(playerDao.retrievePlayersByTeam(otherTeam)).thenAnswer(new Answer<List<Player>>() {
            @Override
            public List<Player> answer(InvocationOnMock invocation) throws Throwable {
                return otherPlayers;
            }
        });
        
        when(goalDao.retrieveGoalsByMatch(match1)).thenAnswer(new Answer<List<Goal>>() {
            @Override
            public List<Goal> answer(InvocationOnMock invocation) throws Throwable {
                return goals1;
            }
        });
        
        when(goalDao.retrieveGoalsByMatch(match2)).thenAnswer(new Answer<List<Goal>>() {
            @Override
            public List<Goal> answer(InvocationOnMock invocation) throws Throwable {
                return goals2;
            }
        });
        
        when(goalDao.retrieveGoalsByMatch(match3)).thenAnswer(new Answer<List<Goal>>() {
            @Override
            public List<Goal> answer(InvocationOnMock invocation) throws Throwable {
                return goals3;
            }
        });
    }

    @Test
    public void add_validTeamTO_addsTeam() {
        teamService.add(teamTO);

        ArgumentCaptor<Team> argument = ArgumentCaptor.forClass(Team.class);
        verify(teamDao).createTeam(argument.capture());
        Assert.assertNotNull(argument.getValue());
        equalsTeam(team, argument.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_nullTeamTO_throwsInvalidArgumentException() {
        teamService.add(null);
    }

    @Test
    public void update_validTeamTO_updatesTeam() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_nullTeamTO_throwsInvalidArgumentException() {
        teamService.update(null);
    }

    @Test
    public void remove_validTeamTO_removesTeam() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void remove_nullTeamTO_throwsInvalidArgumentException() {
        teamService.remove(null);
    }

    @Test
    public void getTeamById_validId_retrievesTeam() {
        TeamTO retrieved = teamService.getTeamById(teamTO.getTeamId());
        equalsTeamTO(teamTO, retrieved);
        
        TeamTO otherRetrieved = teamService.getTeamById(otherTeamTO.getTeamId());
        equalsTeamTO(otherTeamTO, otherRetrieved);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTeamById_nullId_throwsInvalidArgumentException() {
        teamService.getTeamById(null);
    }

    @Test
    public void getAllTeams_noArguments_retrievesTeams() {
    }

    private void equalsTeam(Team expected, Team actual) {
        Assert.assertEquals("Ids don't match", expected.getId(), actual.getId());
        Assert.assertEquals("Names don't match", expected.getName(), actual.getName());
        Assert.assertEquals("Players don't match", expected.getPlayers(), actual.getPlayers());
    }

    private void equalsTeamTO(TeamTO expected, TeamTO actual) {
        Assert.assertEquals("Ids don't match", expected.getTeamId(), actual.getTeamId());
        Assert.assertEquals("Names don't match", expected.getTeamName(), actual.getTeamName());

        Assert.assertEquals("Number of wins doesn't match", expected.getNumberOfWins(), actual.getNumberOfWins());
        Assert.assertEquals("Number of losses doesn't match", expected.getNumberOfLoses(), actual.getNumberOfLoses());
        Assert.assertEquals("Number of ties doesn't match", expected.getNumberOfTies(), actual.getNumberOfTies());

        Assert.assertEquals("Number of goals shot doesn't match", expected.getNumberOfGoalsShot(), actual.getNumberOfGoalsShot());
        Assert.assertEquals("Number of goals received doesn't match", expected.getNumberOfGoalsRecieved(), actual.getNumberOfGoalsRecieved());
    }
    
    private Goal makeGoal(Long id, Match match, Player player, LocalTime time, Team shootingTeam) {
        Goal goal = new Goal();
        goal.setId(id);
        goal.setMatch(match);
        goal.setPlayer(player);
        goal.setShootingTime(time);
        goal.setTeam(shootingTeam);
        return goal;
    }
}
