package com.cts.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.entity.Course;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Integer>{
	
	boolean existsByTitle(String title);
	
}
