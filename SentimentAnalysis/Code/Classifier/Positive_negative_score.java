package com.vrishank.SentAnalysis.Classifier;

public class Positive_negative_score {
	float positive_score;
	float negative_score;
	public Positive_negative_score(float positive_score, float negative_score) {
		super();
		this.positive_score = positive_score;
		this.negative_score = negative_score;
	}
	public float getPositive_score() {
		return positive_score;
	}
	public void setPositive_score(float positive_score) {
		this.positive_score = positive_score;
	}
	public float getNegative_score() {
		return negative_score;
	}
	public void setNegative_score(float negative_score) {
		this.negative_score = negative_score;
	}
	
}
