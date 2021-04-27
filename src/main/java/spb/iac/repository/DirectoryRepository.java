package spb.iac.repository;

import org.springframework.data.repository.CrudRepository;
import spb.iac.model.DirectoryDB;

public interface DirectoryRepository extends CrudRepository<DirectoryDB, Integer> {
}
