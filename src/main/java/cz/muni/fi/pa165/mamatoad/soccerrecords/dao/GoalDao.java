package cz.muni.fi.pa165.mamatoad.soccerrecords.dao;

import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Goal;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.List;

/**
 * This interface allows to manipulate with goals.
 *
 * @author Adriana Smijakova
 */
public interface GoalDao {
    
    /**
     * Stores new goal into database.
     * 
     * @param goal to be created
     * @exception IllegalArgumentException when goal is null
     * @exception IllegalEntityException when id of goal is not null or other attributes are null
     */
    void createGoal(Goal goal);
    
    /**
     * Updates gived goal in database.
     * 
     * @param goal to be updated
     * @exception IllegalArgumentException when goal is null
     * @exception IllegalEntityException when some goal attribute is null
     */
    void updateGoal(Goal goal);
    
    /**
     * Deletes given goal in database.
     * 
     * @param goal to be deleted 
     * @exception  IllegalArgumentException when goal is null
     * @exception IllegalEntityException when goal has null id or does not exist in the database
     */
    void deleteGoal(Goal goal);
    
    /**
     * Retrieves goal with given id.
     * 
     * @param id primary key of requested goal
     * @return goal with given id or null if such goal does not exist
     * @exception IllegalArgumentException when id is null
     */
    Goal retrieveGoalById(Long id);
    
    /**
     * Retrieves goals in given match.
     * 
     * @param match where were goals shot
     * @return list of goals with given match
     * @exception IllegalArgumentException when match is null
     * @exception IllegalEntityException when match id is null
     */
    List<Goal> retrieveGoalsByMatch(Match match);
    
    /**
     * Retrieves goals in given match shot by given team.
     * 
     * @param match where were goals shot
     * @param team which shot the goals 
     * @return list of goals with given match and team
     * @exception  IllegalArgumentException when match or team is null
     * @exception IllegalEntityException when match or team id is null
     */
    List<Goal> retrieveGoalsByMatchAndTeam(Match match, Team team);
    
    /**
     * Retrieves goals shot by given player
     * 
     * @param player who shot the goals
     * @return list of goals witch given player
     * @exception IllegalArgumentException when player is null
     * @exception IllegalEntityException when player id is null
     */
    List<Goal> retrieveGoalsByPlayer(Player player);
    
    /**
     * Retrieves goals shot in given match by given player
     * 
     * @param match where were goals shot
     * @param player who shot the goals
     * @return list of goals witch goven match and player
     * @exception IllegalArgumentException when match or player is null
     * @exception IllegalEntityException when match or player id is null
     */
    List<Goal> retrieveGoalsByMatchAndPlayer(Match match, Player player);
    
}
