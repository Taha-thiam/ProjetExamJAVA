package sn.Fama_Taha.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "amende")
public class Amende {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_amende")
    private Integer idAmende;

    @Column(name = "montant", nullable = false)
    private double montant;

    @Column(name = "date_amende", nullable = false)
    private LocalDate dateAmende;

    @Column(name = "raison")
    private String raison;

    @ManyToOne
    @JoinColumn(name = "id_membre", nullable = false)
    private Membre membre;
}