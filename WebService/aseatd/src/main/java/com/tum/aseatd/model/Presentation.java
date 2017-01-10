package com.tum.aseatd.model;

import java.util.Calendar;
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

@Entity(name = "Presentation")
public class Presentation {

	public Presentation(){
		
	}
	
	public Presentation(Long id, String name, String description, Boolean presented,
			Exercise exercise){
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.presented = presented;
		this.exercise = exercise;
	}
	
	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private Boolean presented;
	
	@OneToOne(mappedBy = "presentation", cascade = CascadeType.ALL)
	private Exercise exercise;
	
	public Long getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public Boolean getPresented(){
		return this.presented;
	}
	
	public Exercise getExercise(){
		return this.exercise;
	}
	
	public void setId(final Long id){
		this.id = id;
	}
	
	public void setName(final String name){
		this.name = name;
	}
	
	public void setDescription(final String description){
		this.description = description;
	}
	
	public void setPresented(final Boolean presented){
		this.presented = presented;
	}
	
	public void setExercise(final Exercise exercise){
		this.exercise = exercise;
	}
}
