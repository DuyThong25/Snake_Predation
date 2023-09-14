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
import snakepredation.jpa_Model.Gamemode;
import snakepredation.jpa_controller.exceptions.IllegalOrphanException;
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
        if (gamemode.getPlayerCollection() == null) {
            gamemode.setPlayerCollection(new ArrayList<Player>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Player> attachedPlayerCollection = new ArrayList<Player>();
            for (Player playerCollectionPlayerToAttach : gamemode.getPlayerCollection()) {
                playerCollectionPlayerToAttach = em.getReference(playerCollectionPlayerToAttach.getClass(), playerCollectionPlayerToAttach.getPlayerID());
                attachedPlayerCollection.add(playerCollectionPlayerToAttach);
            }
            gamemode.setPlayerCollection(attachedPlayerCollection);
            em.persist(gamemode);
            for (Player playerCollectionPlayer : gamemode.getPlayerCollection()) {
                Gamemode oldGameModeIDOfPlayerCollectionPlayer = playerCollectionPlayer.getGameModeID();
                playerCollectionPlayer.setGameModeID(gamemode);
                playerCollectionPlayer = em.merge(playerCollectionPlayer);
                if (oldGameModeIDOfPlayerCollectionPlayer != null) {
                    oldGameModeIDOfPlayerCollectionPlayer.getPlayerCollection().remove(playerCollectionPlayer);
                    oldGameModeIDOfPlayerCollectionPlayer = em.merge(oldGameModeIDOfPlayerCollectionPlayer);
                }
            }
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

    public void edit(Gamemode gamemode) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gamemode persistentGamemode = em.find(Gamemode.class, gamemode.getGameModeID());
            Collection<Player> playerCollectionOld = persistentGamemode.getPlayerCollection();
            Collection<Player> playerCollectionNew = gamemode.getPlayerCollection();
            List<String> illegalOrphanMessages = null;
            for (Player playerCollectionOldPlayer : playerCollectionOld) {
                if (!playerCollectionNew.contains(playerCollectionOldPlayer)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Player " + playerCollectionOldPlayer + " since its gameModeID field is not nullable.");
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
            gamemode.setPlayerCollection(playerCollectionNew);
            gamemode = em.merge(gamemode);
            for (Player playerCollectionNewPlayer : playerCollectionNew) {
                if (!playerCollectionOld.contains(playerCollectionNewPlayer)) {
                    Gamemode oldGameModeIDOfPlayerCollectionNewPlayer = playerCollectionNewPlayer.getGameModeID();
                    playerCollectionNewPlayer.setGameModeID(gamemode);
                    playerCollectionNewPlayer = em.merge(playerCollectionNewPlayer);
                    if (oldGameModeIDOfPlayerCollectionNewPlayer != null && !oldGameModeIDOfPlayerCollectionNewPlayer.equals(gamemode)) {
                        oldGameModeIDOfPlayerCollectionNewPlayer.getPlayerCollection().remove(playerCollectionNewPlayer);
                        oldGameModeIDOfPlayerCollectionNewPlayer = em.merge(oldGameModeIDOfPlayerCollectionNewPlayer);
                    }
                }
            }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Collection<Player> playerCollectionOrphanCheck = gamemode.getPlayerCollection();
            for (Player playerCollectionOrphanCheckPlayer : playerCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Gamemode (" + gamemode + ") cannot be destroyed since the Player " + playerCollectionOrphanCheckPlayer + " in its playerCollection field has a non-nullable gameModeID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
