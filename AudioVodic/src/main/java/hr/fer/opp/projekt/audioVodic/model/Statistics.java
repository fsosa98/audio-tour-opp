package hr.fer.opp.projekt.audioVodic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Statistics {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "statistics_id")
	private int id;
	private int pageTraffic;
	private long time;
	private int audioCounter;

	private String printTime;

	public Statistics() {
	}

	public Statistics(int pageTraffic, int time, int audioCounter) {
		this.pageTraffic = pageTraffic;
		this.time = time;
		this.audioCounter = audioCounter;

		printTime = "00:00:00";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPageTraffic() {
		return pageTraffic;
	}

	public void setPageTraffic(int pageTraffic) {
		this.pageTraffic = pageTraffic;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getAudioCounter() {
		return audioCounter;
	}

	public void setAudioCounter(int audioCounter) {
		this.audioCounter = audioCounter;
	}

	public void addPageTraffic() {
		pageTraffic++;
	}

	public String getPrintTime() {
		return printTime;
	}

	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}

	public void addAudioCounter() {
		audioCounter++;
	}

	public void updateTime(long time) {
		this.time += time;

		long diff = this.time;

		long hours = diff / (1000 * 60 * 60);
		diff -= hours * (1000 * 60 * 60);
		long minutes = diff / (1000 * 60);
		diff -= minutes * (1000 * 60);
		long seconds = diff / (1000);

		if (hours < 10) {
			printTime = "0";
		}
		printTime += hours + ":";
		if (minutes < 10) {
			printTime += "0";
		}
		printTime += minutes + ":";
		if (seconds < 10) {
			printTime += "0";
		}
		printTime += seconds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Statistics other = (Statistics) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
