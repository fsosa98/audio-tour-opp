package hr.fer.opp.projekt.audioVodic.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.audioVodic.model.MuseumObject;
import hr.fer.opp.projekt.audioVodic.repository.MuseumObjectRepository;

@Service
@Transactional
public class MuseumObjectService {

	private final MuseumObjectRepository museumObjectRepository;

	public MuseumObjectService(MuseumObjectRepository museumObjectRepository) {
		this.museumObjectRepository = museumObjectRepository;
	}

	public List<MuseumObject> getAllObjects() {
		List<MuseumObject> museumObjects = new ArrayList<MuseumObject>();
		museumObjectRepository.findAll().forEach(museumObjects::add);
		return museumObjects;
	}

	public MuseumObject getMuseumObject(int id) {
		return museumObjectRepository.findById(id).orElse(null);
	}

	public void addMuseumObject(MuseumObject museumObject) {
		museumObjectRepository.save(museumObject);
	}

	public void deleteMuseumObject(MuseumObject museumObject) {
		museumObjectRepository.delete(museumObject);
	}

	public List<String> getAllgroupsOfMuseumObjects() {
		List<String> groupsOfMuseumObjects = new ArrayList<String>();
		for (MuseumObject musemObject : getAllObjects()) {
			if (!groupsOfMuseumObjects.contains(musemObject.getGroupOfMuseumObjects())) {
				groupsOfMuseumObjects.add(musemObject.getGroupOfMuseumObjects());
			}
		}
		return groupsOfMuseumObjects;
	}

	public List<List<MuseumObject>> getAllObjectsByGroups() {
		List<List<MuseumObject>> museumObjects = new ArrayList<List<MuseumObject>>();
		for (String group : getAllgroupsOfMuseumObjects()) {
			List<MuseumObject> objectsByOneGroup = new ArrayList<MuseumObject>();
			for (MuseumObject museumObject : getAllObjects()) {
				if (group.equals(museumObject.getGroupOfMuseumObjects()) && museumObject.isInMuseum()) {
					objectsByOneGroup.add(museumObject);
				}
			}
			museumObjects.add(objectsByOneGroup);
		}
		return museumObjects;
	}

}
