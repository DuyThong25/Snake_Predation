package snakepredation.jpa_dao;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import snakepredation.jpa_Model.Gamedetail;
import snakepredation.jpa_Model.GamedetailPK;
import snakepredation.jpa_controller.GamedetailJpaController;
import snakepredation.jpa_controller.exceptions.IllegalOrphanException;
import snakepredation.jpa_controller.exceptions.NonexistentEntityException;

public class GamedetailDAO {

    private final GamedetailJpaController gamedetailController;
    private final EntityManagerFactory emf;

    public GamedetailDAO() {
        this.emf = Persistence.createEntityManagerFactory("SnakePredationPU");
        this.gamedetailController = new GamedetailJpaController(emf);
    }

    public void addGamedetail(Gamedetail Gamedetail) throws Exception {
        gamedetailController.create(Gamedetail);
    }

    public void editGamedetail(Gamedetail Gamedetail) throws Exception {
        gamedetailController.edit(Gamedetail);
    }

    public void removeGamedetail(GamedetailPK id) throws IllegalOrphanException, NonexistentEntityException {
        gamedetailController.destroy(id);
    }

    public List<Gamedetail> getAllGamedetail() {
        return gamedetailController.findGamedetailEntities();
    }

    public Gamedetail getGamedetailById(GamedetailPK gamedetailPK) {
        return gamedetailController.findGamedetail(gamedetailPK);
    }
    
    public int getNextGamedetailID() {
        return gamedetailController.findNextGamedetailID();
    }
}
