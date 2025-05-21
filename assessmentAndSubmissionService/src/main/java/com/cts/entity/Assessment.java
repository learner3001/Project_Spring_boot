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
public class Assessment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int assessmentID;
	
	@ManyToOne
	@JoinColumn(name = "courseID" , nullable = false)
	private Course course;
	
	private String type;
	
	private int maxScore;
}
