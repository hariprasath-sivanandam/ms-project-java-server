package courseapp.repositories;

import org.springframework.data.repository.CrudRepository;

import courseapp.models.Course;

public interface CourseRepository extends CrudRepository<Course, Integer> {
	
}