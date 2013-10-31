package cz.muni.fi.pa165.mamatoad.soccerrecords.match;

import cz.muni.fi.pa165.mamatoad.soccerrecords.goal.GoalDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.team.TeamDao;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Tests for Match service
 *
 * @author Tomas Livora
 */
public class MatchServiceTest {

    private GoalDao goalDao;
    private MatchDao matchDao;
    private TeamDao teamDao;

    private MatchService matchService;

    private Match match;
    private MatchTO matchTo;

    @Before
    public void setUp() {
        goalDao = mock(GoalDao.class);
        matchDao = mock(MatchDao.class);
        teamDao = mock(TeamDao.class);

        matchService = new MatchServiceImpl(goalDao, matchDao, teamDao);

        Long matchId = 1L;
        Long homeTeamId = 1L;
        Long visitingTeamId = 2L;
        String homeTeamName = "FC Barcelona";
        String visitingTeamName = "Real Madrid";

        final Team homeTeam = new Team();
        homeTeam.setId(homeTeamId);
        homeTeam.setName(homeTeamName);

        final Team visitingTeam = new Team();
        visitingTeam.setId(visitingTeamId);
        visitingTeam.setName(visitingTeamName);

        match = new Match();
        match.setId(matchId);
        match.setEventDate(LocalDate.now());
        match.setHomeTeam(homeTeam);
        match.setVisitingTeam(visitingTeam);

        matchTo = new MatchTO();
        matchTo.setMatchId(matchId);
        matchTo.setEventDate(LocalDate.now());
        matchTo.setHomeTeamId(homeTeamId);
        matchTo.setHomeTeamName(homeTeamName);
        matchTo.setHomeTeamScore(0);
        matchTo.setVisitingTeamId(visitingTeamId);
        matchTo.setVisitingTeamName(visitingTeamName);
        matchTo.setVisitingTeamScore(0);
        matchTo.setWinnerTeamId(null);

        when(matchDao.retrieveMatchById(matchId)).thenAnswer(new Answer<Match>() {
            @Override
            public Match answer(InvocationOnMock invocation) throws Throwable {
                return match;
            }
        });

        when(teamDao.retrieveTeamById(homeTeamId)).thenAnswer(new Answer<Team>() {
            @Override
            public Team answer(InvocationOnMock invocation) throws Throwable {
                return homeTeam;
            }
        });

        when(teamDao.retrieveTeamById(visitingTeamId)).thenAnswer(new Answer<Team>() {
            @Override
            public Team answer(InvocationOnMock invocation) throws Throwable {
                return visitingTeam;
            }
        });

        when(matchDao.retrieveMatchesByTeam(homeTeam)).thenAnswer(new Answer<List<Match>>() {
            @Override
            public List<Match> answer(InvocationOnMock invocation) throws Throwable {
                List<Match> matches = new ArrayList<>();
                matches.add(match);
                return matches;
            }
        });

        when(matchDao.retrieveAllMatches()).thenAnswer(new Answer<List<Match>>() {
            @Override
            public List<Match> answer(InvocationOnMock invocation) throws Throwable {
                List<Match> matches = new ArrayList<>();
                matches.add(match);
                return matches;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_matchNull_exceptionThrown() {
        matchService.add(null);
    }

    @Test
    public void add_matchValid_matchAdded() {
        match.setId(null);
        matchTo.setMatchId(null);
        matchService.add(matchTo);
        ArgumentCaptor<Match> argument = ArgumentCaptor.forClass(Match.class);
        Mockito.verify(matchDao).createMatch(argument.capture());
        assertDeepEquals(match, argument.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_matchNull_exceptionThrown() {
        matchService.update(null);
    }

    @Test
    public void update_matchValid_matchUpdated() {
        matchService.update(matchTo);
        ArgumentCaptor<Match> argument = ArgumentCaptor.forClass(Match.class);
        Mockito.verify(matchDao).updateMatch(argument.capture());
        assertDeepEquals(match, argument.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void remove_matchNull_exceptionThrown() {
        matchService.remove(null);
    }

    @Test
    public void remove_matchValid_matchRemoved() {
        matchService.remove(matchTo);
        ArgumentCaptor<Match> argument = ArgumentCaptor.forClass(Match.class);
        Mockito.verify(matchDao).deleteMatch(argument.capture());
        assertDeepEquals(match, argument.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getMatchesByTeamId_matchNull_exceptionThrown() {
        matchService.getMatchesByTeamId(null);
    }

    @Test
    public void getMatchesByTeamId_matchExist_matchFound() {
        List<MatchTO> actual = matchService.getMatchesByTeamId(1L);
        List<MatchTO> expected = new ArrayList<>();
        expected.add(matchTo);
        assertEquals(actual, expected);
    }

    @Test
    public void getAllMatches_matchExist_matchFound() {
        List<MatchTO> actual = matchService.getAllMatches();
        List<MatchTO> expected = new ArrayList<>();
        expected.add(matchTo);
        assertEquals(actual, expected);
    }

    private void assertDeepEquals(Match expected, Match actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getHomeTeam(), actual.getHomeTeam());
        assertEquals(expected.getVisitingTeam(), actual.getVisitingTeam());
        assertEquals(expected.getEventDate(), actual.getEventDate());
        assertEquals(expected.getGoals(), actual.getGoals());
    }

    private void assertDeepEquals(MatchTO expected, MatchTO actual) {
        assertEquals(expected.getMatchId(), actual.getMatchId());
        assertEquals(expected.getEventDate(), actual.getEventDate());
        assertEquals(expected.getHomeTeamId(), actual.getHomeTeamId());
        assertEquals(expected.getHomeTeamName(), actual.getHomeTeamName());
        assertEquals(expected.getHomeTeamScore(), actual.getHomeTeamScore());
        assertEquals(expected.getVisitingTeamId(), actual.getVisitingTeamId());
        assertEquals(expected.getVisitingTeamName(), actual.getVisitingTeamName());
        assertEquals(expected.getVisitingTeamScore(), actual.getVisitingTeamScore());
        assertEquals(expected.getWinnerTeamId(), actual.getWinnerTeamId());
    }

}
