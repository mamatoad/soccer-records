package cz.muni.fi.pa165.mamatoad.soccerrecords.dto;

import java.util.Objects;

/**
 * Player Data Transfer Object.
 * @author Matus Nemec
 */
public class PlayerTO {
private Long playerId;
    private String playerName;
    private boolean playerActive;
    private Long playerGoalsScored; //derived
    
    private Long teamId;
    private String teamName;

    public PlayerTO() {
    }

    public PlayerTO(Long playerId, String playerName, boolean playerActive, Long playerGoalsScored, Long teamId, String teamName) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerActive = playerActive;
        this.playerGoalsScored = playerGoalsScored;
        this.teamId = teamId;
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

    public boolean isPlayerActive() {
        return playerActive;
    }

    public void setPlayerActive(boolean playerActive) {
        this.playerActive = playerActive;
    }

    public Long getPlayerGoalsScored() {
        return playerGoalsScored;
    }

    public void setPlayerGoalsScored(Long playerGoalsScored) {
        this.playerGoalsScored = playerGoalsScored;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.playerId);
        hash = 59 * hash + Objects.hashCode(this.playerName);
        hash = 59 * hash + (this.playerActive ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.playerGoalsScored);
        hash = 59 * hash + Objects.hashCode(this.teamId);
        hash = 59 * hash + Objects.hashCode(this.teamName);
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
        final PlayerTO other = (PlayerTO) obj;
        if (!Objects.equals(this.playerId, other.playerId)) {
            return false;
        }
        if (!Objects.equals(this.playerName, other.playerName)) {
            return false;
        }
        if (this.playerActive != other.playerActive) {
            return false;
        }
        if (!Objects.equals(this.playerGoalsScored, other.playerGoalsScored)) {
            return false;
        }
        if (!Objects.equals(this.teamId, other.teamId)) {
            return false;
        }
        if (!Objects.equals(this.teamName, other.teamName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PlayerTO{" + "playerId=" + playerId + ", playerName=" + playerName + ", playerActive=" + playerActive 
                + ", playerGoalsScored=" + playerGoalsScored + ", teamId=" + teamId + ", teamName=" + teamName + '}';
    }
}
