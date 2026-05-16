
package rw.rab.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author nsumba
 */

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private int userId;
    private String username;
    private String password;
    private String email;
    private String role;
    private String otpCode;
    private Date otpExpiry;
    
    public User() {
    }

    public User(int UserId, String username, String password, String email, String role, String otpCode, Date otpExpiry, Sme sme, Investor investor) {
        this.userId = UserId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.otpCode = otpCode;
    }
    

    public int getUserId() {
        return userId;
    }

    public void setUserId(int UserId) {
        this.userId = UserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public Date getOtpExpiry() {
        return otpExpiry;
    }

    public void setOtpExpiry(Date otpExpiry) {
        this.otpExpiry = otpExpiry;
    }
    
    
}
