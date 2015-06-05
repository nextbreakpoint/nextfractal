package com.nextbreakpoint.nextfractal.server;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoteFractal {
	private String source;
	private String error;
	private String UUID;
	private int jobsCount;
	private int tileSize;
	private List<RemoteJob> jobs;
	private long timestamp;

	public RemoteFractal() {
		timestamp = System.currentTimeMillis();
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getJobsCount() {
		return jobsCount;
	}

	public void setJobsCount(int jobsCount) {
		this.jobsCount = jobsCount;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String UUID) {
		this.UUID = UUID;
	}

	public int getTileSize() {
		return tileSize;
	}

	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}

	public List<RemoteJob> getJobs() {
		return jobs;
	}

	public void setJobs(List<RemoteJob> jobs) {
		this.jobs = jobs;
	}

	public long getTimestamp() {
		return timestamp;
	}
}
