/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.mamatoad.soccerrecords.match;

import cz.muni.fi.pa165.mamatoad.soccerrecords.goal.GoalDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.team.TeamDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Adriana Smijakova
 */

@Service
public class MatchServiceImpl implements MatchService{

    private final GoalDao goalDao;
    private final MatchDao matchDao;
    private final TeamDao teamDao;

    @Autowired
    public MatchServiceImpl(GoalDao goalDao, MatchDao matchDao, TeamDao teamDao) {
        if(goalDao==null || matchDao==null ||teamDao==null){
            throw new IllegalArgumentException("Dao objects cannot be null");
        }
        this.goalDao = goalDao;
        this.matchDao = matchDao;
        this.teamDao = teamDao;
    }
    
    @Transactional
    @Override
    public void add(MatchTO match) {
        if(match == null){
            throw new IllegalArgumentException("Match is null");
        }
        matchDao.createMatch(toEntity(match));
    }
    
    @Transactional
    @Override
    public void update(MatchTO match) {
        if(match == null){
            throw new IllegalArgumentException("Match is null");
        }
        matchDao.updateMatch(toEntity(match));
    }
    
    @Transactional
    @Override
    public void remove(MatchTO match) {
        if(match == null){
            throw new IllegalArgumentException("Match is null");
        }
        matchDao.deleteMatch(toEntity(match));
    }
    
    @Transactional
    @Override
    public List<MatchTO> getMatchesByTeamId(Long teamId) {
        if(teamId == null){
            throw new IllegalArgumentException("teamId is null");
        }
        List<MatchTO> matchesTO = new ArrayList<>();
        List<Match> matches = matchDao.retrieveMatchesByTeam(teamDao.retrieveTeamById(teamId));
        for(Match m : matches){
            matchesTO.add(toTO(m));
        }
        return matchesTO;
    }
    
    private Match toEntity(MatchTO matchTO){
        if(matchTO.getMatchId() != null){
            return matchDao.retrieveMatchById(matchTO.getMatchId());
        }
        
        Match match = new Match();
        
        match.setId(matchTO.getMatchId());
        match.setEventDate(matchTO.getEventDate());
        match.setHomeTeam(teamDao.retrieveTeamById(matchTO.getHomeTeamId()));
        match.setVisitingTeam(teamDao.retrieveTeamById(matchTO.getVisitingTeamId()));
        
        return match;
    }
    
    private MatchTO toTO(Match match){
        Team homeTeam = match.getHomeTeam();
        Team visitingTeam = match.getVisitingTeam();
        
        int homeTeamScore = goalDao.retrieveGoalsByMatchAndTeam(match, homeTeam).size();
        int visitingTeamScore = goalDao.retrieveGoalsByMatchAndTeam(match, visitingTeam).size();
        
        boolean drawGame = (homeTeamScore==visitingTeamScore);
        
        Long winnerTeam = null;
        
        if(homeTeamScore>visitingTeamScore){
            winnerTeam = homeTeam.getId();
        }
        else if(!drawGame){
            winnerTeam = visitingTeam.getId();
        }
        
        return new MatchTO(match.getId(), homeTeam.getId(), homeTeam.getName(),  homeTeamScore,  
                visitingTeam.getId(), visitingTeam.getName(), visitingTeamScore, match.getEventDate(), 
                winnerTeam
                );
    }

}
