package cz.muni.fi.pa165.mamatoad.soccerrecords.dto;

import java.util.Objects;
import org.joda.time.LocalDate;

/**
 *
 * @author Adriana Smijakova
 */
public class MatchTO {
    
    private Long matchId;
    
    private Long homeTeamId;
    private String homeTeamName;
    private int homeTeamScore;
    
    private Long visitingTeamId;
    private String visitingTeamName;
    private int visitingTeamScore;
    
    private LocalDate eventDate;
    private Long winnerTeamId;
    
    public MatchTO(){
    }

    public MatchTO(Long matchId, Long homeTeamId, String homeTeamName, int homeTeamScore, Long visitingTeamId,
            String visitingTeamName, int visitingTeamScore, LocalDate eventDate, Long winnerTeamId) {
        this.matchId = matchId;
        this.homeTeamId = homeTeamId;
        this.homeTeamName = homeTeamName;
        this.homeTeamScore = homeTeamScore;
        this.visitingTeamId = visitingTeamId;
        this.visitingTeamName = visitingTeamName;
        this.visitingTeamScore = visitingTeamScore;
        this.eventDate = eventDate;
        this.winnerTeamId = winnerTeamId;
    }

    public Long getMatchId() {
        return matchId;
    }

    public Long getHomeTeamId() {
        return homeTeamId;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public Long getVisitingTeamId() {
        return visitingTeamId;
    }

    public String getVisitingTeamName() {
        return visitingTeamName;
    }

    public int getVisitingTeamScore() {
        return visitingTeamScore;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public Long getWinnerTeamId() {
        return winnerTeamId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public void setHomeTeamId(Long homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public void setVisitingTeamId(Long visitingTeamId) {
        this.visitingTeamId = visitingTeamId;
    }

    public void setVisitingTeamName(String visitingTeamName) {
        this.visitingTeamName = visitingTeamName;
    }

    public void setVisitingTeamScore(int visitingTeamScore) {
        this.visitingTeamScore = visitingTeamScore;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public void setWinnerTeamId(Long winnerTeamId) {
        this.winnerTeamId = winnerTeamId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.matchId);
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
        final MatchTO other = (MatchTO) obj;
        if (!Objects.equals(this.matchId, other.matchId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MatchTO{" + "matchId=" + matchId + ", homeTeamId=" + homeTeamId + ", homeTeamName=" + homeTeamName 
                + ", homeTeamScore=" + homeTeamScore + ", visitingTeamId=" + visitingTeamId + ", visitingTeamName=" 
                + visitingTeamName + ", visitingTeamScore=" + visitingTeamScore + ", eventDate=" + eventDate 
                + ", winnerTeamId=" + winnerTeamId + '}';
    }

    
 }
