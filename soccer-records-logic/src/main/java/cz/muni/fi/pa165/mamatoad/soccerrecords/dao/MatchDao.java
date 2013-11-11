package cz.muni.fi.pa165.mamatoad.soccerrecords.dao;

import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.List;
import org.joda.time.LocalDate;

/**
 * This interface provides create, retrieve, update and delete methods for the Match entity.
 * 
 * @author Matus Nemec
 */
public interface MatchDao {
    
    /**
     * Creates new record for match
     * 
     * @param match match to be created
     * @exception IllegalArgumentException when match is null
     * @exception IllegalEntityException when id is not null or other attributes are null or teams are equal
     */
    void createMatch(Match match);
    
    /**
     * Updates match with match.id, sets values from object match
     * 
     * @param match match to update
     * @exception IllegalArgumentException when match is null
     * @exception IllegalEntityException when some attributes are null or given match does not exist or teams are equal
     */
    void updateMatch(Match match);
    
    /**
     * Deletes match
     * 
     * @param match  match to delete
     * @exception IllegalArgumentException when match is null
     * @exception IllegalEntityException if given match does not exist
     */
    void deleteMatch(Match match);
    
    /**
     * Retrieves match by its id
     * 
     * @param id id of match to retrieve
     * @return match with given id or null if such match does not exist
     * @exception IllegalArgumentException when id is null
     */
    Match retrieveMatchById(Long id);
    
    /**
     * Retrieves matches played by given team
     * 
     * @param team team for which to return matches
     * @return list of matches played by given team
     * @exception IllegalArgumentException when team is null
     * @exception IllegalEntityException when team id is null or given team does not exist
     */
    List<Match> retrieveMatchesByTeam(Team team);
    
    /**
     * Retrieves matches for given date
     * 
     * @param eventDate date when match is scheduled
     * @return list of matches for given date
     * @exception IllegalArgumentException when eventDate is null
     */
    List<Match> retrieveMatchesByEventDate(LocalDate eventDate);
    
    
    /**
     * Retrieves all matches
     *  
     * @return list of all matches
     */
    List<Match> retrieveAllMatches();
}
