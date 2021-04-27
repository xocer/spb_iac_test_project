package spb.iac.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
// Класс необходим только для взаимодействия с Hibernate
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "directories")
public class DirectoryDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime date;
    private String path;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SubDirectory> paths;

    public DirectoryDB(String path, List<SubDirectory> paths) {
        this.date = LocalDateTime.now();
        this.path = path;
        this.paths = paths;
    }
}
