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


@Entity(name = "Exercise")
public class Exercise {

	public Exercise(){
		
	}
	
	public Exercise(Long id, String exercisename, Calendar exercisedate, String exercisetutorname,
			Student student, Presentation presentation, Attendance attendance,
			QRcode qrcode){
		super();
		this.id = id;
		this.exercisename = exercisename;
		this.exercisedate = exercisedate;
		this.exercisetutorname = exercisetutorname;
		this.student = student;
		this.presentation = presentation;
		this.attendance = attendance;
		this.qrcode = qrcode;
	}
	
	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column
	private String exercisename;
	
	@Temporal(TemporalType.DATE)
	private Calendar exercisedate;
	
	@Column
	private String exercisetutorname;
	
	@ManyToOne
	@JoinColumn(name = "student_fk", referencedColumnName = "ID", nullable = false)
	private Student student;
	
	@OneToOne
	private Presentation presentation;
	
	@OneToOne
	private Attendance attendance;
	
	@OneToOne
	private QRcode qrcode;
	
	public Long getId(){
		return this.id;
	}
	
	public String getExercisename(){
		return this.exercisename;
	}
	
	public Calendar getExercisedate(){
		return this.exercisedate;
	}
	
	public String getExercisetutorname(){
		return this.exercisetutorname;
	}
	
	public Student getStudent(){
		return this.student;
	}
	
	public Presentation getPresentation(){
		return this.presentation;
	}
	
	public Attendance getAttendance(){
		return this.attendance;
	}
	
	public QRcode getQrcode(){
		return this.qrcode;
	}
	
	public void setId(final Long id){
		this.id = id;
	}
	
	public void setExercisename (final String exercisename){
		this.exercisename = exercisename;
	}
	
	
	public void setExercisedate(final Calendar exercisedate){
		this.exercisedate = exercisedate;
	}
	
	public void  setExercisetutorname(final String exercisetutorname){
		this.exercisetutorname = exercisetutorname;
	}
	
	public void  setStudent(final Student student){
		this.student = student;
	}
	
	public void  setPresentation(final Presentation presentation){
		this.presentation = presentation;
	}
	
	public void  setAttendance(final Attendance attendance){
		this.attendance = attendance;
	}
	
	public void setQrcode(final QRcode qrcode){
		this.qrcode = qrcode;
	}
}
