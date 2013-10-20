package cz.muni.fi.pa165.mamatoad.soccerrecords.goal;

import java.util.List;

/**
 * Interface for managing goals
 * 
 * @author Tomas Livora
 */
public interface GoalManager {

    /**
     * Adds the given goal to the persistent storage
     * 
     * @param goalDetail 
     */
    void add(GoalDetail goalDetail);
    
    /**
     * Updates the given goal in the persistent storage
     * 
     * @param goalDetail 
     */
    void update(GoalDetail goalDetail);
    
    /**
     * Removes the given goal from the persistent storage
     * 
     * @param goalDetail 
     */
    void remove(GoalDetail goalDetail);
    
    /**
     * Returns list of all goals from the match with the given id
     * 
     * @param matchId id of the match
     * @return list of all goals from the match with the given id
     */
    List<GoalDetail> getGoalsByMatchId(Long matchId);
    
}
