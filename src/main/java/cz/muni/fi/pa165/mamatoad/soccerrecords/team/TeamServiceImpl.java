package cz.muni.fi.pa165.mamatoad.soccerrecords.team;

import cz.muni.fi.pa165.mamatoad.soccerrecords.goal.Goal;
import cz.muni.fi.pa165.mamatoad.soccerrecords.match.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.match.MatchDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.player.PlayerDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Maros Klimovsky
 */
@Transactional
@Service("teamService")
public class TeamServiceImpl implements TeamService{

    private PlayerDao playerDao;
    private MatchDao matchDao;
    private TeamDao teamDao;
    
    public TeamServiceImpl(){
    
    }
    
    @Autowired
    public TeamServiceImpl(PlayerDao playerDao, MatchDao matchDao, TeamDao teamDao){
        this.playerDao = playerDao;
        this.matchDao = matchDao;
        this.teamDao = teamDao;
    }
    
    @Override
    public void add(TeamTO teamTO) {
        if(teamTO == null)
            throw new IllegalArgumentException("teamTO cannot be null");
        
        teamDao.createTeam(convertToEntity(teamTO));
    }

    @Override
    public void update(TeamTO teamTO) {
        if (teamTO == null)
            throw new IllegalArgumentException("teamTO cannot be null");
        
        teamDao.updateTeam(convertToEntity(teamTO));
    }

    @Override
    public void remove(TeamTO teamTO) {
        if (teamTO == null)
            throw new IllegalArgumentException("teamTO cannot be null");
        
        teamDao.deleteTeam(convertToEntity(teamTO));
    }

    @Override
    public List<TeamTO> getAllTeams() {
        List<TeamTO> teams = new ArrayList<>();
        for (Team team: teamDao.retrieveAllTeams()) {
            teams.add(convertToTransferObject(team));
        }
        
        return teams;
    }

    @Override
    public TeamTO getTeamById(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id cannot be null");
        
        return convertToTransferObject(teamDao.retrieveTeamById(id));
    }
    
    private Team convertToEntity(TeamTO teamTo){
        Team team = new Team();
        team.setId(teamTo.getTeamId());
        team.setName(teamTo.getTeamName());
        team.setPlayers(playerDao.retrievePlayersByTeam(team));
        return team;
        
    }
    
    private TeamTO convertToTransferObject(Team team) {
      TeamTO teamTo = new TeamTO();
      teamTo.setTeamId(team.getId());
      teamTo.setTeamName(team.getName());
      
      return setDerivedAttributes(team,teamTo);
      
    }
    
    private TeamTO setDerivedAttributes(Team team,TeamTO teamTo) {
        long goalsShot = 0;
        long goalsRecieved = 0;
        long numberOfWins = 0;
        long numberOfLoses = 0;
        long numberOfTies = 0;
        long goalsShotPerMatch;
        long goalsRecievedPerMatch;
        
        List<Match> matches = matchDao.retrieveMatchesByTeam(team);
        
        for(Match m: matches){
            goalsShotPerMatch = 0;
            goalsRecievedPerMatch = 0;
            
            for (Goal g: m.getGoals()) {
                if(g.getTeam().equals(team)){
                    goalsShotPerMatch++;
                }
                else{
                    goalsRecievedPerMatch++;
                }
            }
            
            if (goalsShotPerMatch > goalsRecievedPerMatch){
                numberOfWins++;
            }
            else if (goalsShotPerMatch < goalsRecievedPerMatch) {
                numberOfLoses++;
            }
            else{
                numberOfTies++;
            }
            
            goalsShot += goalsShotPerMatch;
            goalsRecieved += goalsRecievedPerMatch;
         }
        
        teamTo.setNumberOfGoalsShot(goalsShot);
        teamTo.setNumberOfGoalsRecieved(goalsRecieved);
        teamTo.setNumberOfWins(numberOfWins);
        teamTo.setNumberOfLoses(numberOfLoses);
        teamTo.setNumberOfTies(numberOfTies);
       return teamTo;
    }
}
