package courseapp.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Student {
	 @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private int id;
	  private String username;
	  private String password;
	  private String firstName;
	  private String lastName;	
	  private String phone;
	  private String email;
	  private boolean isAdmin=false;
	  
	  @ManyToMany
		@JoinTable(name="STUDENT_SECTION",
			joinColumns=@JoinColumn(
					name="STUDENT_ID", 
					referencedColumnName="ID"),
			inverseJoinColumns=@JoinColumn(
					name="SECTION_ID",
					referencedColumnName="ID"))		
	 private List<Section> enrolledSections = new ArrayList<Section>();
	  
	public void enrollSection(Section section) {
		this.enrolledSections.add(section);
		if(!section.getEnrolledStudents().contains(this)) {
			section.getEnrolledStudents().add(this);
		}
	}
	
	public void unrollSection(Section section) {
		List<Section> filteredList = this.enrolledSections.stream().filter(sec -> sec.getId() != section.getId()).collect(Collectors.toList());
		this.setEnrolledSections(filteredList);
		if(section.getEnrolledStudents().contains(this)) {
			section.getEnrolledStudents().remove(this);
		}
	}
	  
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public List<Section> getEnrolledSections() {
		return enrolledSections;
	}

	public void setEnrolledSections(List<Section> enrolledSections) {
		this.enrolledSections = enrolledSections;
	}

	
	  
}
