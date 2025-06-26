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

    @ManyToOne
    @JoinColumn(name = "id_membre", nullable = false)
    private Membre membre;

    @ManyToOne
    @JoinColumn(name = "id_ouvrage", nullable = false)
    private Ouvrage ouvrage;

    @Column(name = "retour_emprunt", nullable = false)
    private boolean retourEmprunt;

    @Column(name = "date_retour_reel")
    private LocalDate dateRetourReel;

    public void setDateRetourReel(LocalDate dateRetourReel) {
        this.dateRetourReel = dateRetourReel;
    }
}
