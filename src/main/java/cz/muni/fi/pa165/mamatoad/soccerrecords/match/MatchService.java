/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.mamatoad.soccerrecords.match;

import cz.muni.fi.pa165.mamatoad.soccerrecords.goal.GoalTO;
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
     */
    public void add(MatchTO match);
    /**
     * Updates match with match.id, sets values from object match
     * 
     * @param match match to update
     */
    public void update(MatchTO match);
    /**
     * Removed match
     * 
     * @param match  match to delete
     */
    public void remove(MatchTO match);
    
    /**
     * Returns list of goals shot in match
     * 
     * @param match match where the goals were shot
     * @return list of all goalTO shot in this match
     */
    public List<GoalTO> getGoals(MatchTO match);
    
    /**
     * Returns list of matches with given team id
     * 
     * @param teamId if of the team
     * @return list of matchTO where played team with given id
     */
    public List<MatchTO> getMatchesByTeamId(Long teamId);
}
