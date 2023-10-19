/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakepredation.jpa_controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import snakepredation.jpa_Model.Player;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import snakepredation.jpa_Model.Snake;
import snakepredation.jpa_controller.exceptions.IllegalOrphanException;
import snakepredation.jpa_controller.exceptions.NonexistentEntityException;

/**
 *
 * @author duyth
 */
public class SnakeJpaController implements Serializable {

    public SnakeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Snake snake) {
        if (snake.getPlayerCollection() == null) {
            snake.setPlayerCollection(new ArrayList<Player>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Player> attachedPlayerCollection = new ArrayList<Player>();
            for (Player playerCollectionPlayerToAttach : snake.getPlayerCollection()) {
                playerCollectionPlayerToAttach = em.getReference(playerCollectionPlayerToAttach.getClass(), playerCollectionPlayerToAttach.getPlayerID());
                attachedPlayerCollection.add(playerCollectionPlayerToAttach);
            }
            snake.setPlayerCollection(attachedPlayerCollection);
            em.persist(snake);
            for (Player playerCollectionPlayer : snake.getPlayerCollection()) {
                Snake oldSnakeIDOfPlayerCollectionPlayer = playerCollectionPlayer.getSnakeID();
                playerCollectionPlayer.setSnakeID(snake);
                playerCollectionPlayer = em.merge(playerCollectionPlayer);
                if (oldSnakeIDOfPlayerCollectionPlayer != null) {
                    oldSnakeIDOfPlayerCollectionPlayer.getPlayerCollection().remove(playerCollectionPlayer);
                    oldSnakeIDOfPlayerCollectionPlayer = em.merge(oldSnakeIDOfPlayerCollectionPlayer);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Snake snake) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Snake persistentSnake = em.find(Snake.class, snake.getSnakeID());
            Collection<Player> playerCollectionOld = persistentSnake.getPlayerCollection();
            Collection<Player> playerCollectionNew = snake.getPlayerCollection();
            List<String> illegalOrphanMessages = null;
            for (Player playerCollectionOldPlayer : playerCollectionOld) {
                if (!playerCollectionNew.contains(playerCollectionOldPlayer)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Player " + playerCollectionOldPlayer + " since its snakeID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Player> attachedPlayerCollectionNew = new ArrayList<Player>();
            for (Player playerCollectionNewPlayerToAttach : playerCollectionNew) {
                playerCollectionNewPlayerToAttach = em.getReference(playerCollectionNewPlayerToAttach.getClass(), playerCollectionNewPlayerToAttach.getPlayerID());
                attachedPlayerCollectionNew.add(playerCollectionNewPlayerToAttach);
            }
            playerCollectionNew = attachedPlayerCollectionNew;
            snake.setPlayerCollection(playerCollectionNew);
            snake = em.merge(snake);
            for (Player playerCollectionNewPlayer : playerCollectionNew) {
                if (!playerCollectionOld.contains(playerCollectionNewPlayer)) {
                    Snake oldSnakeIDOfPlayerCollectionNewPlayer = playerCollectionNewPlayer.getSnakeID();
                    playerCollectionNewPlayer.setSnakeID(snake);
                    playerCollectionNewPlayer = em.merge(playerCollectionNewPlayer);
                    if (oldSnakeIDOfPlayerCollectionNewPlayer != null && !oldSnakeIDOfPlayerCollectionNewPlayer.equals(snake)) {
                        oldSnakeIDOfPlayerCollectionNewPlayer.getPlayerCollection().remove(playerCollectionNewPlayer);
                        oldSnakeIDOfPlayerCollectionNewPlayer = em.merge(oldSnakeIDOfPlayerCollectionNewPlayer);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = snake.getSnakeID();
                if (findSnake(id) == null) {
                    throw new NonexistentEntityException("The snake with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Snake snake;
            try {
                snake = em.getReference(Snake.class, id);
                snake.getSnakeID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The snake with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Player> playerCollectionOrphanCheck = snake.getPlayerCollection();
            for (Player playerCollectionOrphanCheckPlayer : playerCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Snake (" + snake + ") cannot be destroyed since the Player " + playerCollectionOrphanCheckPlayer + " in its playerCollection field has a non-nullable snakeID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(snake);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Snake> findSnakeEntities() {
        return findSnakeEntities(true, -1, -1);
    }

    public List<Snake> findSnakeEntities(int maxResults, int firstResult) {
        return findSnakeEntities(false, maxResults, firstResult);
    }

    private List<Snake> findSnakeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Snake.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Snake findSnake(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Snake.class, id);
        } finally {
            em.close();
        }
    }

    public int getSnakeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Snake> rt = cq.from(Snake.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
