package iat.alumni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

 
import iat.alumni.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

	 
}