package cz.muni.fi.pa165.mamatoad.soccerrecords.dto;

import java.util.Objects;

/**
 *
 * @author Maros Klimovsky
 */
public class TeamTO {
    private Long teamId;
    private String teamName;
       
    private long numberOfWins;
    private long numberOfLosses;
    private long numberOfTies;
    
    private long numberOfGoalsShot;
    private long numberOfGoalsReceived;

    public TeamTO() {
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

    public long getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(long numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public long getNumberOfLosses() {
        return numberOfLosses;
    }

    public void setNumberOfLosses(long numberOfLosses) {
        this.numberOfLosses = numberOfLosses;
    }

    public long getNumberOfTies() {
        return numberOfTies;
    }

    public void setNumberOfTies(long numberOfTies) {
        this.numberOfTies = numberOfTies;
    }

    public long getNumberOfGoalsShot() {
        return numberOfGoalsShot;
    }

    public void setNumberOfGoalsShot(long numberOfGoalsShot) {
        this.numberOfGoalsShot = numberOfGoalsShot;
    }

    public long getNumberOfGoalsReceived() {
        return numberOfGoalsReceived;
    }

    public void setNumberOfGoalsReceived(long numberOfGoalsReceived) {
        this.numberOfGoalsReceived = numberOfGoalsReceived;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.teamId);
        hash = 83 * hash + Objects.hashCode(this.teamName);
        hash = 83 * hash + Objects.hashCode(this.numberOfWins);
        hash = 83 * hash + Objects.hashCode(this.numberOfLosses);
        hash = 83 * hash + Objects.hashCode(this.numberOfTies);
        hash = 83 * hash + Objects.hashCode(this.numberOfGoalsShot);
        hash = 83 * hash + Objects.hashCode(this.numberOfGoalsReceived);
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
        final TeamTO other = (TeamTO) obj;
        if (!Objects.equals(this.teamId, other.teamId)) {
            return false;
        }
        if (!Objects.equals(this.teamName, other.teamName)) {
            return false;
        }
        
        if (!Objects.equals(this.numberOfWins, other.numberOfWins)) {
            return false;
        }
        if (!Objects.equals(this.numberOfLosses, other.numberOfLosses)) {
            return false;
        }
        if (!Objects.equals(this.numberOfTies, other.numberOfTies)) {
            return false;
        }
        if (!Objects.equals(this.numberOfGoalsShot, other.numberOfGoalsShot)) {
            return false;
        }
        return Objects.equals(this.numberOfGoalsReceived, other.numberOfGoalsReceived);
    }

    @Override
    public String toString() {
        return "TeamDetail{" + "teamId=" + teamId + ", teamName=" + teamName + ", numberOfWins=" + numberOfWins + ", numberOfLosses=" + numberOfLosses + ", numberOfTies=" + numberOfTies + ", numberOfGoalsShot=" + numberOfGoalsShot + ", numberOfGoalsRecieved=" + numberOfGoalsReceived + '}';
    }
    
    
}
