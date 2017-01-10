package com.tum.aseatd.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity(name = "Student")
public class Student {
	
	public Student(){
		
	}
	
	public Student(Long id, String name, Long matricno, String course,
			List<Exercise> exercises, Tutorial tutorial){
		super();
		this.id = id;
		this.matricno = matricno;
		this.course = course;
		this.exercises = exercises;
		this.tutorial = tutorial;
	}
	
	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private Long matricno;
	
	@Column
	private String course;
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private List<Exercise> exercises;
	
	@ManyToOne
	@JoinColumn(name = "tutorial_fk", referencedColumnName = "ID", nullable = false)
	private Tutorial tutorial;
	
	public Long getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Long getMatricno(){
		return this.matricno;
	}
	
	public String getCourse(){
		return this.course;
	}
	
	public List<Exercise> getExercises(){
		return this.exercises;
	}
	
	public Tutorial getTutorial(){
		return this.tutorial;
	}

	public void setId(final Long id){
		this.id = id;
	}
	
	public void setName(final String name){
		this.name = name;
	}
	
	public void setMatricno(final Long matricno){
		this.matricno = matricno;
	}
	
	public void setCourse(final String course){
		this.course = course;
	}
	
	public void setExercises(final List<Exercise> exercises){
		this.exercises = exercises;
	}
	
	public void setTutorial(final Tutorial tutorial){
		this.tutorial = tutorial;
	}
}
