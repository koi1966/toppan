package toppan.example.toppan.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pidrozdil {
    @Id
    private String pidrozdil;

    private String ip, email;

    public Pidrozdil(String pidrozdil, String ip) {
        this.pidrozdil = pidrozdil;
        this.ip = ip;
    }

    public String getPidrozdil() {
        return pidrozdil;
    }

    public void setPidrozdil(String pidrozdil) {
        this.pidrozdil = pidrozdil;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Pidrozdil() {
    }
}
