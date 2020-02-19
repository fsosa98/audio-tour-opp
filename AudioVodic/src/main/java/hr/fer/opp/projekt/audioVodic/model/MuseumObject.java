package hr.fer.opp.projekt.audioVodic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class MuseumObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@Column(length = 1600)
	private String description;
	private String imageName;
	private String audioName;
	private String groupOfMuseumObjects;
	
	private boolean inMuseum;

	@OneToOne
	@JoinColumn(name = "statistics_id")
	private Statistics statistics;

	public MuseumObject() {
	}

	public MuseumObject(String name, String description, String imageName, String groupOfMuseumObjects,
			String audioName, boolean inMuseum) {
		this.name = name;
		this.description = description;
		this.imageName = Integer.toString(id) + "." + imageName;
		this.audioName = Integer.toString(id) + "." + audioName;
		this.groupOfMuseumObjects = groupOfMuseumObjects;
		
		this.inMuseum = inMuseum;
	}

	public MuseumObject(String name, String description, String imageName, String audioName,
			String groupOfMuseumObjects, Statistics statistics, boolean inMuseum) {
		this.name = name;
		this.description = description;
		this.imageName = imageName;
		this.audioName = audioName;
		this.groupOfMuseumObjects = groupOfMuseumObjects;
		this.statistics = statistics;
		
		this.inMuseum = inMuseum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageExt) {
		this.imageName = Integer.toString(id) + "." + imageExt;
	}

	public String getAudioName() {
		return audioName;
	}

	public void setAudioName(String audioExt) {
		this.audioName = Integer.toString(id) + "." + audioExt;
	}

	public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	public String getGroupOfMuseumObjects() {
		return groupOfMuseumObjects;
	}

	public void setGroupOfMuseumObjects(String groupOfMuseumObjects) {
		this.groupOfMuseumObjects = groupOfMuseumObjects;
	}

	public boolean isInMuseum() {
		return inMuseum;
	}

	public void setInMuseum(boolean inMuseum) {
		this.inMuseum = inMuseum;
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
		MuseumObject other = (MuseumObject) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
