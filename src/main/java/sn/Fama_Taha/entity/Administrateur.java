package sn.Fama_Taha.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter 
@Setter @AllArgsConstructor @NoArgsConstructor 
@Table(name = "Administrateurs")

public class Administrateur {
    @Id
    @Column(name = "login",length = 50)
    private String login;
    @Column(name = "password", length = 100)
    private String password;
      public String getLogin() {
        return login;
    }
    
}
