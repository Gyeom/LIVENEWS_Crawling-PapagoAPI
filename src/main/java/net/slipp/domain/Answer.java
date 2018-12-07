package net.slipp.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.ForeignKey;

@Entity
public class Answer extends AbstractEntity {
	
@ManyToOne
@JoinColumn (foreignKey = @ForeignKey(name = "fk_question_writer"))
@JsonProperty
private User writer;

@ManyToOne
@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
@JsonProperty
private Question question;

@Lob
@JsonProperty
private String contents;

public Answer() {
	
}

public Answer(User writer, Question question, String contents) {
	this.writer = writer;
	this.question = question;
	this.contents = contents;
}


public boolean isSameWriter(User loginUser) {
	// TODO Auto-generated method stub
	return loginUser.equals(this.writer);
}

@Override
public String toString() {
	return "Answer ["+ super.toString()+", writer=" + writer + ", question=" + question + ", contents=" + contents + "]";
}






}
