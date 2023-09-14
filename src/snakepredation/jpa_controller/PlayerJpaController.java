
package snakepredation.jpa_controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import snakepredation.jpa_Model.Gamemode;
import snakepredation.jpa_Model.Scores;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import snakepredation.jpa_Model.Player;
import snakepredation.jpa_controller.exceptions.IllegalOrphanException;
import snakepredation.jpa_controller.exceptions.NonexistentEntityException;

public class PlayerJpaController implements Serializable {

    public PlayerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Player player) {
        if (player.getScoresCollection() == null) {
            player.setScoresCollection(new ArrayList<Scores>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gamemode gameModeID = player.getGameModeID();
            if (gameModeID != null) {
                gameModeID = em.getReference(gameModeID.getClass(), gameModeID.getGameModeID());
                player.setGameModeID(gameModeID);
            }
            Collection<Scores> attachedScoresCollection = new ArrayList<Scores>();
            for (Scores scoresCollectionScoresToAttach : player.getScoresCollection()) {
                scoresCollectionScoresToAttach = em.getReference(scoresCollectionScoresToAttach.getClass(), scoresCollectionScoresToAttach.getScoresPK());
                attachedScoresCollection.add(scoresCollectionScoresToAttach);
            }
            player.setScoresCollection(attachedScoresCollection);
            em.persist(player);
            if (gameModeID != null) {
                gameModeID.getPlayerCollection().add(player);
                gameModeID = em.merge(gameModeID);
            }
            for (Scores scoresCollectionScores : player.getScoresCollection()) {
                Player oldPlayerIDOfScoresCollectionScores = scoresCollectionScores.getPlayerID();
                scoresCollectionScores.setPlayerID(player);
                scoresCollectionScores = em.merge(scoresCollectionScores);
                if (oldPlayerIDOfScoresCollectionScores != null) {
                    oldPlayerIDOfScoresCollectionScores.getScoresCollection().remove(scoresCollectionScores);
                    oldPlayerIDOfScoresCollectionScores = em.merge(oldPlayerIDOfScoresCollectionScores);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Player player) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Player persistentPlayer = em.find(Player.class, player.getPlayerID());
            Gamemode gameModeIDOld = persistentPlayer.getGameModeID();
            Gamemode gameModeIDNew = player.getGameModeID();
            Collection<Scores> scoresCollectionOld = persistentPlayer.getScoresCollection();
            Collection<Scores> scoresCollectionNew = player.getScoresCollection();
            List<String> illegalOrphanMessages = null;
            for (Scores scoresCollectionOldScores : scoresCollectionOld) {
                if (!scoresCollectionNew.contains(scoresCollectionOldScores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Scores " + scoresCollectionOldScores + " since its playerID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (gameModeIDNew != null) {
                gameModeIDNew = em.getReference(gameModeIDNew.getClass(), gameModeIDNew.getGameModeID());
                player.setGameModeID(gameModeIDNew);
            }
            Collection<Scores> attachedScoresCollectionNew = new ArrayList<Scores>();
            for (Scores scoresCollectionNewScoresToAttach : scoresCollectionNew) {
                scoresCollectionNewScoresToAttach = em.getReference(scoresCollectionNewScoresToAttach.getClass(), scoresCollectionNewScoresToAttach.getScoresPK());
                attachedScoresCollectionNew.add(scoresCollectionNewScoresToAttach);
            }
            scoresCollectionNew = attachedScoresCollectionNew;
            player.setScoresCollection(scoresCollectionNew);
            player = em.merge(player);
            if (gameModeIDOld != null && !gameModeIDOld.equals(gameModeIDNew)) {
                gameModeIDOld.getPlayerCollection().remove(player);
                gameModeIDOld = em.merge(gameModeIDOld);
            }
            if (gameModeIDNew != null && !gameModeIDNew.equals(gameModeIDOld)) {
                gameModeIDNew.getPlayerCollection().add(player);
                gameModeIDNew = em.merge(gameModeIDNew);
            }
            for (Scores scoresCollectionNewScores : scoresCollectionNew) {
                if (!scoresCollectionOld.contains(scoresCollectionNewScores)) {
                    Player oldPlayerIDOfScoresCollectionNewScores = scoresCollectionNewScores.getPlayerID();
                    scoresCollectionNewScores.setPlayerID(player);
                    scoresCollectionNewScores = em.merge(scoresCollectionNewScores);
                    if (oldPlayerIDOfScoresCollectionNewScores != null && !oldPlayerIDOfScoresCollectionNewScores.equals(player)) {
                        oldPlayerIDOfScoresCollectionNewScores.getScoresCollection().remove(scoresCollectionNewScores);
                        oldPlayerIDOfScoresCollectionNewScores = em.merge(oldPlayerIDOfScoresCollectionNewScores);
                    }
                }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Collection<Scores> scoresCollectionOrphanCheck = player.getScoresCollection();
            for (Scores scoresCollectionOrphanCheckScores : scoresCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Player (" + player + ") cannot be destroyed since the Scores " + scoresCollectionOrphanCheckScores + " in its scoresCollection field has a non-nullable playerID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Gamemode gameModeID = player.getGameModeID();
            if (gameModeID != null) {
                gameModeID.getPlayerCollection().remove(player);
                gameModeID = em.merge(gameModeID);
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
    
}
