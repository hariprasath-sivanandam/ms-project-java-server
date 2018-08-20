package courseapp.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Section {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private int seats,maxseats;
	
	@ManyToMany(mappedBy="enrolledSections")
	@JsonIgnore
	private List<Student> enrolledStudents;
	
	@ManyToOne
	@JsonIgnore
	private Course course;
	
	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public int getMaxseats() {
		return maxseats;
	}

	public void setMaxseats(int maxseats) {
		this.maxseats = maxseats;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
		if(!course.getSections().contains(this)) {
			course.getSections().add(this);
		}
	}
	
	public void enrollStudent(Student student) {
		this.enrolledStudents.add(student);
		if(!student.getEnrolledSections().contains(this)) {
			student.getEnrolledSections().add(this);
		}
	}	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Student> getEnrolledStudents() {
		return enrolledStudents;
	}
	public void setEnrolledStudents(List<Student> enrolledStudents) {
		this.enrolledStudents = enrolledStudents;
	}
}