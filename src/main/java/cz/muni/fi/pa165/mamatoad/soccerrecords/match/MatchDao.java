package cz.muni.fi.pa165.mamatoad.soccerrecords.match;

import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author matus-nemec
 */
public interface MatchDao {
    
    Match createMatch(Match match);
    
    Match updateMatch(Match match);
    
    void deleteMatch(Match match);
    
    Match retrieveMatchById(Long id);
    
    List<Match> retrieveMatchesByTeam(Team team);
    
    List<Match> retireveMatchesByDateTime(DateTime dateTime);
    
}
