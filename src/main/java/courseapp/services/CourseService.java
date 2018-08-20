package courseapp.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import courseapp.models.Course;
import courseapp.models.Section;
import courseapp.models.Student;
import courseapp.repositories.CourseRepository;
import courseapp.repositories.SectionRepository;
import courseapp.repositories.StudentRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseService {
	@Autowired
	CourseRepository courseRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	SectionRepository sectionRepository;

	@GetMapping("/api/course/all")
	public Iterable<Course> findAllCourses() {
		return courseRepository.findAll();
	}

	@GetMapping("/api/course/{courseId}")
	public Course findCourseById(@PathVariable("courseId") int courseId) {
		Optional<Course> data = courseRepository.findById(courseId);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}

	@PostMapping("/api/course")
	public Course createCourse(@RequestBody Course course) {
		Date presentDate = new Date();
		course.setCreated(presentDate);
		course.setModified(presentDate);
		return courseRepository.save(course);
	}

	@PutMapping("/api/course/{courseId}/section")
	public void updateSection(@PathVariable("courseId") int courseId, @RequestBody Section newSection) {
		Optional<Course> data = courseRepository.findById(courseId);
		if (data.isPresent()) {
			Course course = data.get();
			for (Section section : course.getSections()) {
				if (section.getId() == newSection.getId()) {
					section.setMaxseats(newSection.getMaxseats());
					section.setName(newSection.getName());
				}
			}
			courseRepository.save(course);
		}
	}

	@PostMapping("/api/course/{courseId}/section")
	public Section addSection(@PathVariable("courseId") int courseId, @RequestBody Section newSection) {
		Optional<Course> data = courseRepository.findById(courseId);
		Section savedSection = null;
		if (data.isPresent()) {
			Course course = data.get();

			// newSection.setId(-1);
			newSection.setSeats(newSection.getMaxseats());
			savedSection = sectionRepository.save(newSection);

			course.addsection(newSection);
			courseRepository.save(course);
		}
		return savedSection;
	}

	@PostMapping("/api/course/courseType")
	public void updateCourseType(@RequestBody List<Course> courses) {
		// Good way is to check whether the logged in user is admin from session
		courseRepository.saveAll(courses);
	}

	@DeleteMapping("/api/course/{courseId}")
	public void deleteCourse(@PathVariable("courseId") int id) {
		courseRepository.deleteById(id);
	}

	@GetMapping("/api/course")
	public Iterable<Course> findCourses(HttpSession httpsession) {
		List<Course> allCourses = (List<Course>) courseRepository.findAll();
		List<Course> courses = new ArrayList<Course>();
		Student student = (Student) httpsession.getAttribute("current_user");
		for (Course course : allCourses) {
			if (student == null) {
				if (course.getCourseType().equals("public")) {
					courses.add(course);
				}
			} else {
				courses.add(course);
			}
		}
		return courses;
	}

	@GetMapping("/api/course/student")
	public Course[] findStudentCourses(HttpSession session) {
		// Optional<Student> data = studentRepository.findById(studentId);
		Student student = (Student) session.getAttribute("current_user");
		if (student != null) {
			// Student student = data.get();
			List<Section> sections = (List<Section>) student.getEnrolledSections();
			List<Course> courses = (List<Course>) findAllCourses();
			HashSet<Course> res = new HashSet<Course>();
			
			if(sections!= null  && sections.size()>0) {
				for (Section section : sections) {
					for (Course course : courses) {
						if (course.getSections().stream().anyMatch(t -> t.getId() == (section.getId()))) {
							res.add(course);
						}
					}
				}
			}
			
			Course c[] = new Course[res.size()];
			int i = 0;
			for (Object o : res) {
				c[i++] = (Course) o;
			}
			return c;
		}
		return null;
	}
}