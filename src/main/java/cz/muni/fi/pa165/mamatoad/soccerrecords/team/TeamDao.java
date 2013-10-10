package cz.muni.fi.pa165.mamatoad.soccerrecords.team;

import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.List;

/**
 * Interface providing CRUD operations for the Team entity
 * 
 * @author Tomas Livora
 */
public interface TeamDao {
    
    /**
     * Creates new record with the given team
     * 
     * @param team team to be created, not null, without id
     * @return the created team
     */
    void createTeam(Team team) throws IllegalEntityException;
    
    /**
     * Updates the record with the given team
     * 
     * @param team team to be updated, not null
     * @return the updated team
     */
    void updateTeam(Team team) throws IllegalEntityException;
    
    /**
     * Deletes the record with the given team
     * 
     * @param team team to be deleted, not null
     */
    void deleteTeam(Team team) throws IllegalEntityException;
    
    /**
     * Retrieves the team with the given id
     * 
     * @param id id of the requested team, not null
     * @return the team with the given id
     */
    Team retrieveTeamById(Long id);
    
    /**
     * Retrieves the list of the teams with the given name
     * 
     * @param name name of the requested team, not null, not empty
     * @return the list of the teams with the given name
     */
    List<Team> retrieveTeamsByName(String name);
    
}
