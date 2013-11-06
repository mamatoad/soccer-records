package cz.muni.fi.pa165.mamatoad.soccerrecords.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.joda.time.LocalTime;

/**
 * This class represents entity Goal
 * 
 * @author Maros Klimovsky
 */
@Entity
public class Goal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne
    private Match match;
    
    @OneToOne
    private Team team;
    
    @OneToOne
    private Player player;
    
    private LocalTime shootingTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public LocalTime getShootingTime() {
        return shootingTime;
    }

    public void setShootingTime(LocalTime shootingTime) {
        this.shootingTime = shootingTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final Goal other = (Goal) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Goal{" + "id=" + id + ", match=" + match + ", team=" + team + ", player=" + player + ", shootingTime=" + shootingTime + '}';
    }
    
    
}
