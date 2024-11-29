package org.example.trainfaster.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "Maintance",schema = "public")
public class Maintance implements Serializable {



    @Id
    @SequenceGenerator(name = "maintenance_sequence", sequenceName = "maintenance_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "maintenance_sequence")
    private int Maintenance_ID;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    private Timestamp Maintenance_Date;

    private String Description;

    private String Status;
}
