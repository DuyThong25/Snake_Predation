/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakepredation.jpa_Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author duyth
 */
@Embeddable
public class GamedetailPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "GameID")
    private int gameID;
    @Basic(optional = false)
    @Column(name = "GameDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gameDate;
    @Basic(optional = false)
    @Column(name = "PlayerID")
    private int playerID;

    public GamedetailPK() {
    }

    public GamedetailPK(int gameID, Date gameDate, int playerID) {
        this.gameID = gameID;
        this.gameDate = gameDate;
        this.playerID = playerID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) gameID;
        hash += (gameDate != null ? gameDate.hashCode() : 0);
        hash += (int) playerID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GamedetailPK)) {
            return false;
        }
        GamedetailPK other = (GamedetailPK) object;
        if (this.gameID != other.gameID) {
            return false;
        }
        if ((this.gameDate == null && other.gameDate != null) || (this.gameDate != null && !this.gameDate.equals(other.gameDate))) {
            return false;
        }
        if (this.playerID != other.playerID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "snakepredation.jpa_Model.GamedetailPK[ gameID=" + gameID + ", gameDate=" + gameDate + ", playerID=" + playerID + " ]";
    }
    
}
