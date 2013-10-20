package cz.muni.fi.pa165.mamatoad.soccerrecords.goal;

import cz.muni.fi.pa165.mamatoad.soccerrecords.match.MatchDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.player.PlayerDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.team.TeamDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Tomas Livora
 */
@Named("goalManager")
public class GoalManagerImpl implements GoalManager {
    
    private GoalDao goalDao;
    private MatchDao matchDao;
    private PlayerDao playerDao;
    private TeamDao teamDao;

    @Inject
    public GoalManagerImpl(GoalDao goalDao, MatchDao matchDao, PlayerDao playerDao, TeamDao teamDao) {
        this.goalDao = goalDao;
        this.matchDao = matchDao;
        this.playerDao = playerDao;
        this.teamDao = teamDao;
    }

    @Override
    public void add(GoalDetail goal) {        
        try {
            goalDao.createGoal(goalDetailToGoal(goal));
        } catch (IllegalArgumentException | IllegalEntityException | DataAccessException ex) {
            Logger.getLogger(GoalManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(GoalDetail goal) {
        try {
            goalDao.updateGoal(goalDetailToGoal(goal));
        } catch (IllegalArgumentException | IllegalEntityException | DataAccessException ex) {
            Logger.getLogger(GoalManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void remove(GoalDetail goal) {
        try {
            goalDao.deleteGoal(goalDetailToGoal(goal));
        } catch (IllegalArgumentException | IllegalEntityException | DataAccessException ex) {
            Logger.getLogger(GoalManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static GoalDetail goalToGoalDetail(Goal goal) {
        return new GoalDetail(goal.getId(), goal.getMatch().getId(), goal.getPlayer().getId(),
                goal.getPlayer().getName(), goal.getTeam().getId(), goal.getTeam().getName(), goal.getShootingTime());
    }
    
    private Goal goalDetailToGoal(GoalDetail goalDetail) {
        Goal goal = new Goal();
        goal.setId(goalDetail.getGoalId());
        goal.setMatch(matchDao.retrieveMatchById(goalDetail.getMatchId()));
        goal.setPlayer(playerDao.retrievePlayerById(goalDetail.getPlayerId()));
        goal.setTeam(teamDao.retrieveTeamById(goalDetail.getTeamId()));
        goal.setShootingTime(goalDetail.getTime());
        return goal;
    }

}
