package cz.muni.fi.pa165.mamatoad.soccerrecords.dao;

import cz.muni.fi.pa165.mamatoad.soccerrecords.models.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.models.Team;
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
     */
    Player create(Player player);
    
    /**
     * This method updates player
     * @param player to update
     * @return updated player
     */
    Player update(Player player);
    
    /**
     * This method deletes player
     * @param player to delete
     */
    void delete(Player player);
    
    /**
     * This method retrieves player by his id
     * @param id of player
     * @return player with given id
     */
    Player retrievePlayerById(Long id);
    
    /**
     * This method retrieves player by name
     * @param name of player
     * @return list of players with given name
     */
    List<Player> retrievePlayersByName(String name);
    
    /**
     * This method retrieves player by team
     * @param team of player
     * @return list of players with given team
     */
    List<Player> retrievePlayersByTeam(Team team);
    
    /**
     * This method retrieves player by activity
     * @param active - is player active?
     * @return list of players with given activity
     */
    List<Player> retrievePlayerByActivity(Boolean active);
    
    
}
