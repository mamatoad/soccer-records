package cz.muni.fi.pa165.mamatoad.soccerrecords.match;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.MatchDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Goal;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Team;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Maros Klimovsky
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springConfigTest.xml"})
@Transactional
public class MatchDaoTest {
    
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MatchDao matchDao;
    
    private Match match;
    private Player player;
    private Goal goal;
    private Team team;
    private Team otherTeam;
    private Team thirdTeam;

    @Before
    public void initialize() throws SQLException {
        // create Player
        player = new Player();
        player.setName("John Kopachka");
        player.setActive(true);
        em.persist(player);

        // create Teams
        team = new Team();
        team.setName("FC Ballerinas");
        team.setPlayers(new ArrayList<Player>());
        em.persist(team);

        otherTeam = new Team();
        otherTeam.setName("FC Skulkers");
        otherTeam.setPlayers(new ArrayList<Player>());
        em.persist(otherTeam);

        thirdTeam = new Team();
        thirdTeam.setName("FC Noobs");
        thirdTeam.setPlayers(new ArrayList<Player>());
        em.persist(thirdTeam);
        
        // create Goal
        goal = new Goal();
        em.persist(goal);
        
        // create Match
        match = new Match();
        match.setHomeTeam(team);
        match.setVisitingTeam(otherTeam);
        match.setEventDate(LocalDate.now());

    }

    @Test
    public void test_create_validMatch_createsMatch() {
        matchDao.createMatch(match);

        Long id = match.getId();
        Assert.assertNotNull("Id was not assigned.", id);

    }

    @Test(expected = DataAccessException.class)
    public void test_create_nullMatch_ThrowsIllegalArgumentException() {
        match = null;
        matchDao.createMatch(match);

    }

    @Test(expected = DataAccessException.class)
    public void test_create_matchWithId_Throws() {
        match.setId(Long.MIN_VALUE);
        matchDao.createMatch(match);
    }

    @Test(expected = DataAccessException.class)
    public void test_create_nullMatchHomeTeam_ThrowsIllegalEntityException() {
        match.setHomeTeam(null);
        matchDao.createMatch(match);
    }

    @Test(expected = DataAccessException.class)
    public void test_create_nullMatchVisitingTeam_ThrowsIllegalEntityException() {
        match.setVisitingTeam(null);
        matchDao.createMatch(match);
    }

    @Test(expected = DataAccessException.class)
    public void test_create_nullMatchEventDate_ThrowsIllegalEntityException() {
        match.setEventDate(null);
        matchDao.createMatch(match);
    }

    @Test(expected = DataAccessException.class)
    public void test_create_matchHomeTeamNullId_ThrowsIllegalEntityException() {
        Team testTeam = new Team();
        match.setHomeTeam(testTeam);
        matchDao.createMatch(match);
    }

    @Test(expected = DataAccessException.class)
    public void test_create_matchVisitingTeamNullId_ThrowsIllegalEntityException() {
        Team testTeam = new Team();
        match.setVisitingTeam(testTeam);
        matchDao.createMatch(match);
    }

    @Test(expected = DataAccessException.class)
    public void test_create_matchTeamPlayingAgainstItself_ThrowsIllegalEntityException() {
        match.setVisitingTeam(match.getHomeTeam());
        matchDao.createMatch(match);
    }

    @Test(expected = DataAccessException.class)
    public void test_update_nullMatch_ThrowsIllegalArgumentException() {
        match = null;
        matchDao.updateMatch(match);
    }

    @Test(expected = DataAccessException.class)
    public void test_update_matchNullId_ThrowsIllegalEntityException() {
        matchDao.updateMatch(match);
    }

    @Test(expected = DataAccessException.class)
    public void test_update_nullMatchHomeTeam_ThrowsIllegalEntityException() {
        match.setHomeTeam(null);
        matchDao.updateMatch(match);
    }

    @Test(expected = DataAccessException.class)
    public void test_update_nullMatchVisitingTeam_ThrowsIllegalEntityException() {
        match.setVisitingTeam(null);
        matchDao.updateMatch(match);
    }

    @Test(expected = DataAccessException.class)
    public void test_update_nullMatchEventDate_ThrowsIllegalEntityException() {
        match.setEventDate(null);
        matchDao.updateMatch(match);
    }

    @Test(expected = DataAccessException.class)
    public void test_update_matchHomeTeamNullId_ThrowsIllegalEntityException() {
        Team testTeam = new Team();
        match.setHomeTeam(testTeam);
        matchDao.updateMatch(match);
    }

