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

@Embeddable
public class ScoresPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ScoresID")
    private int scoresID;
    @Basic(optional = false)
    @Column(name = "ScoresDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scoresDate;

    public ScoresPK() {
    }

    public ScoresPK(int scoresID, Date scoresDate) {
        this.scoresID = scoresID;
        this.scoresDate = scoresDate;
    }

    public int getScoresID() {
        return scoresID;
    }

    public void setScoresID(int scoresID) {
        this.scoresID = scoresID;
    }

    public Date getScoresDate() {
        return scoresDate;
    }

    public void setScoresDate(Date scoresDate) {
        this.scoresDate = scoresDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) scoresID;
        hash += (scoresDate != null ? scoresDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScoresPK)) {
            return false;
        }
        ScoresPK other = (ScoresPK) object;
        if (this.scoresID != other.scoresID) {
            return false;
        }
        if ((this.scoresDate == null && other.scoresDate != null) || (this.scoresDate != null && !this.scoresDate.equals(other.scoresDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "snakepredation.Model.ScoresPK[ scoresID=" + scoresID + ", scoresDate=" + scoresDate + " ]";
    }
    
}
