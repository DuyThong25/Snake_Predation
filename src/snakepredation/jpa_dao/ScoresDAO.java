package snakepredation.jpa_dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import snakepredation.jpa_Model.Scores;
import snakepredation.jpa_Model.ScoresPK;
import snakepredation.jpa_controller.ScoresJpaController;
import snakepredation.jpa_controller.exceptions.IllegalOrphanException;
import snakepredation.jpa_controller.exceptions.NonexistentEntityException;

public class ScoresDAO {

    private final ScoresJpaController scoresController;
    private final EntityManagerFactory emf;

    public ScoresDAO() {
        this.emf = Persistence.createEntityManagerFactory("SnakePredationPU");
        this.scoresController = new ScoresJpaController(emf);
    }

    public void addScores(Scores scores)  {
        try {
            scoresController.create(scores);
        } catch (Exception ex) {
            Logger.getLogger(ScoresDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editScores(Scores scores) throws Exception {
        scoresController.edit(scores);
    }

    public void removeScores(ScoresPK id) throws IllegalOrphanException, NonexistentEntityException { 
        scoresController.destroy(id);
    }

    public List<Scores> getAllScores() {
        return scoresController.findScoresEntities();
    }

    public Scores getScoresById(ScoresPK id) {
        return scoresController.findScores(id);
    }

}
