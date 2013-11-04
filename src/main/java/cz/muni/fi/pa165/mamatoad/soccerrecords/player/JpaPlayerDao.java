package cz.muni.fi.pa165.mamatoad.soccerrecords.player;

import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of PlayerDao interface.
 * This interface provides create, retrieve, update and delete methods for work with Player entity.
 * @author Maros Klimovsky
 */
@Repository("playerDao")
public class JpaPlayerDao implements PlayerDao {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void createPlayer(Player player) {
        if (player == null)
            throw new IllegalArgumentException("player cannot be null");
        
        if (player.getId() != null)
            throw new IllegalEntityException("player.id is already set");
        
        if (player.getName() == null || player.getName().isEmpty())
            throw new IllegalEntityException("player.name cannot be null");
        
        if ((player.getTeam() != null && player.getTeam().getId() == null))
            throw new IllegalEntityException("player.team.id cannot be null");
        
        em.persist(player);
                
    }

    @Override
    public void updatePlayer(Player player) {
        if (player == null)
            throw new IllegalArgumentException("player cannot be null");
        
        if (player.getName() == null || player.getName().isEmpty())
            throw new IllegalEntityException("player.name cannot be null");
        
        if (player.getId() == null)
            throw new IllegalEntityException("player.id cannot be null");
        
        if ((player.getTeam() != null && player.getTeam().getId() == null))
            throw new IllegalEntityException("player.team.id cannot be null");
         
        if(em.find(Player.class, player.getId()) == null)
           throw new IllegalEntityException("player "+player+" doesn't exist");
        
        em.merge(player);
        
    }
    
    @Override
    public void deletePlayer(Player player) {
        if (player == null)
            throw new IllegalArgumentException("player cannot be null");
        
        if (player.getId() == null)
            throw new IllegalEntityException("player.id cannot be null");
        
        if(em.find(Player.class, player.getId()) == null)
           throw new IllegalEntityException("player doesn't exist");
        
        Player target = em.merge(player);
        em.remove(target);
        
    }

    @Override
    public Player retrievePlayerById(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id cannot be null");
        
        Player player = em.find(Player.class,id);
        
        return player;
    }

    @Override
    public List<Player> retrievePlayersByName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("name cannot be null");
        
        TypedQuery<Player> query = em.createQuery("Select p from Player p Where p.name=:name", Player.class).setParameter("name", name);
        
        List<Player> players = query.getResultList();
        
        return players;
    }

    @Override
    public List<Player> retrievePlayersByTeam(Team team) {
        if (team == null)
            throw new IllegalArgumentException("team cannot be null");
        
        if (team.getId() == null)
            throw new IllegalEntityException("team.id cannot be null");
        
        if (team.getId() != null && !teamExists(team))
            throw new IllegalEntityException("team " + team + " doesn't exist");
        
        TypedQuery<Player> query = em.createQuery("Select p from Player p Where p.team=:team", Player.class).setParameter("team", team);
        List<Player> players = query.getResultList();
        
        return players;
    }

    @Override
    public List<Player> retrievePlayersByActivity(boolean active) {
        
        TypedQuery<Player> query = em.createQuery("Select p from Player p Where p.active=:active", Player.class).setParameter("active", active);
        List<Player> players = query.getResultList();
        
        return players;
    }
    
    @Override
    public List<Player> retrieveAllPlayers() {
        
        TypedQuery<Player> query = em.createQuery("Select p from Player p", Player.class);
        List<Player> players = query.getResultList();
        
        return players;
    }
    
    private boolean teamExists(Team team) {
        
        Long id = team.getId();
        
        Team teamInDb = em.find(Team.class, id);
        
        return (teamInDb != null);
    }
    
}
