package spb.iac.service;

import org.springframework.stereotype.Service;
import spb.iac.model.Directory;
import spb.iac.model.DirectoryDB;
import spb.iac.model.SimpleComparator;
import spb.iac.model.SubDirectory;
import spb.iac.repository.DirectoryRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DirectoryService {
    private final DirectoryRepository repository;

    public DirectoryService(DirectoryRepository repository) {
        this.repository = repository;
    }

    public List<Directory> getAllDirectory() {
        List<Directory> list = new ArrayList<>();
        repository.findAll().forEach(dirDB -> {
                list.add(new Directory(dirDB,
                        getCountOfDir(dirDB.getPaths()),
                        getCountOfFile(dirDB.getPaths()),
                        humanReadableByteCount(getSumByDirectory(Paths.get(dirDB.getPath())), false)
                ));
        });
        return list;
    }

    public List<SubDirectory> getSubDirById(int id) {
        List<SubDirectory> list = repository.findById(id).get().getPaths();
        list.sort(new SimpleComparator());
        return list;
    }

    public void save(String path) {
        List<SubDirectory> list = getInfoByPath(path);
        DirectoryDB dir = new DirectoryDB(path, list);
        repository.save(dir);
    }

    // Метод проходится по основной директории и возвращает лист с первым слоем поддиректорий и файлов
    private List<SubDirectory> getInfoByPath(String path) {
        List<SubDirectory> list = new ArrayList<>();
        Path ourPath = Paths.get(path);
        if (Files.isDirectory(ourPath)) {
            try {
                final List<Path> collect = Files.list(ourPath).collect(Collectors.toList());

                for(Path p : collect) {
                    if (Files.isDirectory(p)) {
                        list.add(new SubDirectory(p.toString(), "DIR"));
                    } else if (Files.isRegularFile(p)) {
                        list.add(new SubDirectory(p.toString(), humanReadableByteCount(getSumByDirectory(p),false)));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // Считаем количество поддиректорий
    private int getCountOfDir(List<SubDirectory> list) {
        int result = 0;
        for(SubDirectory sd : list) {
            Path path = Paths.get(sd.getPath());
            if (Files.isDirectory(path)) {
                result++;
            }
        }
        return result;
    }

    // Считаем количество файлов
    private int getCountOfFile(List<SubDirectory> list) {
        int result = 0;
        for(SubDirectory sd : list) {
            Path path = Paths.get(sd.getPath());
            if (Files.isRegularFile(path)) {
                result++;
            }
        }
        return result;
    }

    // Получаем корректную строку, как у нас будет отображаться размер файла
    public String humanReadableByteCount (long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) {
            return bytes + " b";
        }
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + "";
        return String.format("%.1f %sb", bytes / Math.pow(unit, exp), pre);
    }

    // Получаем размер всех поддиректорий и файлов
    private long getSumByDirectory(Path path) {
        long result = 0;
        try {
            result =Files.walk(path)
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .mapToLong(File::length)
                    .sum();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
