
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


@Entity
@Table(name = "scores")
@NamedQueries({
    @NamedQuery(name = "Scores.findAll", query = "SELECT s FROM Scores s"),
    @NamedQuery(name = "Scores.findByScoresID", query = "SELECT s FROM Scores s WHERE s.scoresPK.scoresID = :scoresID"),
    @NamedQuery(name = "Scores.findByScoresDate", query = "SELECT s FROM Scores s WHERE s.scoresPK.scoresDate = :scoresDate"),
    @NamedQuery(name = "Scores.findByTotalScores", query = "SELECT s FROM Scores s WHERE s.totalScores = :totalScores")})
public class Scores implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ScoresPK scoresPK;
    @Basic(optional = false)
    @Column(name = "TotalScores")
    private int totalScores;
    @JoinColumn(name = "PlayerID", referencedColumnName = "PlayerID")
    @ManyToOne(optional = false)
    private Player playerID;

    public Scores() {
    }

    public Scores(ScoresPK scoresPK) {
        this.scoresPK = scoresPK;
    }

    public Scores(ScoresPK scoresPK, int totalScores) {
        this.scoresPK = scoresPK;
        this.totalScores = totalScores;
    }

    public Scores(int scoresID, Date scoresDate) {
        this.scoresPK = new ScoresPK(scoresID, scoresDate);
    }

    public ScoresPK getScoresPK() {
        return scoresPK;
    }

    public void setScoresPK(ScoresPK scoresPK) {
        this.scoresPK = scoresPK;
    }

    public int getTotalScores() {
        return totalScores;
    }

    public void setTotalScores(int totalScores) {
        this.totalScores = totalScores;
    }

    public Player getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Player playerID) {
        this.playerID = playerID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scoresPK != null ? scoresPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Scores)) {
            return false;
        }
        Scores other = (Scores) object;
        if ((this.scoresPK == null && other.scoresPK != null) || (this.scoresPK != null && !this.scoresPK.equals(other.scoresPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "snakepredation.Model.Scores[ scoresPK=" + scoresPK + " ]";
    }
    
}
