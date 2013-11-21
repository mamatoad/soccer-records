package cz.muni.fi.pa165.mamatoad.soccerrecords.service.impl;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.MatchDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.GoalDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.MatchTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.TeamDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.MatchService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Adriana Smijakova
 */

@Service("matchService")
public class MatchServiceImpl implements MatchService{
    @Autowired
    private GoalDao goalDao;
    @Autowired
    private MatchDao matchDao;
    @Autowired
    private TeamDao teamDao;
    
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
    
    @Transactional
    @Override
    public List<MatchTO> getAllMatches() {
        List<Match> matches = matchDao.retrieveAllMatches();
        List<MatchTO> matchesTO = new ArrayList<>();
        for(Match m:matches){
          matchesTO.add(toTO(m));
        }
        return matchesTO;
    }
    
    
    @Transactional
    @Override
    public MatchTO getMatchById(Long matchId) {
        if(matchId == null){
            throw new IllegalArgumentException("MatchId is null");
        }
        return toTO(matchDao.retrieveMatchById(matchId));
    }

    
    private Match toEntity(MatchTO matchTO){
               
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
