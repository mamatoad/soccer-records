package cz.muni.fi.pa165.mamatoad.soccerrecords.match;

import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
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
     * @exception IllegalEntityException when id is not null or other attributes are null
     */
    void createMatch(Match match) throws IllegalArgumentException, IllegalEntityException;
    
    /**
     * Updates match with match.id, sets values from object match
     * 
     * @param match match to update
     * @exception IllegalArgumentException when match is null
     * @exception IllegalEntityException when some attributes are null or given match does not exist
     */
    void updateMatch(Match match) throws IllegalArgumentException, IllegalEntityException;
    
    /**
     * Deletes match
     * 
     * @param match  match to delete
     * @exception IllegalArgumentException when match is null
     * @exception IllegalEntityException if given match does not exist
     */
    void deleteMatch(Match match) throws IllegalArgumentException, IllegalEntityException;
    
    /**
     * Retrieves match by its id
     * 
     * @param id id of match to retrieve
     * @return match with given id or null if such match does not exist
     * @exception IllegalArgumentException when id is null
     */
    Match retrieveMatchById(Long id) throws IllegalArgumentException;
    
    /**
     * Retrieves matches played by given team
     * 
     * @param team team for which to return matches
     * @return list of matches played by given team
     * @exception IllegalArgumentException when team is null
     * @exception IllegalEntityException when team id is null or given team does not exist
     */
    List<Match> retrieveMatchesByTeam(Team team) throws IllegalArgumentException, IllegalEntityException;
    
    /**
     * Retrieves matches for given date
     * 
     * @param eventDate date when match is scheduled
     * @return list of matches for given date
     * @exception IllegalArgumentException when eventDate is null
     */
    List<Match> retrieveMatchesByEventDate(LocalDate eventDate) throws IllegalArgumentException;
}
