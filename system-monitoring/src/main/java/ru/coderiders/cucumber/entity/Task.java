package ru.coderiders.cucumber.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.coderiders.cucumber.enums.TaskStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Сущность работы
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */

@Entity
@Getter
@Setter
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_users", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_field", referencedColumnName = "id")
    private Field field;

    @Column(name = "type")
    private String type;

    @Column(name = "start_time")
    private LocalDateTime startTime = LocalDateTime.now();

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

}
