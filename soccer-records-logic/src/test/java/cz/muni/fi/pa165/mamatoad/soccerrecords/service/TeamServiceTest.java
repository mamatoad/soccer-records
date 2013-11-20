package cz.muni.fi.pa165.mamatoad.soccerrecords.service;

import cz.muni.fi.pa165.mamatoad.soccerrecords.service.impl.TeamServiceImpl;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.TeamTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.TeamDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Goal;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.MatchDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.PlayerDao;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Tests for TeamService.
 *
 * @author Matus Nemec
 */
@RunWith(MockitoJUnitRunner.class)
public class TeamServiceTest {

    @Mock
    private PlayerDao playerDao;
    @Mock
    private MatchDao matchDao;
    @Mock
    private TeamDao teamDao;
    @Autowired
    @InjectMocks
    private final TeamService teamService = new TeamServiceImpl();
    
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
    List<Match> matches;
    List<Goal> goals1;
    List<Goal> goals2;
    List<Goal> goals3;
    List<Team> teams;

    @Before
    public void setup() {
        // first team
        
        team = new Team();
        team.setId(1L);
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
        otherTeam.setId(2L);
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
        
        teams = new ArrayList<>();
        teams.add(team);
        teams.add(otherTeam);
        
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
        
        matches = new ArrayList<>();
        matches.add(match1);
        matches.add(match2);
        matches.add(match3);
        
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
        
        match1.setGoals(goals1);
        match2.setGoals(goals2);
        match3.setGoals(goals3);
        
        // team TOs
        
        teamTO = new TeamTO();
        teamTO.setTeamId(team.getId());
        teamTO.setTeamName(team.getName());
        teamTO.setNumberOfWins(1);
        teamTO.setNumberOfLosses(1);
        teamTO.setNumberOfTies(1);
        teamTO.setNumberOfGoalsShot(7);
        teamTO.setNumberOfGoalsReceived(8);
        
        otherTeamTO = new TeamTO();
        otherTeamTO.setTeamId(otherTeam.getId());
        otherTeamTO.setTeamName(otherTeam.getName());
        otherTeamTO.setNumberOfWins(1);
        otherTeamTO.setNumberOfLosses(1);
        otherTeamTO.setNumberOfTies(1);
        otherTeamTO.setNumberOfGoalsShot(8);
        otherTeamTO.setNumberOfGoalsReceived(7);
        
        stub(teamDao.retrieveTeamById(team.getId())).toReturn(team);

        stub(teamDao.retrieveTeamById(otherTeam.getId())).toReturn(otherTeam);
        
        stub(playerDao.retrievePlayersByTeam(team)).toReturn(players);
        
        stub(playerDao.retrievePlayersByTeam(otherTeam)).toReturn(otherPlayers);
        
        stub(matchDao.retrieveMatchesByTeam(team)).toReturn(matches);

        stub(matchDao.retrieveMatchesByTeam(otherTeam)).toReturn(matches);
        
        stub(teamDao.retrieveAllTeams()).toReturn(teams);
    }

    @Test
    public void test_add_validTeamTO_addsTeam() {
        teamService.add(teamTO);

        ArgumentCaptor<Team> argument = ArgumentCaptor.forClass(Team.class);
        verify(teamDao).createTeam(argument.capture());
        Assert.assertNotNull(argument.getValue());
        equalsTeam(team, argument.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_add_nullTeamTO_throwsInvalidArgumentException() {
        teamService.add(null);
    }

    @Test
    public void test_update_validTeamTO_updatesTeam() {
        team.setName("A very new name");
        teamTO.setTeamName(team.getName());
        
        teamService.update(teamTO);
        
        ArgumentCaptor<Team> argument = ArgumentCaptor.forClass(Team.class);
        verify(teamDao).updateTeam(argument.capture());
        Assert.assertNotNull(argument.getValue());
        equalsTeam(team, argument.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_update_nullTeamTO_throwsInvalidArgumentException() {
        teamService.update(null);
    }

    @Test
    public void test_remove_validTeamTO_removesTeam() {
        teamService.remove(otherTeamTO);
        
        ArgumentCaptor<Team> argument = ArgumentCaptor.forClass(Team.class);
        verify(teamDao).deleteTeam(argument.capture());
        Assert.assertNotNull(argument.getValue());
        equalsTeam(otherTeam, argument.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_remove_nullTeamTO_throwsInvalidArgumentException() {
        teamService.remove(null);
    }

    @Test
    public void test_getTeamById_validId_retrievesTeam() {
        TeamTO retrieved = teamService.getTeamById(teamTO.getTeamId());
        equalsTeamTO(teamTO, retrieved);
        
        TeamTO otherRetrieved = teamService.getTeamById(otherTeamTO.getTeamId());
        equalsTeamTO(otherTeamTO, otherRetrieved);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_getTeamById_nullId_throwsInvalidArgumentException() {
        teamService.getTeamById(null);
    }

    @Test
    public void test_getAllTeams_noArguments_retrievesTeams() {
        List<TeamTO> retrievedTeams = teamService.getAllTeams();
        equalsTeamTO(teamTO, retrievedTeams.get(0));
        equalsTeamTO(otherTeamTO, retrievedTeams.get(1));
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
        Assert.assertEquals("Number of losses doesn't match", expected.getNumberOfLosses(), actual.getNumberOfLosses());
        Assert.assertEquals("Number of ties doesn't match", expected.getNumberOfTies(), actual.getNumberOfTies());

        Assert.assertEquals("Number of goals shot doesn't match", expected.getNumberOfGoalsShot(), actual.getNumberOfGoalsShot());
        Assert.assertEquals("Number of goals received doesn't match", expected.getNumberOfGoalsReceived(), actual.getNumberOfGoalsReceived());
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
