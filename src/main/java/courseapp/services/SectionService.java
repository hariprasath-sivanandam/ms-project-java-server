package courseapp.services;

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
public class SectionService {
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	SectionRepository sectionRepository;
	@Autowired
	CourseRepository courseRepository;

	@DeleteMapping("/api/section/{sectionId}")
	public void deleteSection(@PathVariable("sectionId") int sectionId) {
		sectionRepository.deleteById(sectionId);
	}
//
//	@PostMapping("/api/course/{courseId}/section")
//	public Section createSection(@PathVariable("courseId") int courseId, @RequestBody Section section) {
//		Optional<Course> data = courseRepository.findById(courseId);
//		if (data.isPresent()) {
//			section.setCourse(data.get());
//			return sectionRepository.save(section);
//		}
//		return null;
//
//	}

	@PutMapping("/api/section/{sectionId}")
	public Section updateSection(@PathVariable("sectionId") int sectionId, @RequestBody Section section) {
		Optional<Section> data = sectionRepository.findById(sectionId);
		if (data.isPresent()) {
			section.setId(sectionId);
			return sectionRepository.save(section);
		}
		return null;
	}

	@DeleteMapping("/api/section/{sectionId}/enrollment")
	public Section unrollStudentInSection(@PathVariable("sectionId") int sectionId, HttpSession session) {
		Optional<Section> data = sectionRepository.findById(sectionId);
		if (data.isPresent()) {
			Section section = data.get();
			Student student = (Student) session.getAttribute("current_user");
			if (student != null) {
				section.setSeats(Math.min(section.getSeats() + 1, section.getMaxseats()));
				//section.getEnrolledStudents().remove(student);
				student.unrollSection(section);
				studentRepository.save(student);
				return sectionRepository.save(section);
			}
		}
		return null;
	}

	@GetMapping("/api/course/{courseId}/section")
	public List<Section> findSectionsForCourse(@PathVariable("courseId") int courseId) {
		Optional<Course> data = courseRepository.findById(courseId);
		if (data.isPresent()) {
			return data.get().getSections();
		}
		return null;

	}

	@PostMapping("/api/section/{sectionId}/enrollment")
	public Section enrollStudentInSection(@PathVariable("sectionId") int sectionId, HttpSession session) {
		Optional<Section> data = sectionRepository.findById(sectionId);
		if (data.isPresent()) {
			Student student = (Student) session.getAttribute("current_user");
			if (student != null) {
				Section section = data.get();
				section.setSeats(Math.max(section.getSeats() - 1, 0));
				//section.enrollStudent(student);
				student.enrollSection(section);
				studentRepository.save(student);
				return sectionRepository.save(section);
			}
		}
		return null;
	}

	@PostMapping("/api/section/{sectionId}/enrollment/{studentId}")
	public Section enrollStudentInSectionTest(@PathVariable("sectionId") int sectionId,
			@PathVariable("studentId") int studentId, HttpSession session) {
		Optional<Section> data = sectionRepository.findById(sectionId);
		if (data.isPresent()) {
			// Student student = (Student) session.getAttribute("current_user");
			Optional<Student> student = studentRepository.findById(studentId);
			if (student.isPresent()) {
				Section section = data.get();
				section.setSeats(Math.max(section.getSeats() - 1, 0));
				// section.enrollStudent(student.get());
				Student stud = student.get();
				stud.enrollSection(section);
				studentRepository.save(stud);
				return sectionRepository.save(section);
			}
		}
		return null;

	}

	@GetMapping("/api/student/section")
	public List<Section> findSectionsForStudent(HttpSession session) {
		Student student = (Student) session.getAttribute("current_user");
		if (student != null) {
			return student.getEnrolledSections();
		}
		return null;
	}
}
