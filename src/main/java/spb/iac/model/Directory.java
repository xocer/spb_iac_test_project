package spb.iac.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Этот класс отвечает за отображение основной директории
@Data
public class Directory {
    private int id;
    private LocalDateTime date;
    private String path;
    private List<SubDirectory> paths;
    private int subdirectories;
    private int files;
    private String size;

    public Directory(DirectoryDB directoryDB, int subdirectories, int files, String size)  {
        this.id = directoryDB.getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.date = directoryDB.getDate();
        this.path = directoryDB.getPath();
        this.paths = directoryDB.getPaths();
        this.subdirectories = subdirectories;
        this.files = files;
        this.size = size;
    }
}
