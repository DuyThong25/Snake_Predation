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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author duyth
 */
@Entity
@Table(name = "gamemode")
@NamedQueries({
    @NamedQuery(name = "Gamemode.findAll", query = "SELECT g FROM Gamemode g"),
    @NamedQuery(name = "Gamemode.findByGameModeID", query = "SELECT g FROM Gamemode g WHERE g.gameModeID = :gameModeID"),
    @NamedQuery(name = "Gamemode.findByGameModeName", query = "SELECT g FROM Gamemode g WHERE g.gameModeName = :gameModeName")})
public class Gamemode implements Serializable {

    @OneToMany(mappedBy = "gameModeID")
    private Collection<Gamedetail> gamedetailCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "GameModeID")
    private Integer gameModeID;
    @Basic(optional = false)
    @Column(name = "GameModeName")
    private String gameModeName;

    public Gamemode() {
    }

    public Gamemode(Integer gameModeID) {
        this.gameModeID = gameModeID;
    }

    public Gamemode(Integer gameModeID, String gameModeName) {
        this.gameModeID = gameModeID;
        this.gameModeName = gameModeName;
    }

    public Integer getGameModeID() {
        return gameModeID;
    }

    public void setGameModeID(Integer gameModeID) {
        this.gameModeID = gameModeID;
    }

    public String getGameModeName() {
        return gameModeName;
    }

    public void setGameModeName(String gameModeName) {
        this.gameModeName = gameModeName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameModeID != null ? gameModeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gamemode)) {
            return false;
        }
        Gamemode other = (Gamemode) object;
        if ((this.gameModeID == null && other.gameModeID != null) || (this.gameModeID != null && !this.gameModeID.equals(other.gameModeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "snakepredation.Model.Gamemode[ gameModeID=" + gameModeID + " ]";
    }

    public Collection<Gamedetail> getGamedetailCollection() {
        return gamedetailCollection;
    }

    public void setGamedetailCollection(Collection<Gamedetail> gamedetailCollection) {
        this.gamedetailCollection = gamedetailCollection;
    }
    
}
