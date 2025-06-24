package sn.Fama_Taha.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "membre")
public class Membre {
    @Id
    @Column(name = "id_membre")
    private String idMembre;

    @Column(name = "nom", length = 100, nullable = false)
    private String nom;

    @Column(name = "prenom", length = 100, nullable = false)
    private String prenom;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    private String telephone;
    private String typeMembre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createur_login")
    private Administrateur createur;
}