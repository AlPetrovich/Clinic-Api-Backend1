package com.dh.clinica.persistence.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "dni", nullable = false)
    private String dni;

    @Column(name = "accessDate",nullable = false)
    private LocalDate accessDate;

    //------Relacion con turnos --- tenia lazy
    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Turn> turns = new HashSet<>();

    //------Relacion con Domicilio
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address", referencedColumnName = "id")
    private Address address;

    public Patient(String name, String lastname, String dni, LocalDate accessDate, Address address) {
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.accessDate = accessDate;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
