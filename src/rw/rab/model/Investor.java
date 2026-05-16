
package rw.rab.model;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @author nsumba
 */

public class Investor implements Serializable{
    private static final long serialVersionUID = 1L;
    private int investorId;
    private String fullName;
    private String phone;
    private double availableBalance;
  

    public Investor() {
    }

    public Investor(int investorId, String fullName, String phone, double availableBalance, User user, List<Sector> sectors, List<Funding> fundings) {
        this.investorId = investorId;
        this.fullName = fullName;
        this.phone = phone;
        this.availableBalance = availableBalance;
    }

        
    public int getInvestorId() {
        return investorId;
    }

    public void setInvestorId(int investorId) {
        this.investorId = investorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }
    
    
}
