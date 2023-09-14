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
import snakepredation.jpa_Model.Player;
import snakepredation.jpa_Model.Scores;
import snakepredation.jpa_Model.ScoresPK;
import snakepredation.jpa_controller.exceptions.NonexistentEntityException;
import snakepredation.jpa_controller.exceptions.PreexistingEntityException;

/**
 *
 * @author duyth
 */
public class ScoresJpaController implements Serializable {

    public ScoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Scores scores) throws PreexistingEntityException, Exception {
        if (scores.getScoresPK() == null) {
            scores.setScoresPK(new ScoresPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Player playerID = scores.getPlayerID();
            if (playerID != null) {
                playerID = em.getReference(playerID.getClass(), playerID.getPlayerID());
                scores.setPlayerID(playerID);
            }
            em.persist(scores);
            if (playerID != null) {
                playerID.getScoresCollection().add(scores);
                playerID = em.merge(playerID);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findScores(scores.getScoresPK()) != null) {
                throw new PreexistingEntityException("Scores " + scores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Scores scores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Scores persistentScores = em.find(Scores.class, scores.getScoresPK());
            Player playerIDOld = persistentScores.getPlayerID();
            Player playerIDNew = scores.getPlayerID();
            if (playerIDNew != null) {
                playerIDNew = em.getReference(playerIDNew.getClass(), playerIDNew.getPlayerID());
                scores.setPlayerID(playerIDNew);
            }
            scores = em.merge(scores);
            if (playerIDOld != null && !playerIDOld.equals(playerIDNew)) {
                playerIDOld.getScoresCollection().remove(scores);
                playerIDOld = em.merge(playerIDOld);
            }
            if (playerIDNew != null && !playerIDNew.equals(playerIDOld)) {
                playerIDNew.getScoresCollection().add(scores);
                playerIDNew = em.merge(playerIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ScoresPK id = scores.getScoresPK();
                if (findScores(id) == null) {
                    throw new NonexistentEntityException("The scores with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ScoresPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Scores scores;
            try {
                scores = em.getReference(Scores.class, id);
                scores.getScoresPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The scores with id " + id + " no longer exists.", enfe);
            }
            Player playerID = scores.getPlayerID();
            if (playerID != null) {
                playerID.getScoresCollection().remove(scores);
                playerID = em.merge(playerID);
            }
            em.remove(scores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Scores> findScoresEntities() {
        return findScoresEntities(true, -1, -1);
    }

    public List<Scores> findScoresEntities(int maxResults, int firstResult) {
        return findScoresEntities(false, maxResults, firstResult);
    }

    private List<Scores> findScoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Scores.class));
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

    public Scores findScores(ScoresPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Scores.class, id);
        } finally {
            em.close();
        }
    }

    public int getScoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Scores> rt = cq.from(Scores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
