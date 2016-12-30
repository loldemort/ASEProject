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



@Entity(name = "Tutorial")
public class Tutorial {
	
	public Tutorial(){
		
	}
	
	public Tutorial(Long id, String groupname, String moduleid, String tutorname,
			List<Student> students){
		super();
		this.id = id;
		this.groupname = groupname;
		this.moduleid = moduleid;
		this.tutorname = tutorname;
		this.students = students;
	}
	
	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column
	private String groupname;
	
	@Column
	private String moduleid;
	
	@Column
	private String tutorname;
	
	@OneToMany(mappedBy = "tutorial", cascade = CascadeType.ALL)
	private List<Student> students;

	public Long getId(){
		return this.id;
	}

	public String getGroupname(){
		return this.groupname;
	}
	
	public String getModuleid(){
		return this.moduleid;
	}
	
	public String getTutorname(){
		return this.tutorname;
	}
	
	public List<Student> getStudents(){
		return this.students;
	}
	
	
	public void setId(final Long id){
		this.id = id;
	}
	
	public void setGroupname(final String groupname){
		this.groupname = groupname;
	}
	
	public void setModuleid(final String moduleid){
		this.moduleid = moduleid;
	}
	
	public void setTutorname(final String tutorname){
		this.tutorname = tutorname;
	}
	
	public void setStudents(final List<Student> students){
		this.students = students;
	}
}
