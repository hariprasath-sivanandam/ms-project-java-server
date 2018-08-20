package courseapp.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import courseapp.models.User;
import courseapp.models.Widget;

public interface WidgetRepository extends CrudRepository<Widget, Integer>{

	@Transactional
	@Modifying
	@Query("DELETE FROM Widget w WHERE w.lesson.id=:lessonId")
	void deleteWidgetsByLessonId(
		@Param("lessonId") int lessonId);
}