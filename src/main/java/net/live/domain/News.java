package net.live.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
public class News  {

	// TODO Auto-generated method stub
	
	@Column
	@Id
	@GeneratedValue
	@JsonProperty
	private Long id;
	
	@JsonProperty
	private String link;
	@JsonProperty
	private String image;
	@JsonProperty
	@Lob
	private String contents;
	@JsonProperty
	@Lob
	private String headline;
	@JsonProperty
	private int pageNum; 
	
	
	
	
	//추가
	@JsonProperty
	private Integer countOfAnswer = 0;
	
	
	@OneToMany(mappedBy="news")
	@OrderBy("id DESC")
	private List<NewsAnswer> newsAnswers; 
	//
	
	
	public News() {
		
	}

	public News(String link, String image, String contents, String headline, int pageNum) {
		this.link = link;
		this.image = image;
		this.contents = contents;
		this.headline = headline;
		this.pageNum = pageNum;
	}
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getHeadLine() {
		return headline;
	}
	public void setHeadLine(String headline) {
		this.headline = headline;
	}
	public void addAnswer() {
		// TODO Auto-generated method stub
		this.countOfAnswer +=1;
		
	}
	public void deleteAnswer() {
		this.countOfAnswer -=1;
	}

}
