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
     * @exception IllegalEntityException when id is not null
     */
    void createMatch(Match match) throws IllegalEntityException;
    
    /**
     * Updates match with match.id, sets values from object match
     * 
     * @param match match to update
     */
    void updateMatch(Match match);
    
    /**
     * Deletes match
     * 
     * @param match  match to delete
     */
    void deleteMatch(Match match);
    
    /**
     * Retrieves match by its id
     * 
     * @param id id of match to retrieve
     * @return match with given id
     */
    Match retrieveMatchById(Long id);
    
    /**
     * Retrieves matches played by given team
     * 
     * @param team team for which to return matches
     * @return list of matches played by given team
     */
    List<Match> retrieveMatchesByTeam(Team team);
    
    /**
     * Retrieves matches for given date
     * 
     * @param eventDate date when match is scheduled
     * @return list of matches for given date
     */
    List<Match> retrieveMatchesByEventDate(LocalDate eventDate);
    
}
