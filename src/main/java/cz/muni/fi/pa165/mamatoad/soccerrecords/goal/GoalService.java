package cz.muni.fi.pa165.mamatoad.soccerrecords.goal;

import java.util.List;

/**
 * Interface for managing goals
 * 
 * @author Tomas Livora
 */
public interface GoalService {

    /**
     * Adds the given goal to the persistent storage
     * 
     * @param goal the goal being added
     */
    void add(GoalTO goal);
    
    /**
     * Updates the given goal in the persistent storage
     * 
     * @param goal the goal being updated
     */
    void update(GoalTO goal);
    
    /**
     * Removes the given goal from the persistent storage
     * 
     * @param goal the goal being removed
     */
    void remove(GoalTO goal);
    
    /**
     * Returns list of all goals from the match with the given id
     * 
     * @param matchId id of the match
     * @return list of all goals from the match with the given id
     */
    List<GoalTO> getGoalsByMatchId(Long matchId);
    
}