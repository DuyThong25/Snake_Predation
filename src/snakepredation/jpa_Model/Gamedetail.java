/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakepredation.jpa_Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author duyth
 */
@Entity
@Table(name = "gamedetail")
@NamedQueries({
    @NamedQuery(name = "Gamedetail.findAll", query = "SELECT g FROM Gamedetail g"),
    @NamedQuery(name = "Gamedetail.findByGameID", query = "SELECT g FROM Gamedetail g WHERE g.gamedetailPK.gameID = :gameID"),
    @NamedQuery(name = "Gamedetail.findByGameDate", query = "SELECT g FROM Gamedetail g WHERE g.gamedetailPK.gameDate = :gameDate"),
    @NamedQuery(name = "Gamedetail.findByPlayerID", query = "SELECT g FROM Gamedetail g WHERE g.gamedetailPK.playerID = :playerID"),
    @NamedQuery(name = "Gamedetail.findByGameName", query = "SELECT g FROM Gamedetail g WHERE g.gameName = :gameName")})
public class Gamedetail implements Serializable {

    @JoinColumn(name = "GameModeID", referencedColumnName = "GameModeID")
    @ManyToOne
    private Gamemode gameModeID;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GamedetailPK gamedetailPK;
    @Basic(optional = false)
    @Column(name = "GameName")
    private String gameName;
    @JoinColumn(name = "PlayerID", referencedColumnName = "PlayerID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Player player;

    public Gamedetail() {
    }

    public Gamedetail(GamedetailPK gamedetailPK) {
        this.gamedetailPK = gamedetailPK;
    }

    public Gamedetail(GamedetailPK gamedetailPK, String gameName) {
        this.gamedetailPK = gamedetailPK;
        this.gameName = gameName;
    }

    public Gamedetail(int gameID, Date gameDate, int playerID) {
        this.gamedetailPK = new GamedetailPK(gameID, gameDate, playerID);
    }

    public GamedetailPK getGamedetailPK() {
        return gamedetailPK;
    }

    public void setGamedetailPK(GamedetailPK gamedetailPK) {
        this.gamedetailPK = gamedetailPK;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gamedetailPK != null ? gamedetailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gamedetail)) {
            return false;
        }
        Gamedetail other = (Gamedetail) object;
        if ((this.gamedetailPK == null && other.gamedetailPK != null) || (this.gamedetailPK != null && !this.gamedetailPK.equals(other.gamedetailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "snakepredation.jpa_Model.Gamedetail[ gamedetailPK=" + gamedetailPK + " ]";
    }

    public Gamemode getGameModeID() {
        return gameModeID;
    }

    public void setGameModeID(Gamemode gameModeID) {
        this.gameModeID = gameModeID;
    }
    
}
