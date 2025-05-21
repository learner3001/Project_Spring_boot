package com.cts.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cts.entity.Assessment;

@Repository
public interface IAssessmentRepository extends JpaRepository<Assessment, Integer>{

}
