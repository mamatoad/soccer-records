package cz.muni.fi.pa165.mamatoad.soccerrecords.goal;

import cz.muni.fi.pa165.mamatoad.soccerrecords.match.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.match.MatchDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.player.PlayerDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.team.TeamDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tomas Livora
 */

@Service("goalService")
public class GoalServiceImpl implements GoalService {
    @Autowired
    private GoalDao goalDao;
    @Autowired
    private MatchDao matchDao;
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private TeamDao teamDao;

    @Transactional
    @Override
    public void add(GoalTO goal) {
        if (goal == null) {
            throw new IllegalArgumentException("goal is null");
        }
        
        goalDao.createGoal(convertToEntity(goal));
    }

    @Transactional
    @Override
    public void update(GoalTO goal) {
        if (goal == null) {
            throw new IllegalArgumentException("goal is null");
        }
        
        goalDao.updateGoal(convertToEntity(goal));
    }

    @Transactional
    @Override
    public void remove(GoalTO goal) {
        if (goal == null) {
            throw new IllegalArgumentException("goal is null");
        }
        
        goalDao.deleteGoal(convertToEntity(goal));
    }

    @Transactional
    @Override
    public List<GoalTO> getGoalsByMatchId(Long matchId) {
        if (matchId == null) {
            throw new IllegalArgumentException("matchId is null");
        }
        
        List<GoalTO> goals = new ArrayList<>();
        Match match = matchDao.retrieveMatchById(matchId);
        for (Goal goal : goalDao.retrieveGoalsByMatch(match)) {
            goals.add(convertToTransferObject(goal));
        }
        return goals;
    }
    
    private static GoalTO convertToTransferObject(Goal goal) {
        return new GoalTO(goal.getId(), goal.getMatch().getId(), goal.getTeam().getId(), goal.getTeam().getName(), 
                goal.getPlayer().getId(), goal.getPlayer().getName(), goal.getShootingTime());
    }
    
    private Goal convertToEntity(GoalTO goalTO) {
        if (goalTO.getGoalId() != null) {
            return goalDao.retrieveGoalById(goalTO.getGoalId());
        }
        
        Goal goal = new Goal();
        goal.setId(goalTO.getGoalId());
        goal.setMatch(matchDao.retrieveMatchById(goalTO.getMatchId()));
        goal.setPlayer(playerDao.retrievePlayerById(goalTO.getPlayerId()));
        goal.setTeam(teamDao.retrieveTeamById(goalTO.getTeamId()));
        goal.setShootingTime(goalTO.getTime());
        return goal;
    }

}
