package com.cts.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cts.entity.Submission;

@Repository
public interface ISubmissionRepository extends JpaRepository<Submission, Integer>{

}
