package cz.muni.fi.pa165.mamatoad.soccerrecords.goal;

import cz.muni.fi.pa165.mamatoad.soccerrecords.match.Match;
import cz.muni.fi.pa165.mamatoad.soccerrecords.player.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import java.util.List;

/**
 *
 * @author adriana-smijakova
 */
public interface GoalDao {
    
    Goal createGoal(Goal goal);
    
    Goal updateGoal(Goal goal);
    
    void deleteGoal(Goal goal);
    
    Goal retrieveGoalById(Long id);
    
    List<Goal> retrieveGoalsByMatch(Match match);
    
    List<Goal> retrieveGoalsByMatchAndTeam(Match match, Team team);
    
    List<Goal> retrieveGoalsByPlayer(Player player);
    
    List<Goal> retrieveGoalsByMatchAndPlayer(Match match, Player player);
    
}
