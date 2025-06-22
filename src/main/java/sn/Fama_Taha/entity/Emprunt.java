package sn.Fama_Taha.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "emprunt")
public class Emprunt {
    @Id
    @Column(name = "id_emprunt")
    private String idEmprunt;

    @Column(name = "date_emprunt", nullable = false)
    private LocalDate dateEmprunt;

    @Column(name = "date_retour_prevue", nullable = false)
    private LocalDate dateRetourPrevue;

    @Column(name = "date_retour_reelle")
    private LocalDate dateRetourReelle;

    @ManyToOne
    @JoinColumn(name = "id_membre", nullable = false)
    private Membre membre;

    // Exemple : si vous avez une entité Livre
    // @ManyToOne
    // @JoinColumn(name = "id_livre", nullable = false)
    // private Livre livre;
}
