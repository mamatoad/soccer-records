package cz.muni.fi.pa165.mamatoad.soccerrecords.service.impl;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.GoalDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Goal;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.GoalTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.MatchDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.PlayerDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.TeamDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Acl;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Role;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.GoalService;
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
    @Acl(Role.ADMIN)
    public void add(GoalTO goal) {
        if (goal == null) {
            throw new IllegalArgumentException("goal is null");
        }
        
        goalDao.createGoal(convertToEntity(goal));
    }

    @Transactional
    @Override
    @Acl(Role.ADMIN)
    public void update(GoalTO goal) {
        if (goal == null) {
            throw new IllegalArgumentException("goal is null");
        }
        
        goalDao.updateGoal(convertToEntity(goal));
    }

    @Transactional
    @Override
    @Acl(Role.ADMIN)
    public void remove(GoalTO goal) {
        if (goal == null) {
            throw new IllegalArgumentException("goal is null");
        }
        
        goalDao.deleteGoal(convertToEntity(goal));
    }

    @Override
    public GoalTO getGoalById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        
        Goal goal = goalDao.retrieveGoalById(id);
        return convertToTransferObject(goal);
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
        Goal goal = new Goal();
        goal.setId(goalTO.getGoalId());
        goal.setMatch(matchDao.retrieveMatchById(goalTO.getMatchId()));
        goal.setPlayer(playerDao.retrievePlayerById(goalTO.getPlayerId()));
        goal.setTeam(teamDao.retrieveTeamById(goalTO.getTeamId()));
        goal.setShootingTime(goalTO.getTime());
        return goal;
    }

}
