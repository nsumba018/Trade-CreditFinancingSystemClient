
package rw.rab.model;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @author nsumba
 */

public class Sector implements Serializable{
    private static final long serialVersionUID = 1L;
    private int sectoId;
    private String sectorName;
    private String description;
    
    
    public Sector() {
    }

    public Sector(int sectoId, String sectorName, String description, List<Investor> investors) {
        this.sectoId = sectoId;
        this.sectorName = sectorName;
        this.description = description;
    }    

    public int getSectoId() {
        return sectoId;
    }

    public void setSectoId(int sectoId) {
        this.sectoId = sectoId;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
