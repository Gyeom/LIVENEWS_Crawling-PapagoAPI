package net.slipp.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonProperty;



@Entity
public class Question extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name= "fk_question_writer"))
	@JsonProperty
	private User writer;
	@JsonProperty
	private String title;
	@Lob
	@JsonProperty
	private String contents;
	
	@JsonProperty
	private Integer countOfAnswer = 0;
	
	
	@OneToMany(mappedBy="question",  cascade = CascadeType.ALL)
	@OrderBy("id DESC")
	private List<Answer> answers; 
	
	public Question() {}
	public Question(User writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}

	public void update(String title, String contents) {
		// TODO Auto-generated method stub
		this.title = title;
		this.contents = contents;
	}

	public boolean isSameWriter(User loginUser) {
		// TODO Auto-generated method stub
		 
		return this.writer.equals(loginUser);
	}
	public void addAnswer() {
		// TODO Auto-generated method stub
		this.countOfAnswer +=1;
		
	}
	public void deleteAnswer() {
		this.countOfAnswer -=1;
	}

	
	
	
	

	 
	
}
