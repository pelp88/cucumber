package ru.coderiders.cucumber.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.coderiders.cucumber.enums.RegularOperation;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "operations")
@AllArgsConstructor
@NoArgsConstructor
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_plant", referencedColumnName = "id")
    private Plant plantId;

    @Column(name = "operation_name")
    @Enumerated(EnumType.STRING)
    private RegularOperation operationName;

    @Column(name = "work_time")
    private LocalDate procDate;

    @Column(name = "interval")
    private Long interval;  // пусть задается в днях

}
