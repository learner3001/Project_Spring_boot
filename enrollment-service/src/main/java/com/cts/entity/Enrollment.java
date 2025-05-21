package com.cts.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
		uniqueConstraints = @UniqueConstraint(columnNames = {"studentId","courseId"})
	)
public class Enrollment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int enrollmentID;
	
	@ManyToOne
	@JoinColumn(name = "studentId" , nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "courseId" , nullable = false)
	private Course course;
	
	private int progress;
}
