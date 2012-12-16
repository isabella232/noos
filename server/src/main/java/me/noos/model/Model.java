package me.noos.model;

import java.util.ArrayList;
import java.util.List;

import me.noos.engine.comment.Comment;
import me.noos.engine.recommendation.collaborative.data.BaseDataset;
import me.noos.engine.recommendation.collaborative.recommender.DiggDelphi;

public class Model {
	private static Model instance = null;
	
	private BaseDataset dataset;
	private DiggDelphi delphi;
	private List<Comment> comments;
	private int commentCounter = 1;
	
	private Model() {
		comments = new ArrayList<Comment>();
	}
	
	public static Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}
		
		return instance;
	}

	public BaseDataset getDataset() {
		return dataset;
	}

	public void setDataset(BaseDataset dataset) {
		this.dataset = dataset;
	}
	
	public DiggDelphi getDelphi() {
		return delphi;
	}

	public void setDelphi(DiggDelphi delphi) {
		this.delphi = delphi;
	}
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public int getCommentCounter() {
		return commentCounter;
	}

	public void setCommentCounter(int commentCounter) {
		this.commentCounter = commentCounter;
	}
}
