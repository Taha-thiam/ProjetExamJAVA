package sn.Fama_Taha.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "membre")
public class Membre {
    @Id
    @Column(name ="id_membre")
    private String nom;
    @Column(name = "nom",length = 100,nullable = false)
    private String prenom;
    @Column(name = "prenom",length = 100,nullable = false)
    private String email;
    private String telephone;
    private String typeMembre;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createur_login")
    private Administrateur createur;

}
