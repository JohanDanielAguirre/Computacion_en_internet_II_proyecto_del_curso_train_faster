package org.example.trainfaster.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "Train",schema = "public")
public class Train implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_id", nullable = false)
    private int id;

    private String model;

    @ManyToMany
    @JoinTable(
            name = "train_trip",
            schema = "public",
            joinColumns = @JoinColumn(name = "train_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id")
    )
    private List<Trip> trips;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Maintance> maintenances;

}
