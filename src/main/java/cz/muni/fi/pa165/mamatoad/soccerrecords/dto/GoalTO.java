package cz.muni.fi.pa165.mamatoad.soccerrecords.dto;

import java.util.Objects;
import org.joda.time.LocalTime;

/**
 *
 * @author Tomas Livora
 */
public class GoalTO {
    
    private Long goalId;
    private Long matchId;
    private Long teamId;
    private String teamName;
    private Long playerId;
    private String playerName;
    private LocalTime time;

    public GoalTO() {}

    public GoalTO(Long goalId, Long matchId, Long teamId, String teamName, Long playerId, String playerName,
            LocalTime time) {
        this.goalId = goalId;
        this.matchId = matchId;
        this.teamId = teamId;
        this.teamName = teamName;
        this.playerId = playerId;
        this.playerName = playerName;
        this.time = time;
    }

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.goalId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GoalTO other = (GoalTO) obj;
        return Objects.equals(this.goalId, other.goalId);
    }

    @Override
    public String toString() {
        return "GoalDetail{" + "goalId=" + goalId + ", matchId=" + matchId + ", teamId=" + teamId + ", teamName=" 
                + teamName + ", playerId=" + playerId + ", playerName=" + playerName + ", time=" + time + '}';
    }

}
