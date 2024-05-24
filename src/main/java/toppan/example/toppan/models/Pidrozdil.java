package toppan.example.toppan.models;

import lombok.extern.slf4j.Slf4j;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Slf4j   // Логер
@Entity
public class Pidrozdil {
    @Id
    private String pidrozdil;

    @OneToMany(mappedBy = "pidrozdil")
    private List<Rubin_week> rubin_weeks;

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
