package snakepredation.jpa_dao;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import snakepredation.jpa_Model.Player;
import snakepredation.jpa_controller.PlayerJpaController;
import snakepredation.jpa_controller.exceptions.IllegalOrphanException;
import snakepredation.jpa_controller.exceptions.NonexistentEntityException;

public class PlayerDAO {

    private final PlayerJpaController playerController;
    private final EntityManagerFactory emf;

    public PlayerDAO() {
        this.emf = Persistence.createEntityManagerFactory("SnakePredationPU");
        this.playerController = new PlayerJpaController(emf);
    }

    public void addPlayer(Player player) throws Exception {
        playerController.create(player);
    }

    public void editPlayer(Player player) throws Exception {
        playerController.edit(player);
    }
    public void removePlayer(int id) throws IllegalOrphanException, NonexistentEntityException {
        playerController.destroy(id);
    }   
    
    public List<Player> getAllPlayer() {
        return playerController.findPlayerEntities();
    }
    public Player getPlayerById(int playerID) {
        return playerController.findPlayer(playerID);
    }
    public int getNextPlayerID() {
        return playerController.findNextPlayerID();
    }
}
