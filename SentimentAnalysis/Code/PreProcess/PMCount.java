package com.lokesh.SentAnalysis.Preprocess;

public class PMCount {

	int posCount;
	int negCount;
	int neutralCount;
	public PMCount(int posCount, int negCount,int neutralCount) {
		super();
		this.posCount = posCount;
		this.negCount = negCount;
		this.neutralCount = neutralCount;
	}
	public int getPosCount() {
		return posCount;
	}
	public void setPosCount(int posCount) {
		this.posCount = posCount;
	}
	public int getNegCount() {
		return negCount;
	}
	public void setNegCount(int negCount) {
		this.negCount = negCount;
	}
	public int getNeutralCount() {
		return neutralCount;
	}
	public void setNeutralCount(int neutralCount) {
		this.neutralCount = neutralCount;
	}
	
}
