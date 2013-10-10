package cz.muni.fi.pa165.mamatoad.soccerrecords.team;

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
     * @param team team to be created
     * @return the created team
     */
    Team createTeam(Team team);
    
    /**
     * Updates the record with the given team
     * 
     * @param team team to be updated
     * @return the updated team
     */
    Team updateTeam(Team team);
    
    /**
     * Deletes the record with the given team
     * 
     * @param team team to be deleted
     */
    void deleteTeam(Team team);
    
    /**
     * Retrieves the team with the given id
     * 
     * @param id id of the requested team
     * @return the team with the given id
     */
    Team retrieveTeamById(Long id);
    
    /**
     * Retrieves the list of the teams with the given name
     * 
     * @param name name of the requested team
     * @return the list of the teams with the given name
     */
    List<Team> retrieveTeamsByName(String name);
    
}
