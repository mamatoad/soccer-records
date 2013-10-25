/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.mamatoad.soccerrecords.match;

import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import java.util.List;
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
    private Long visitingTeamId;
    private String visitingTeamName;
    private LocalDate eventDate;
    //derivated
    private boolean drawGame;
    private Long winnerId;
    private Long looserId;
    private Long homeTeamScore;
    private Long visitingTeamScore;

    public MatchTO(){
    }

    public MatchTO(Long matchId, String homeTeamName, Long homeTeamId,  Long visitingTeamId, String visitingTeamName,
            LocalDate eventDate, boolean drawGame, Long winnerId, Long looserId, Long homeTeamScore, 
            Long visitingTeamScore) {
        this.matchId = matchId;
        this.homeTeamId = homeTeamId;
        this.homeTeamName = homeTeamName;
        this.visitingTeamId = visitingTeamId;
        this.visitingTeamName = visitingTeamName;
        this.eventDate = eventDate;
        this.drawGame = drawGame;
        this.winnerId = winnerId;
        this.looserId = looserId;
        this.homeTeamScore = homeTeamScore;
        this.visitingTeamScore = visitingTeamScore;
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

    public Long getVisitingTeamId() {
        return visitingTeamId;
    }

    public String getVisitingTeamName() {
        return visitingTeamName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public boolean isDrawGame() {
        return drawGame;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public Long getLooserId() {
        return looserId;
    }

    public Long getHomeTeamScore() {
        return homeTeamScore;
    }

    public Long getVisitingTeamScore() {
        return visitingTeamScore;
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

    public void setVisitingTeamId(Long visitingTeamId) {
        this.visitingTeamId = visitingTeamId;
    }

    public void setVisitingTeamName(String visitingTeamName) {
        this.visitingTeamName = visitingTeamName;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public void setDrawGame(boolean drawGame) {
        this.drawGame = drawGame;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    public void setLooserId(Long looserId) {
        this.looserId = looserId;
    }

    public void setHomeTeamScore(Long homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public void setVisitingTeamScore(Long visitingTeamScore) {
        this.visitingTeamScore = visitingTeamScore;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.matchId);
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
        return "MatchTO{" + "matchId=" + matchId + ", homeTeamId=" + homeTeamId + ", homeTeamName=" + homeTeamName +
                ", visitingTeamId=" + visitingTeamId + ", visitingTeamName=" + visitingTeamName + ", eventDate=" + 
                eventDate + ", drawGame=" + drawGame + ", winnerId=" + winnerId + ", looserId=" + looserId + 
                ", homeTeamScore=" + homeTeamScore + ", visitingTeamScore=" + visitingTeamScore + '}';
    }

   

 }
