package cz.muni.fi.pa165.mamatoad.soccerrecords.service.impl;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.PlayerDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Player;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.GoalDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dao.TeamDao;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.PlayerTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.entity.Team;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Acl;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Role;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.PlayerService;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Player management implementation.
 *
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
    @Acl(Role.ADMIN)
    public void add(PlayerTO playerTO) {
        if (playerTO == null) {
            throw new IllegalArgumentException("playerTO is null");
        }
        playerDao.createPlayer(convertToEntity(playerTO));
    }

    @Transactional
    @Override
    @Acl(Role.ADMIN)
    public void update(PlayerTO playerTO) {
        if (playerTO == null) {
            throw new IllegalArgumentException("playerTO is null");
        }
        playerDao.updatePlayer(convertToEntity(playerTO));
    }

    @Transactional
    @Override
    @Acl(Role.ADMIN)
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
        Player returnedPlayer = playerDao.retrievePlayerById(id);

        if (returnedPlayer == null) {
            return null;
        }

        return convertToTransferObject(returnedPlayer);
    }

    @Transactional
    @Override
    public List<PlayerTO> getPlayersByTeamId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        Team team = teamDao.retrieveTeamById(id);
        if (team == null) {
            return new ArrayList<>();
        }
        return convertToListOfTransferObjects(playerDao.retrievePlayersByTeam(team));
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

    @Override
    public List<PlayerTO> getPlayersByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name is null");
        }
        List<Player> returnedPlayers = playerDao.retrievePlayersByName(name);

        return convertToListOfTransferObjects(returnedPlayers);
    }

    @Override
    public List<PlayerTO> getFilteredPlayers(String searchTerm) {
        if (searchTerm == null) {
            throw new IllegalArgumentException("search term is null");
        }
        List<PlayerTO> plist = new ArrayList<>(getAllPlayers());
        ListIterator<PlayerTO> pli = plist.listIterator();
        Pattern regex = Pattern.compile(Pattern.quote(searchTerm), Pattern.CASE_INSENSITIVE);
        while (pli.hasNext()) {
            PlayerTO p = pli.next();
            if (!regex.matcher(p.getPlayerName()).find()) {
                pli.remove();
            }
        }
        return plist;
    }
}
