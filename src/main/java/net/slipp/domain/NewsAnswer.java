package net.slipp.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class NewsAnswer extends AbstractEntity {

	@ManyToOne
	@JoinColumn (foreignKey = @ForeignKey(name = "fk_newsAnswer_writer"))
	@JsonProperty
	private User writer;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_newsAnswer_to_news"))
	@JsonProperty
	private News news;

	@Lob
	@JsonProperty
	private String contents;

	public NewsAnswer() {

	}

	public NewsAnswer(User writer, News news, String contents) {
		this.writer = writer;
		this.news = news;
		this.contents = contents;
	}


	public boolean isSameWriter(User loginUser) {
		// TODO Auto-generated method stub
		return loginUser.equals(this.writer);
	}

	@Override
	public String toString() {
		return "NewsAnswer ["+ super.toString()+", writer=" + writer + ", news=" + news + ", contents=" + contents + "]";
	}
}