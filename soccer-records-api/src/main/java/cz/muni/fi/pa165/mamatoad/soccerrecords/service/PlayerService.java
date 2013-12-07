package cz.muni.fi.pa165.mamatoad.soccerrecords.service;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.PlayerTO;
import java.util.List;

/**
 * Interface for player management.
 * @author Matus Nemec
 */
public interface PlayerService {
    /**
     * This method adds new player.
     * @param playerTO 
     * @throws IllegalArgumentException if playerTO is null
     * @throws DataAccessException for errors on persistence layer
     */
    public void add(PlayerTO playerTO);
    
    /**
     * This method updates given player.
     * @param playerTO
     * @throws IllegalArgumentException if playerTO is null
     * @throws DataAccessException for errors on persistence layer
     */
    public void update(PlayerTO playerTO);
    
    /**
     * This method deletes given player.
     * @param playerTO 
     * @throws IllegalArgumentException if playerTO is null
     * @throws DataAccessException for errors on persistence layer
     */
    public void remove(PlayerTO playerTO);
    
    /**
     * This method returns player by id.
     * @param id
     * @return PlayerTO with given id
     * @throws IllegalArgumentException if id is null
     * @throws DataAccessException for errors on persistence layer
     */
    public PlayerTO getPlayerById(Long id);
    
    /**
     * This method returns players by team id.
     * @param id ID of team
     * @return List<PlayerTO> list of all players for given team
     * @throws IllegalArgumentException if id is null
     * @throws DataAccessException for errors on persistence layer
     */
    public List<PlayerTO> getPlayersByTeamId(Long id);
    
    /**
     * This method returns all players.
     * @return List<PlayerTO> list of all players
     * @throws DataAccessException for errors on persistence layer
     */
    public List<PlayerTO> getAllPlayers();
    
    /**
     * This method returns players by name.
     * @param name player's name
     * @return List<PlayerTO> list of all players for given name
     * @throws IllegalArgumentException if name is null
     * @throws DataAccessException for errors on persistence layer
     */
    public List<PlayerTO> getPlayersByName(String name);
    
    /**
     * This method returns players, if part of their name matches the search term.
     * @param searchTerm search term
     * @return List<PlayerTO> list of all players for given search term
     * @throws IllegalArgumentException if search term is null
     * @throws DataAccessException for errors on persistence layer
     */
    public List<PlayerTO> getFilteredPlayers(String searchTerm);
}
