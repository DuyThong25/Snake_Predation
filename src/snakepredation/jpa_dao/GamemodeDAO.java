package snakepredation.jpa_dao;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import snakepredation.jpa_Model.Gamemode;
import snakepredation.jpa_controller.GamemodeJpaController;
import snakepredation.jpa_controller.exceptions.IllegalOrphanException;
import snakepredation.jpa_controller.exceptions.NonexistentEntityException;

public class GamemodeDAO {

    private final GamemodeJpaController gamemodeController;
    private final EntityManagerFactory emf;

    public GamemodeDAO() {
        this.emf = Persistence.createEntityManagerFactory("SnakePredationPU");
        this.gamemodeController = new GamemodeJpaController(emf);
    }
    public void addGamemode(Gamemode Gamemode) throws Exception {
        gamemodeController.create(Gamemode);
    }

    public void editGamemode(Gamemode Gamemode) throws Exception {
        gamemodeController.edit(Gamemode);
    }

    public void removeGamemode(int id) throws IllegalOrphanException, NonexistentEntityException { 
        gamemodeController.destroy(id);
    }

    public List<Gamemode> getAllGamemode() {
        return gamemodeController.findGamemodeEntities();
    }

    public Gamemode getGamemodeById(int id) {
        return gamemodeController.findGamemode(id);
    }

}
