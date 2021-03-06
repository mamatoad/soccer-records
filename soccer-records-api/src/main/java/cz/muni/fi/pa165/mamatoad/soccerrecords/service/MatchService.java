package cz.muni.fi.pa165.mamatoad.soccerrecords.service;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.MatchTO;
import java.util.List;

/**
 * This interface allows to manipulate with matches.
 *
 * @author Adriana Smijakova
 */
public interface MatchService {
    
    /**
     * Adds new record for match
     * 
     * @param match match to be created
     * @exception IllegalArgumentException when match is null
     * @throws DataAccessException for errors on persistence layer
     */
    public void add(MatchTO match);
    
    /**
     * Updates match with match.id, sets values from object match
     * 
     * @param match match to update
     * @exception IllegalArgumentException when match is null
     * @throws DataAccessException for errors on persistence layer
     */
    public void update(MatchTO match);
    
    /**
     * Removed match
     * 
     * @param match  match to delete
     * @exception IllegalArgumentException when match is null
     * @throws DataAccessException for errors on persistence layer
     */
    public void remove(MatchTO match);
    
    /**
     * Returns list of matches with given team id
     * 
     * @param teamId if of the team
     * @return list of matchTO where played team with given id
     * @exception IllegalArgumentException when teamId is null
     * @throws DataAccessException for errors on persistence layer
     */
    public List<MatchTO> getMatchesByTeamId(Long teamId);
    
    /**
     * Returns all matches
     * 
     * @return list of matches
     * @throws DataAccessException for errors on persistence layer
     */
    public List<MatchTO> getAllMatches();
    
    /**
     * Returns matchTO
     * 
     * @param matchId id of match to find
     * @return MatchTO of match id
     * @throws IllegalArgumentException when id is null
     * @throws DataAccessException for errors on persistence layer
     */
    public MatchTO getMatchById(Long matchId);
}