    @Test(expected = DataAccessException.class)
    public void test_update_matchVisitingTeamNullId_ThrowsIllegalEntityException() {
        Team testTeam = new Team();
        match.setVisitingTeam(testTeam);
        matchDao.updateMatch(match);
    }

    @Test(expected = DataAccessException.class)
    public void test_update_matchTeamPlayingAgainstItself_ThrowsIllegalEntityException() {
        match.setVisitingTeam(match.getHomeTeam());
        matchDao.updateMatch(match);
    }

    @Test
    public void test_update_validHomeTeam_UpdatesHomeTeam() {

        matchDao.createMatch(match);

        match.setHomeTeam(thirdTeam);
        matchDao.updateMatch(match);

        Assert.assertEquals("home team didn't update correctly", thirdTeam, 
                em.find(Match.class, match.getId()).getHomeTeam());
    }

    @Test
    public void test_update_validVisitingTeam_UpdatesVisitingTeam() {

        matchDao.createMatch(match);

        match.setVisitingTeam(thirdTeam);
        matchDao.updateMatch(match);

        Assert.assertEquals("visiting team didn't update correctly", thirdTeam, 
                em.find(Match.class, match.getId()).getVisitingTeam());
    }

//    @Test
//    public void test_update_validGoals_UpdatesGoals() {
//
//        matchDao.createMatch(match);
//        Long id = match.getId();
//        Goal g = em.find(Goal.class, goal.getId());
//        g.setMatch(match);
//        em.merge(g);
//
//        Assert.assertTrue("goals didn't update correctly", em.find(Match.class, id).getGoals().contains(goal));
//    }

    @Test
    public void test_update_validEventDate_UpdatesEventDate() {

        matchDao.createMatch(match);
        LocalDate date = LocalDate.parse("2005-11-12");
        match.setEventDate(date);
        matchDao.updateMatch(match);

        Assert.assertEquals("eventDate didn't update correctly", date, 
                em.find(Match.class, match.getId()).getEventDate());
    }

    @Test(expected = DataAccessException.class)
    public void test_delete_nullMatch_throwsIllegalArgumentException() {
        match = null;
        matchDao.deleteMatch(match);
    }

    @Test(expected = DataAccessException.class)
    public void test_delete_matchNullId_throwsIllegalEntityException() {
        match.setId(null);
        matchDao.deleteMatch(match);
    }

    @Test
    public void test_delete_validMatch_deletesMatch() {
        matchDao.createMatch(match);
        Long id = match.getId();
        matchDao.deleteMatch(match);

        Assert.assertNull("match was't deleted", em.find(Match.class, id));
    }

    @Test(expected = DataAccessException.class)
    public void test_retrieve_nullId_ThrowsIllegalArgumentException() {
        Long id = null;
        matchDao.retrieveMatchById(id);

    }

    @Test(expected = DataAccessException.class)
    public void test_retrieve_nullTeam_ThrowsIllegalArgumentException() {
        matchDao.retrieveMatchesByTeam(null);

    }

    @Test(expected = DataAccessException.class)
    public void test_retrieve_nullEventDate_ThrowsIllegalArgumentException() {
        LocalDate date = null;
        matchDao.retrieveMatchesByEventDate(date);

    }

    @Test
    public void test_retrieve_validId_retrievesMatch() {
        matchDao.createMatch(match);
        Long id = match.getId();
        Assert.assertEquals("match wasn't retrieved", match, matchDao.retrieveMatchById(id));

    }

    @Test
    public void test_retrieve_validTeam_retrievesMatch() {
        matchDao.createMatch(match);
        Team homeTeam = match.getHomeTeam();
        Team visitingTeam = match.getVisitingTeam();
        Long id = match.getId();

        Assert.assertTrue("match wasn't retrieved", matchDao.retrieveMatchesByTeam(homeTeam).contains(match));
        Assert.assertTrue("match wasn't retrieved", matchDao.retrieveMatchesByTeam(visitingTeam).contains(match));

    }

    @Test
    public void test_retrieve_validEventDate_retrievesMatch() {
        matchDao.createMatch(match);
        LocalDate date = match.getEventDate();
        matchDao.retrieveMatchesByEventDate(date);
        Assert.assertTrue("match wasn't retrieved", matchDao.retrieveMatchesByEventDate(date).contains(match));
    }
}
