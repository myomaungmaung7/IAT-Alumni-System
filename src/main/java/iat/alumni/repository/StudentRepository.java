package iat.alumni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import iat.alumni.model.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{
	@Query("SELECT s FROM Student s WHERE s.iatEmail=:em")
	 Student findbyEmail(@Param("em")String em);
}
