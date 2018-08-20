package courseapp.repositories;

import org.springframework.data.repository.CrudRepository;

import courseapp.models.Lesson;

public interface LessonRepository extends CrudRepository<Lesson, Integer> {

}