package com.cts.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.entity.Enrollment;

@Repository
public interface IEnrollmentRepository extends JpaRepository<Enrollment, Integer>{

}
