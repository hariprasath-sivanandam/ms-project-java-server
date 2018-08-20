package courseapp.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  private String username;
  private String password;
  private String firstName;
  private String lastName;	
  private String phone;
  private String email;
  private String role;
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateOfBirth;
  @ManyToMany
	@JoinTable(name="STUDENT_SECTION",
		joinColumns=@JoinColumn(
				name="STUDENT_ID", 
				referencedColumnName="ID"),
		inverseJoinColumns=@JoinColumn(
				name="SECTION_ID",
				referencedColumnName="ID"))
@JsonIgnore
private List<Section> enrolledSections;

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
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public java.util.Date getDateOfBirth() {
	return dateOfBirth;
}
public void setDateOfBirth(java.util.Date dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
} 
 
  
}
