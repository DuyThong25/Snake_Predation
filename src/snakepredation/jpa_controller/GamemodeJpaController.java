/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakepredation.jpa_controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import snakepredation.jpa_Model.Gamemode;
import snakepredation.jpa_controller.exceptions.NonexistentEntityException;
import snakepredation.jpa_controller.exceptions.PreexistingEntityException;

/**
 *
 * @author duyth
 */
public class GamemodeJpaController implements Serializable {

    public GamemodeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gamemode gamemode) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(gamemode);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGamemode(gamemode.getGameModeID()) != null) {
                throw new PreexistingEntityException("Gamemode " + gamemode + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gamemode gamemode) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            gamemode = em.merge(gamemode);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gamemode.getGameModeID();
                if (findGamemode(id) == null) {
                    throw new NonexistentEntityException("The gamemode with id " + id + " no longer exists.");
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
            Gamemode gamemode;
            try {
                gamemode = em.getReference(Gamemode.class, id);
                gamemode.getGameModeID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gamemode with id " + id + " no longer exists.", enfe);
            }
            em.remove(gamemode);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gamemode> findGamemodeEntities() {
        return findGamemodeEntities(true, -1, -1);
    }

    public List<Gamemode> findGamemodeEntities(int maxResults, int firstResult) {
        return findGamemodeEntities(false, maxResults, firstResult);
    }

    private List<Gamemode> findGamemodeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gamemode.class));
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

    public Gamemode findGamemode(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gamemode.class, id);
        } finally {
            em.close();
        }
    }

    public int getGamemodeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gamemode> rt = cq.from(Gamemode.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
