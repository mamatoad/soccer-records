package cz.muni.fi.pa165.mamatoad.soccerrecords.player;

import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import java.util.List;

/**
 *
 * @author maros-klimovsky
 */
public interface PlayerDao {
    
    Player createPlayer(Player player);
    
    Player updatePlayer(Player player);
    
    void deletePlayer(Player player);
    
    Player retrievePlayerById(Long id);
    
    List<Player> retrievePlayersByName(String name);
    
    List<Player> retrievePlayersByTeam(Team team);
    
    List<Player> retrievePlayersByActivity(Boolean active);
    
}
