package cz.muni.fi.pa165.mamatoad.soccerrecords.entity;

import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Goal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.joda.time.LocalDate;

/**
 * This class represents entity Match
 * 
 * @author Maros Klimovsky
 */
@Entity
@Table(name = "SoccerMatch")
public class Match implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @OneToOne
    private Team homeTeam;
    
    @OneToOne
    private Team visitingTeam;
    
    private LocalDate eventDate;
    
    @OneToMany(mappedBy = "match")
    private List<Goal> goals;

    public Match() {
        goals = new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getVisitingTeam() {
        return visitingTeam;
    }

    public void setVisitingTeam(Team visitingTeam) {
        this.visitingTeam = visitingTeam;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public List<Goal> getGoals() {
        return goals;
    }
    
    @Deprecated
    // only for mocking in tests
    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final Match other = (Match) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Match{" + "id=" + id + ", homeTeam=" + homeTeam + ", visitingTeam=" + visitingTeam + ", eventDate=" + eventDate + '}';
    }
    
    
}
