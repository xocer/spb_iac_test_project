package spb.iac.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//Класс используется для хранения информации о поддиректориях и файлах из основной директории
@NoArgsConstructor
@Data
@Entity
@Table(name = "sub_directories")
public class SubDirectory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String path;
    private String size;

    public SubDirectory(String path, String size) {
        this.path = path;
        this.size = size;
    }
}
