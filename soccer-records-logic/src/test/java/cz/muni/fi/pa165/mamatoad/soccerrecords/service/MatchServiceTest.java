package cz.muni.fi.pa165.mamatoad.soccerrecords.service;

import cz.muni.fi.pa165.mamatoad.soccerrecords.service.impl.MatchServiceImpl;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.MatchTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.MatchService;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.MatchDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.GoalDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.TeamDao;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Tests for Match service
 *
 * @author Tomas Livora
 */
@RunWith(MockitoJUnitRunner.class)
public class MatchServiceTest {
    @Mock
    private GoalDao goalDao;
    @Mock
    private MatchDao matchDao;
    @Mock
    private TeamDao teamDao;
    @Autowired
    @InjectMocks
    private MatchService matchService = new MatchServiceImpl();

    private Match match;
    private MatchTO matchTo;

    @Before
    public void setUp() {
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

        stub(matchDao.retrieveMatchById(matchId)).toReturn(match);


        stub(teamDao.retrieveTeamById(homeTeamId)).toReturn(homeTeam);

        stub(teamDao.retrieveTeamById(visitingTeamId)).toReturn(visitingTeam);

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
    public void test_add_matchNull_exceptionThrown() {
        matchService.add(null);
    }

    @Test
    public void test_add_matchValid_matchAdded() {
        match.setId(null);
        matchTo.setMatchId(null);
        matchService.add(matchTo);
        ArgumentCaptor<Match> argument = ArgumentCaptor.forClass(Match.class);
        Mockito.verify(matchDao).createMatch(argument.capture());
        assertDeepEquals(match, argument.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_update_matchNull_exceptionThrown() {
        matchService.update(null);
    }

    @Test
    public void test_update_matchValid_matchUpdated() {
        matchService.update(matchTo);
        ArgumentCaptor<Match> argument = ArgumentCaptor.forClass(Match.class);
        Mockito.verify(matchDao).updateMatch(argument.capture());
        assertDeepEquals(match, argument.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_remove_matchNull_exceptionThrown() {
        matchService.remove(null);
    }

    @Test
    public void test_remove_matchValid_matchRemoved() {
        matchService.remove(matchTo);
        ArgumentCaptor<Match> argument = ArgumentCaptor.forClass(Match.class);
        Mockito.verify(matchDao).deleteMatch(argument.capture());
        assertDeepEquals(match, argument.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_getMatchesByTeamId_matchNull_exceptionThrown() {
        matchService.getMatchesByTeamId(null);
    }

    @Test
    public void test_getMatchesByTeamId_matchExist_matchFound() {
        List<MatchTO> actual = matchService.getMatchesByTeamId(1L);
        List<MatchTO> expected = new ArrayList<>();
        expected.add(matchTo);
        assertEquals(actual, expected);
    }

    @Test
    public void test_getAllMatches_matchExist_matchFound() {
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
