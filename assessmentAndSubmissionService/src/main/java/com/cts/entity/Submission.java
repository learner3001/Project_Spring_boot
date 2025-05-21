package com.cts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int submissionID;
	
	@ManyToOne
	@JoinColumn(name = "assessmentID" , nullable = false)
	private Assessment assessment;
	
	@ManyToOne
	@JoinColumn(name = "studentId" , nullable = false)
	private User user;
	
	private int score;
}
