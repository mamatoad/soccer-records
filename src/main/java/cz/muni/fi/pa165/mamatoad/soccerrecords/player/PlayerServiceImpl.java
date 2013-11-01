package cz.muni.fi.pa165.mamatoad.soccerrecords.player;

import cz.muni.fi.pa165.mamatoad.soccerrecords.goal.GoalDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.team.TeamDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Player management implementation.
 * @author Matus Nemec
 */

@Service("playerService")
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private TeamDao teamDao;
    @Autowired
    private GoalDao goalDao;

    @Transactional
    @Override
    public void add(PlayerTO playerTO) {
        if (playerTO == null) {
            throw new IllegalArgumentException("playerTO is null");
        }
        playerDao.createPlayer(convertToEntity(playerTO));
    }

    @Transactional
    @Override
    public void update(PlayerTO playerTO) {
        if (playerTO == null) {
            throw new IllegalArgumentException("playerTO is null");
        }
        playerDao.updatePlayer(convertToEntity(playerTO));
    }

    @Transactional
    @Override
    public void remove(PlayerTO playerTO) {
        if (playerTO == null) {
            throw new IllegalArgumentException("playerTO is null");
        }
        playerDao.deletePlayer(convertToEntity(playerTO));
    }

    @Transactional
    @Override
    public PlayerTO getPlayerById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return convertToTransferObject(playerDao.retrievePlayerById(id));
    }

    @Transactional
    @Override
    public List<PlayerTO> getPlayersByTeamId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return convertToListOfTransferObjects(playerDao.retrievePlayersByTeam(teamDao.retrieveTeamById(id)));
    }

    @Transactional
    @Override
    public List<PlayerTO> getAllPlayers() {
        return convertToListOfTransferObjects(playerDao.retrieveAllPlayers());
    }
    
    private Player convertToEntity(PlayerTO playerTO) {
        Player player = new Player();
        player.setId(playerTO.getPlayerId());
        player.setName(playerTO.getPlayerName());
        if (playerTO.getTeamId() == null) {
            player.setTeam(null); 
        } else {
            player.setTeam(teamDao.retrieveTeamById(playerTO.getTeamId()));
        }
        player.setActive(playerTO.isPlayerActive());
        return player;
    }
    
    private PlayerTO convertToTransferObject(Player player) {
        PlayerTO playerTO = new PlayerTO();
        playerTO.setPlayerId(player.getId());
        playerTO.setPlayerName(player.getName());
        if (player.getTeam() == null) {
            playerTO.setTeamId(null);
            playerTO.setTeamName(null);
        } else {
            playerTO.setTeamId(player.getTeam().getId());
            playerTO.setTeamName(player.getTeam().getName());
        }
        
        playerTO.setPlayerActive(player.getActive()); 
        playerTO.setPlayerGoalsScored(new Long(goalDao.retrieveGoalsByPlayer(player).size()));
        return playerTO;
    }
    
    private List<PlayerTO> convertToListOfTransferObjects(List<Player> players) {
        List<PlayerTO> playerTOs = new ArrayList<>();
        for (Player player : players) {
            playerTOs.add(convertToTransferObject(player));
        }
        return playerTOs;
    }

}
