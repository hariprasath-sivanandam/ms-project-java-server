package courseapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import javax.servlet.http.HttpSession;

import courseapp.models.Student;
import courseapp.repositories.StudentRepository;
import courseapp.exceptions.UsernameConflictException;
import courseapp.exceptions.InvalidLoginException;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentService {
	@Autowired
	StudentRepository studentRepository;

	@DeleteMapping("/api/student/{studentId}")
	public void deleteStudent(@PathVariable("studentId") int id) {
		studentRepository.deleteById(id);
	}

	@PostMapping("/api/student")
	public Student createStudent(@RequestBody Student student) {
		return studentRepository.save(student);
	}


	@GetMapping("/api/student")
	public List<Student> findAllStudents() {
		return (List<Student>) studentRepository.findAll();
	}

	@PutMapping("/api/student/profile")
	public Student updateProfile(@RequestBody Student newStudent, HttpSession session) {
		Optional<Student> data = studentRepository.findStudentByUsername(newStudent.getUsername());
		if(data.isPresent()) {
			Student student = data.get();
			student.setUsername(newStudent.getUsername());
			student.setPassword(newStudent.getPassword());
			student.setFirstName(newStudent.getFirstName());
			student.setLastName(newStudent.getLastName());
			student.setEmail(newStudent.getEmail());
			student.setPhone(newStudent.getPhone());
			studentRepository.save(student);
			session.setAttribute("current_user", student);
			return student;
		}
		return null;		
	}

	@GetMapping("/api/student/{studentId}")
	public Student findStudentById(@PathVariable("studentId") int studentId) {
		Optional<Student> data = studentRepository.findById(studentId);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	
	@GetMapping("/api/student/profile")
	public Student getProfile(HttpSession session) {
		Student student = (Student)session.getAttribute("current_user");
		if(student != null) {
			return student;
		}
		return null;
	}
	

	@PostMapping("/api/student/register")
	public Student register(@RequestBody Student student,  HttpSession session) {
		Optional<Student> data = studentRepository.findStudentByUsername(student.getUsername());
		if(data.isPresent()) {
			throw new  UsernameConflictException("username :" + student.getUsername());
		}
		else 
		{
			session.setAttribute("current_user", student);
			return studentRepository.save(student);
		}
	}

	@PostMapping("/api/student/login")
	public Student login(@RequestBody Student student, HttpSession session) {
		Optional<Student> data = studentRepository.findStudentByCredentials(student.getUsername(),student.getPassword());
		if(!data.isPresent()) {
			throw new  InvalidLoginException("username :" + student.getUsername());
		}
		else 
		{
			session.setAttribute("current_user", data.get());
			return data.get();
		}
	}
	
	@PostMapping("/api/student/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}

	
}
