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
import snakepredation.jpa_Model.Gamedetail;
import snakepredation.jpa_Model.GamedetailPK;
import snakepredation.jpa_controller.exceptions.NonexistentEntityException;
import snakepredation.jpa_controller.exceptions.PreexistingEntityException;

/**
 *
 * @author duyth
 */
public class GamedetailJpaController implements Serializable {

    public GamedetailJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gamedetail gamedetail) throws PreexistingEntityException, Exception {
        if (gamedetail.getGamedetailPK() == null) {
            gamedetail.setGamedetailPK(new GamedetailPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(gamedetail);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGamedetail(gamedetail.getGamedetailPK()) != null) {
                throw new PreexistingEntityException("Gamedetail " + gamedetail + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gamedetail gamedetail) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            gamedetail = em.merge(gamedetail);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                GamedetailPK id = gamedetail.getGamedetailPK();
                if (findGamedetail(id) == null) {
                    throw new NonexistentEntityException("The gamedetail with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(GamedetailPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gamedetail gamedetail;
            try {
                gamedetail = em.getReference(Gamedetail.class, id);
                gamedetail.getGamedetailPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gamedetail with id " + id + " no longer exists.", enfe);
            }
            em.remove(gamedetail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gamedetail> findGamedetailEntities() {
        return findGamedetailEntities(true, -1, -1);
    }

    public List<Gamedetail> findGamedetailEntities(int maxResults, int firstResult) {
        return findGamedetailEntities(false, maxResults, firstResult);
    }

    private List<Gamedetail> findGamedetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gamedetail.class));
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

    public Gamedetail findGamedetail(GamedetailPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gamedetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getGamedetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gamedetail> rt = cq.from(Gamedetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public int findNextGamedetailID() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT g.gamedetailPK.gameID FROM Gamedetail g");
            List<Integer> existingGamedetailIDs = query.getResultList();

            if (existingGamedetailIDs.isEmpty()) {
                return 1; // Nếu không có gameID nào trong cơ sở dữ liệu, bắt đầu từ 1
            } else {
                Collections.sort(existingGamedetailIDs);

                for (int i = 1; i <= existingGamedetailIDs.size(); i++) {
                    if (!existingGamedetailIDs.contains(i)) {
                        return i;
                    }
                }

                return existingGamedetailIDs.get(existingGamedetailIDs.size() - 1) + 1; // Nếu tất cả số đã sử dụng, thêm 1 lên số cuối cùng
            }
        } finally {
            em.close();
        }
    }
}
