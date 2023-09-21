/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakepredation.jpa_Model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author duyth
 */
@Entity
@Table(name = "player")
@NamedQueries({
    @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p"),
    @NamedQuery(name = "Player.findByPlayerID", query = "SELECT p FROM Player p WHERE p.playerID = :playerID"),
    @NamedQuery(name = "Player.findByPlayerName", query = "SELECT p FROM Player p WHERE p.playerName = :playerName")})
public class Player implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerID")
    private Collection<Scores> scoresCollection;
    @JoinColumn(name = "GameID", referencedColumnName = "GameID")
    @ManyToOne(optional = false)
    private Gamedetail gameID;
    @JoinColumn(name = "SnakeID", referencedColumnName = "SnakeID")
    @ManyToOne(optional = false)
    private Snake snakeID;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PlayerID")
    private Integer playerID;
    @Basic(optional = false)
    @Column(name = "PlayerName")
    private String playerName;
    @JoinColumn(name = "GameModeID", referencedColumnName = "GameModeID")
    @ManyToOne(optional = false)
    private Gamemode gameModeID;

    public Player() {
    }

    public Player(Integer playerID) {
        this.playerID = playerID;
    }

    public Player(Integer playerID, String playerName) {
        this.playerID = playerID;
        this.playerName = playerName;
    }

    public Integer getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Integer playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Gamemode getGameModeID() {
        return gameModeID;
    }

    public void setGameModeID(Gamemode gameModeID) {
        this.gameModeID = gameModeID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (playerID != null ? playerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.playerID == null && other.playerID != null) || (this.playerID != null && !this.playerID.equals(other.playerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "snakepredation.jpa_Model.Player[ playerID=" + playerID + " ]";
    }

    public Collection<Scores> getScoresCollection() {
        return scoresCollection;
    }

    public void setScoresCollection(Collection<Scores> scoresCollection) {
        this.scoresCollection = scoresCollection;
    }

    public Gamedetail getGameID() {
        return gameID;
    }

    public void setGameID(Gamedetail gameID) {
        this.gameID = gameID;
    }

    public Snake getSnakeID() {
        return snakeID;
    }

    public void setSnakeID(Snake snakeID) {
        this.snakeID = snakeID;
    }
    
}
