/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakepredation.jpa_controller;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import snakepredation.jpa_Model.Player;
import snakepredation.jpa_Model.Snake;
import snakepredation.jpa_controller.exceptions.NonexistentEntityException;
import snakepredation.jpa_controller.exceptions.PreexistingEntityException;

/**
 *
 * @author duyth
 */
public class PlayerJpaController implements Serializable {

    public PlayerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Player player) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Snake snakeID = player.getSnakeID();
            if (snakeID != null) {
                snakeID = em.getReference(snakeID.getClass(), snakeID.getSnakeID());
                player.setSnakeID(snakeID);
            }
            em.persist(player);
            if (snakeID != null) {
                snakeID.getPlayerCollection().add(player);
                snakeID = em.merge(snakeID);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlayer(player.getPlayerID()) != null) {
                throw new PreexistingEntityException("Player " + player + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Player player) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Player persistentPlayer = em.find(Player.class, player.getPlayerID());
            Snake snakeIDOld = persistentPlayer.getSnakeID();
            Snake snakeIDNew = player.getSnakeID();
            if (snakeIDNew != null) {
                snakeIDNew = em.getReference(snakeIDNew.getClass(), snakeIDNew.getSnakeID());
                player.setSnakeID(snakeIDNew);
            }
            player = em.merge(player);
            if (snakeIDOld != null && !snakeIDOld.equals(snakeIDNew)) {
                snakeIDOld.getPlayerCollection().remove(player);
                snakeIDOld = em.merge(snakeIDOld);
            }
            if (snakeIDNew != null && !snakeIDNew.equals(snakeIDOld)) {
                snakeIDNew.getPlayerCollection().add(player);
                snakeIDNew = em.merge(snakeIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = player.getPlayerID();
                if (findPlayer(id) == null) {
                    throw new NonexistentEntityException("The player with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Player player;
            try {
                player = em.getReference(Player.class, id);
                player.getPlayerID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The player with id " + id + " no longer exists.", enfe);
            }
            Snake snakeID = player.getSnakeID();
            if (snakeID != null) {
                snakeID.getPlayerCollection().remove(player);
                snakeID = em.merge(snakeID);
            }
            em.remove(player);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Player> findPlayerEntities() {
        return findPlayerEntities(true, -1, -1);
    }

    public List<Player> findPlayerEntities(int maxResults, int firstResult) {
        return findPlayerEntities(false, maxResults, firstResult);
    }

    private List<Player> findPlayerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Player.class));
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

    public Player findPlayer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Player.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlayerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Player> rt = cq.from(Player.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public int findNextPlayerID() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT p.playerID FROM Player p");
            List<Integer> existingPlayerIDs = query.getResultList();

            if (existingPlayerIDs.isEmpty()) {
                return 1; // Nếu không có playerID nào trong cơ sở dữ liệu, bắt đầu từ 1
            } else {
                Collections.sort(existingPlayerIDs);

                for (int i = 1; i <= existingPlayerIDs.size(); i++) {
                    if (!existingPlayerIDs.contains(i)) {
                        return i;
                    }
                }

                return existingPlayerIDs.get(existingPlayerIDs.size() - 1) + 1; // Nếu tất cả số đã sử dụng, thêm 1 lên số cuối cùng
            }
        } finally {
            em.close();
        }
    }
}
