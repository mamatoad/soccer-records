package cz.muni.fi.pa165.mamatoad.soccerrecords.team;

import java.util.List;

/**
 *
 * @author livthomas
 */
public interface TeamDao {
    
    Team createTeam(Team team);
    
    Team updateTeam(Team team);
    
    void deleteTeam(Team team);
    
    Team retrieveTeamById(Long id);
    
    List<Team> retrieveTeamsByName(String name);
    
}
