package courseapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import courseapp.models.Student;
import courseapp.models.User;



public interface StudentRepository extends CrudRepository<Student, Integer> {
	@Query("SELECT u FROM Student u WHERE u.username=:username AND u.password=:password")
	Optional<Student>  findStudentByCredentials(
		@Param("username") String username, 
		@Param("password") String password);
	
	@Query("SELECT u FROM Student u WHERE u.username=:username")
	Optional<Student> findStudentByUsername(
		@Param("username") String username);

}