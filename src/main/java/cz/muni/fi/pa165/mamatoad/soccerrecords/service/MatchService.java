/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
     */
    public void add(MatchTO match);
    
    /**
     * Updates match with match.id, sets values from object match
     * 
     * @param match match to update
     * @exception IllegalArgumentException when match is null
     */
    public void update(MatchTO match);
    
    /**
     * Removed match
     * 
     * @param match  match to delete
     * @exception IllegalArgumentException when match is null
     */
    public void remove(MatchTO match);
    
    /**
     * Returns list of matches with given team id
     * 
     * @param teamId if of the team
     * @return list of matchTO where played team with given id
     * @exception IllegalArgumentException when teamId is null
     */
    public List<MatchTO> getMatchesByTeamId(Long teamId);
    
    /**
     * Returns all matches
     * 
     * @return list of matches
     */
    public List<MatchTO> getAllMatches();
}
