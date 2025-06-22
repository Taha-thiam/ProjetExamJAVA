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
@Table(name = "reservation")
public class Reservation {
    @Id
    @Column(name = "id_reservation")
    private String idReservation;

    @Column(name = "date_reservation", nullable = false)
    private LocalDate dateReservation;

    @ManyToOne
    @JoinColumn(name = "id_membre", nullable = false)
    private Membre membre;

    @ManyToOne
    @JoinColumn(name = "id_ouvrage", nullable = false)
    private Ouvrage ouvrage;
}