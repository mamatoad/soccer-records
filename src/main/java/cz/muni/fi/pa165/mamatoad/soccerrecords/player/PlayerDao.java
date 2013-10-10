package cz.muni.fi.pa165.mamatoad.soccerrecords.player;

import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.List;

/**
 * This interface provides create, retrieve, update and delete methods for work with Player entity.
 * @author Maros Klimovsky
 */
public interface PlayerDao {
    
    /**
     * This method creates new Player
     * @param player to create
     * @return created player
     * @throws IllegalArgumentException if player is null
     * @throws IllegalEntityException if player.id is already set or player.name is not set
     *                                or team is set but doesn't exist
     */
    void createPlayer(Player player) throws IllegalEntityException, IllegalArgumentException;
    
    /**
     * This method updates player
     * @param player to update
     * @return updated player
     * @throws IllegalArgumentException if player is null
     * @throws IllegalEntityException if player.id or player.name is not set
     *                                or team is set but doesn't exist
     */
    void updatePlayer(Player player) throws IllegalEntityException, IllegalArgumentException;
    
    /**
     * This method deletes player
     * @param player to delete
     * @throws IllegalArgumentException if player is null
     * @throws IllegalEntityException if player.id is not set
     */
    void deletePlayer(Player player) throws IllegalEntityException, IllegalArgumentException;
    
    /**
     * This method retrieves player by his id
     * @param id of player
     * @return player with given id or null if not found
     * @throws IllegalArgumentException if id is null
     */
    Player retrievePlayerById(Long id) throws IllegalArgumentException;
    
    /**
     * This method retrieves player by name
     * @param name of player
     * @return list of players with given name
     * @throws IllegalArgumentException if name is not set
     */
    List<Player> retrievePlayersByName(String name) throws IllegalArgumentException;
    
    /**
     * This method retrieves player by team
     * @param team of player
     * @return list of players with given team
     * @throws IllegalEntityException if team is null
     * @throws IllegalEntityException id team.id is null or if team doesn't exist
     */
    List<Player> retrievePlayersByTeam(Team team) throws IllegalEntityException, IllegalArgumentException;
    
    /**
     * This method retrieves player by activity
     * @param active - is player active?
     * @return list of players with given activity
     */
    List<Player> retrievePlayersByActivity(boolean active);
    
    
}
