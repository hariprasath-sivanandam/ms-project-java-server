package courseapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import courseapp.models.Module;
import courseapp.models.Lesson;
import courseapp.repositories.ModuleRepository;
import courseapp.repositories.LessonRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonService {
	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	LessonRepository lessonRepository;
	
	@PostMapping("/api/module/{moduleId}/lesson")
	public Lesson createlesson(
			@PathVariable("moduleId") int moduleId,
			@RequestBody Lesson newlesson) {
		Optional<Module> data = moduleRepository.findById(moduleId);
		
		if(data.isPresent()) {
			Module module = data.get();
			newlesson.setModule(module);
			return lessonRepository.save(newlesson);
		}
		return null;		
	}
	
	@GetMapping("/api/module/{moduleId}/lesson")
	public List<Lesson> findAllLessonsForModule(
			@PathVariable("moduleId") int moduleId) {
		Optional<Module> data = moduleRepository.findById(moduleId);
		if(data.isPresent()) {
			Module module = data.get();
			return module.getLessons();
		}
		return null;		
	}
	
	@DeleteMapping("/api/lesson/{lessonId}")
	public void deleteLesson(@PathVariable("lessonId") int lessonId)
	{
		lessonRepository.deleteById(lessonId);
	}
	
	@GetMapping("/api/lesson")
	public List<Lesson> findAllLessons()
	{
		return (List<Lesson>) lessonRepository.findAll();
	}
}