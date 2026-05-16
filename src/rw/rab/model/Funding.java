
package rw.rab.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author nsumba
 */

public class Funding implements Serializable{
    private static final long serialVersionUID = 1L;
    private int fundingId;
    private double fundedAmount;
    private Date fundedDate;
    

    public Funding() {
    }

    public Funding(int fundingId, double fundedAmount, Date fundedDate, Invoice invoice, Investor investor) {
        this.fundingId = fundingId;
        this.fundedAmount = fundedAmount;
        this.fundedDate = fundedDate;
    }
    

    public int getFundingId() {
        return fundingId;
    }

    public void setFundingId(int fundingId) {
        this.fundingId = fundingId;
    }

    public double getFundedAmount() {
        return fundedAmount;
    }

    public void setFundedAmount(double fundedAmount) {
        this.fundedAmount = fundedAmount;
    }

    public Date getFundedDate() {
        return fundedDate;
    }

    public void setFundedDate(Date fundedDate) {
        this.fundedDate = fundedDate;
    }
    
    
    
}
