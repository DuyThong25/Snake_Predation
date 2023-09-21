
package snakepredation.jpa_Model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "gamedetail")
@NamedQueries({
    @NamedQuery(name = "Gamedetail.findAll", query = "SELECT g FROM Gamedetail g"),
    @NamedQuery(name = "Gamedetail.findByGameID", query = "SELECT g FROM Gamedetail g WHERE g.gameID = :gameID"),
    @NamedQuery(name = "Gamedetail.findByGameName", query = "SELECT g FROM Gamedetail g WHERE g.gameName = :gameName")})
public class Gamedetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GameID")
    private Integer gameID;
    @Column(name = "GameName")
    private String gameName;
    @JoinColumn(name = "GameModeID", referencedColumnName = "GameModeID")
    @ManyToOne
    private Gamemode gameModeID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gameID")
    private Collection<Player> playerCollection;

    public Gamedetail() {
    }

    public Gamedetail(Integer gameID) {
        this.gameID = gameID;
    }

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Gamemode getGameModeID() {
        return gameModeID;
    }

    public void setGameModeID(Gamemode gameModeID) {
        this.gameModeID = gameModeID;
    }

    public Collection<Player> getPlayerCollection() {
        return playerCollection;
    }

    public void setPlayerCollection(Collection<Player> playerCollection) {
        this.playerCollection = playerCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameID != null ? gameID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gamedetail)) {
            return false;
        }
        Gamedetail other = (Gamedetail) object;
        if ((this.gameID == null && other.gameID != null) || (this.gameID != null && !this.gameID.equals(other.gameID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "snakepredation.jpa_Model.Gamedetail[ gameID=" + gameID + " ]";
    }
    
}
