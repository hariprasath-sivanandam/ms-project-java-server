package courseapp.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;

import courseapp.models.Course;
import courseapp.models.Lesson;
import courseapp.models.Module;
import courseapp.models.User;
import courseapp.models.Widget;
import courseapp.repositories.LessonRepository;
import courseapp.repositories.WidgetRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class WidgetService {
	@Autowired
	WidgetRepository widgetRepository;
	
	@Autowired
	LessonRepository lessonRepository;
	
	@GetMapping("/api/widget")
	public List<Widget> findAllCourses() {
		return (List<Widget>) widgetRepository.findAll(); 
	}
	
	@GetMapping("/api/widget/{widgetId}")
	public Widget findWidgetById(@PathVariable("widgetId") int widgetId) {
		Optional<Widget> data = widgetRepository.findById(widgetId);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@GetMapping("/api/lesson/{lessonId}/widget")
	public List<Widget> findAllWidgetsForLesson(
			@PathVariable("lessonId") int lessonId) {
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if(data.isPresent()) {
			Lesson lesson = data.get();
			return lesson.getWidgets();
		}
		return null;		
	}
	
	@PostMapping("/api/lesson/{lessonId}/widget")
	public Widget createWidget(
			@PathVariable("lessonId") int lessonId,
			@RequestBody Widget newWidget) {
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		
		if(data.isPresent()) {
			Lesson lesson = data.get();
			newWidget.setLesson(lesson);
			return widgetRepository.save(newWidget);

		}
		return null;		
	}
	
	
	@PostMapping("/api/lesson/{lessonId}/widgets")
	public List<Widget> createWidgets(
			@PathVariable("lessonId") int lessonId,
			@RequestBody List<Widget> newWidgets) {
		widgetRepository.deleteWidgetsByLessonId(lessonId);
		List<Widget> output = new ArrayList<Widget>();
	for(Widget w : newWidgets) {
		output.add(createWidget(lessonId,w));
	}
		return output;
	}
	
	
	
	@PutMapping("/api/widget/{widgetId}")
	public Widget updateWidget(
			@PathVariable("widgetId") int widgetId,
			@RequestBody Widget newWidget) {
		Optional<Widget> data = widgetRepository.findById(widgetId);
		if(data.isPresent()) {
			Widget widget = data.get();
			widget.setName(newWidget.getName());
			widget.setWidgetOrder(newWidget.getWidgetOrder());
			widget.setText(newWidget.getText());
			widget.setWidgetType(newWidget.getWidgetType());
			widget.setStyle(newWidget.getStyle());
			widget.setWidth(newWidget.getWidth());
			widget.setHeight(newWidget.getHeight());
			widget.setSize(newWidget.getSize());
			widget.setHref(newWidget.getHref());
			widget.setSrc(newWidget.getSrc());
			widget.setWidth(newWidget.getWidth());
			widget.setListType(newWidget.getListType());
			widgetRepository.save(widget);
			return widget;
		}
		return null;		
	}

	@DeleteMapping("/api/widget/{widgetId}")
	public void deleteWidget(@PathVariable("widgetId") int widgetId)
	{	
		widgetRepository.deleteById(widgetId);
	}
	
	
}
