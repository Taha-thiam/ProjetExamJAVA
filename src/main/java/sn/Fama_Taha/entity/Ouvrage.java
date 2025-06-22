
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
@Table(name = "ouvrage")
public class Ouvrage {
    @Id
    @Column(name = "id_ouvrage")
    private String idOuvrage;

    @Column(name = "titre", nullable = false, length = 200)
    private String titre;

    @Column(name = "auteur", nullable = false, length = 100)
    private String auteur;

    @Column(name = "annee_publication")
    private int anneePublication;

    @Column(name = "genre", length = 50)
    private String genre;

    @Column(name = "disponible")
    private boolean disponible;
}