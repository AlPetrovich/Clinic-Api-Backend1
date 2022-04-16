package com.dh.clinica.persistence.entities.auth;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 60, nullable = false)
    private String name;

    public Role() {
    }


}
