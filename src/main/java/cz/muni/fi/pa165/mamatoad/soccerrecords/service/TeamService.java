package cz.muni.fi.pa165.mamatoad.soccerrecords.service;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.TeamTO;
import java.util.List;

/**
 * Interface for managing teams
 * 
 * @author Maros Klimovsky
 */
public interface TeamService {
    
    /**
     * This method adds new team
     * @param teamTO 
     * @throws DataAccessException for errors on persistence layer
     */
    public void add(TeamTO teamTO);
    
    /**
     * This method updates given team
     * @param teamTO
     * @throws DataAccessException for errors on persistence layer
     */
    public void update(TeamTO teamTO);
    
    /**
     * This method removes given team
     * @param teamTO
     * @throws DataAccessException for errors on persistence layer
     */
    public void remove(TeamTO teamTO);
    
    /**
     * This method returns team with given id
     * @param id
     * @throws DataAccessException for errors on persistence layer
     */
    public TeamTO getTeamById(Long id);
    /**
     * This method returns all teams
     * @return List<teamDao>
     * @throws DataAccessException for errors on persistence layer
     */
    public List<TeamTO> getAllTeams();
 }
