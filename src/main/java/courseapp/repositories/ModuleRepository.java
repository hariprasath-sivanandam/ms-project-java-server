package courseapp.repositories;

import org.springframework.data.repository.CrudRepository;

import courseapp.models.Module;

public interface ModuleRepository extends CrudRepository<Module, Integer>{

}