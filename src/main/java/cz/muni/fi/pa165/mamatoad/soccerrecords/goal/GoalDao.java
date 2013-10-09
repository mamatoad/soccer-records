package cz.muni.fi.pa165.mamatoad.soccerrecords.goal;

import cz.muni.fi.pa165.mamatoad.soccerrecords.match.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.player.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
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
     * @return created goal
     * @exception IllegalArgumentException when goal is null
     */
    Goal createGoal(Goal goal)throws IllegalArgumentException;
    
    /**
     * Updates gived goal in database.
     * 
     * @param goal to be updated
     * @return updated goal
     * @exception IllegalArgumentException when goal is null
     */
    Goal updateGoal(Goal goal) throws IllegalArgumentException;
    
    /**
     * Deletes given goal in database.
     * 
     * @param goal to be deleted 
     * @exception  IllegalArgumentException when goal is null
     */
    void deleteGoal(Goal goal) throws IllegalArgumentException;
    
    /**
     * Retrieves goal with given id.
     * 
     * @param id primary key of requested goal
     * @return goal with given id or null if such goal does not exist
     * @exception IllegalArgumentException when id is null
     */
    Goal retrieveGoalById(Long id) throws IllegalArgumentException;
    
    /**
     * Retrieves goals in given match.
     * 
     * @param match where were goals shot
     * @return list of goals with given match
     * @exception IllegalArgumentException when match is null
     */
    List<Goal> retrieveGoalsByMatch(Match match) throws IllegalArgumentException;
    
    /**
     * Retrieves goals in given match shot by given team.
     * 
     * @param match where were goals shot
     * @param team which shot the goals 
     * @return list of goals with given match and team
     * @exception  IllegalArgumentException when match or team is null
     */
    List<Goal> retrieveGoalsByMatchAndTeam(Match match, Team team) throws IllegalArgumentException;
    
    /**
     * Retrieves goals shot by given player
     * 
     * @param player who shot the goals
     * @return list of goals witch given player
     * @exception IllegalArgumentException when player is null
     */
    List<Goal> retrieveGoalsByPlayer(Player player) throws IllegalArgumentException;
    
    /**
     * Retrieves goals shot in given match by given player
     * 
     * @param match where were goals shot
     * @param player who shot the goals
     * @return list of goals witch goven match and player
     * @exception IllegalArgumentException when match or player is null
     */
    List<Goal> retrieveGoalsByMatchAndPlayer(Match match, Player player) throws IllegalArgumentException;
    
}
