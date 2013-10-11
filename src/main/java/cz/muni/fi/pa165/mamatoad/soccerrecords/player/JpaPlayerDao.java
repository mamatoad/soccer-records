package cz.muni.fi.pa165.mamatoad.soccerrecords.player;

import cz.muni.fi.pa165.mamatoad.soccerrecords.team.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception.IllegalEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 * JPA implementation of PlayerDao interface.
 * This interface provides create, retrieve, update and delete methods for work with Player entity.
 * @author Maros Klimovsky
 */
public class JpaPlayerDao implements PlayerDao {

    private EntityManagerFactory entityManagerFactory;
    
    public JpaPlayerDao(EntityManagerFactory entityManagerFactory) {
        if(entityManagerFactory == null)
            throw new IllegalArgumentException("entityManagerFactory cannot be null");
        
        this.entityManagerFactory = entityManagerFactory;
    }
    
    @Override
    public void createPlayer(Player player) throws IllegalEntityException {
        if (player == null)
            throw new IllegalArgumentException("player cannot be null");
        
        if (player.getId() != null)
            throw new IllegalEntityException("player.id is already set");
        
        if (player.getName() == null || player.getName().isEmpty())
            throw new IllegalEntityException("player.name cannot be null");
        
        if ((player.getTeam() != null && player.getTeam().getId() == null))
            throw new IllegalEntityException("player.team.id cannot be null");
        
                  
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(player);
        entityManager.getTransaction().commit();
                
    }

    @Override
    public void updatePlayer(Player player) throws IllegalEntityException {
        if (player == null)
            throw new IllegalArgumentException("player cannot be null");
        
        if (player.getName() == null || player.getName().isEmpty())
            throw new IllegalEntityException("player.name cannot be null");
        
        if (player.getId() == null)
            throw new IllegalEntityException("player.id cannot be null");
        
        if ((player.getTeam() != null && player.getTeam().getId() == null))
            throw new IllegalEntityException("player.team.id cannot be null");
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
         
        if(entityManager.find(Player.class, player.getId()) == null)
           throw new IllegalEntityException("player "+player+" doesn't exist");
        
        entityManager.getTransaction().begin();
        entityManager.merge(player);
        entityManager.getTransaction().commit();
        
    }
    
    @Override
    public void deletePlayer(Player player) throws IllegalEntityException {
        if (player == null)
            throw new IllegalArgumentException("player cannot be null");
        
        if (player.getId() == null)
            throw new IllegalEntityException("player.id cannot be null");
            
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        if(entityManager.find(Player.class, player.getId()) == null)
           throw new IllegalEntityException("player doesn't exist");
        
        entityManager.getTransaction().begin();
        Player target = entityManager.merge(player);
        entityManager.remove(target);
        entityManager.getTransaction().commit();
        
    }

    @Override
    public Player retrievePlayerById(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id cannot be null");
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        Player player = entityManager.find(Player.class,id);
        
        return player;
    }

    @Override
    public List<Player> retrievePlayersByName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("name cannot be null");
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        TypedQuery<Player> query = entityManager.createQuery("Select p from Player p Where p.name=:name", Player.class).setParameter(":name", name);
        Player player = query.getSingleResult();
        List<Player> players = query.getResultList();
        
        return players;
    }

    @Override
    public List<Player> retrievePlayersByTeam(Team team) throws IllegalEntityException {
        if (team == null)
            throw new IllegalArgumentException("team cannot be null");
        
        if (team.getId() == null)
            throw new IllegalEntityException("team.id cannot be null");
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        TypedQuery<Player> query = entityManager.createQuery("Select p from Player p Where p.team=:team", Player.class).setParameter(":team", team);
        List<Player> players = query.getResultList();
        
        return players;
    }

    @Override
    public List<Player> retrievePlayersByActivity(boolean active) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        TypedQuery<Player> query = entityManager.createQuery("Select p from Player p Where p.active=:active", Player.class).setParameter(":active", active);
        List<Player> players = query.getResultList();
        
        return players;
    }
    
}
