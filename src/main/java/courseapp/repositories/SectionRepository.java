package courseapp.repositories;

import org.springframework.data.repository.CrudRepository;
import courseapp.models.Section;

public interface SectionRepository extends CrudRepository<Section, Integer> {

}