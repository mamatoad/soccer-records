package cz.muni.fi.pa165.mamatoad.soccerrecords.goal;

import cz.muni.fi.pa165.mamatoad.soccerrecords.match.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.match.MatchDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.player.PlayerDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.team.TeamDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Tomas Livora
 */
@Named("goalService")
public class GoalServiceImpl implements GoalService {
    
    private final GoalDao goalDao;
    private final MatchDao matchDao;
    private final PlayerDao playerDao;
    private final TeamDao teamDao;

    @Inject
    public GoalServiceImpl(GoalDao goalDao, MatchDao matchDao, PlayerDao playerDao, TeamDao teamDao) {
        this.goalDao = goalDao;
        this.matchDao = matchDao;
        this.playerDao = playerDao;
        this.teamDao = teamDao;
    }

    @Override
    public void add(GoalTO goal) {        
        try {
            goalDao.createGoal(convertToEntity(goal));
        } catch (IllegalArgumentException | IllegalEntityException | DataAccessException ex) {
            Logger.getLogger(GoalServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(GoalTO goal) {
        try {
            goalDao.updateGoal(convertToEntity(goal));
        } catch (IllegalArgumentException | IllegalEntityException | DataAccessException ex) {
            Logger.getLogger(GoalServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void remove(GoalTO goal) {
        try {
            goalDao.deleteGoal(convertToEntity(goal));
        } catch (IllegalArgumentException | IllegalEntityException | DataAccessException ex) {
            Logger.getLogger(GoalServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<GoalTO> getGoalsByMatchId(Long matchId) {
        try {
            List<GoalTO> goals = new ArrayList<>();
            Match match = matchDao.retrieveMatchById(matchId);
            for (Goal goal : goalDao.retrieveGoalsByMatch(match)) {
                goals.add(convertToTransferObject(goal));
            }
            return goals;
        } catch (IllegalArgumentException | IllegalEntityException | DataAccessException ex) {
            Logger.getLogger(GoalServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private static GoalTO convertToTransferObject(Goal goal) {
        return new GoalTO(goal.getId(), goal.getMatch().getId(), goal.getPlayer().getId(),
                goal.getPlayer().getName(), goal.getTeam().getId(), goal.getTeam().getName(), goal.getShootingTime());
    }
    
    private Goal convertToEntity(GoalTO goalTO) {
        Goal goal = new Goal();
        goal.setId(goalTO.getGoalId());
        goal.setMatch(matchDao.retrieveMatchById(goalTO.getMatchId()));
        goal.setPlayer(playerDao.retrievePlayerById(goalTO.getPlayerId()));
        goal.setTeam(teamDao.retrieveTeamById(goalTO.getTeamId()));
        goal.setShootingTime(goalTO.getTime());
        return goal;
    }

}
