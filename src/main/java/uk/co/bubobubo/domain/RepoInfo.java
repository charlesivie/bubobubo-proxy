package uk.co.bubobubo.domain;

public class RepoInfo {

	private Level level;
	private long limit;
	private long size;
	private boolean overLimit;
	private String id;

	public Level getLevel() {
		return level;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		this.limit = limit;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public boolean isOverLimit() {
		return overLimit;
	}

	public void setOverLimit(boolean overLimit) {
		this.overLimit = overLimit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
