package snakepredation.jpa_dao;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import snakepredation.jpa_Model.Snake;
import snakepredation.jpa_controller.SnakeJpaController;
import snakepredation.jpa_controller.exceptions.IllegalOrphanException;
import snakepredation.jpa_controller.exceptions.NonexistentEntityException;

public class SnakeDAO {

    private final SnakeJpaController snakeController;
    private final EntityManagerFactory emf;

    public SnakeDAO() {
        this.emf = Persistence.createEntityManagerFactory("SnakePredationPU");
        this.snakeController = new SnakeJpaController(emf);
    }
    
    public void addSnake(Snake snake) throws Exception {
        snakeController.create(snake);
    }

    public void editSnake(Snake snake) throws Exception {
        snakeController.edit(snake);
    }

    public void removeSnake(int id) throws IllegalOrphanException, NonexistentEntityException {
        snakeController.destroy(id);
    }

    public List<Snake> getAllSnake() {
        return snakeController.findSnakeEntities();
    }

    public Snake getSnakeById(int id) {
        return snakeController.findSnake(id);
    }
}
