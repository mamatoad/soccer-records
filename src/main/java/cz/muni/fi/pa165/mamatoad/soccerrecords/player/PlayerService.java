package cz.muni.fi.pa165.mamatoad.soccerrecords.player;

import java.util.List;

/**
 * Interface for player management.
 * @author Matus Nemec
 */
public interface PlayerService {
    /**
     * This method adds new player.
     * @param playerTO 
     */
    public void add(PlayerTO playerTO);
    
    /**
     * This method updates given player.
     * @param playerTO 
     */
    public void update(PlayerTO playerTO);
    
    /**
     * This method deletes given player.
     * @param playerTO 
     */
    public void remove(PlayerTO playerTO);
    
    /**
     * This method returns player by id.
     * @param id
     * @return PlayerTO with given id
     */
    public PlayerTO getPlayerById(Long id);
    
    /**
     * This method returns players by team id.
     * @param id ID of team
     * @return List<PlayerTO> list of all players for given team
     */
    public List<PlayerTO> getPlayersByTeamId(Long id);
    
    /**
     * This method returns all players.
     * @return List<PlayerTO> list of all players
     */
    public List<PlayerTO> getAllPlayers();
}
