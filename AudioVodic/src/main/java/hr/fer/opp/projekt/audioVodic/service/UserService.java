package hr.fer.opp.projekt.audioVodic.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.audioVodic.model.User;
import hr.fer.opp.projekt.audioVodic.repository.UserRepository;

@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	public User getUserByUsername(String username) {
		if (username == null)
			return null;
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(users::add);
		for (User user : users) {
			if (username.equals(user.getUsername())) {
				return user;
			}
		}
		return null;
	}

	public User getUser(int id) {
		return userRepository.findById(id).orElse(null);
	}

	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	public void addUser(User user) {
		userRepository.save(user);
	}

	public User getVlasnik() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(users::add);
		for (User user : users) {
			if (user.getRole().getName().equals("Vlasnik sustava")) {
				return user;
			}
		}
		return null;
	}

	public List<User> getAdmins() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(users::add);
		List<User> admins = new ArrayList<User>();
		for (User user : users) {
			if (user.getRole().getName().equals("Administrator")) {
				admins.add(user);
			}
		}
		return admins;
	}

	public List<User> getRegisteredUsers() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(users::add);
		List<User> reg = new ArrayList<User>();
		for (User user : users) {
			if (user.getRole().getName().equals("Registrirani korisnik")) {
				reg.add(user);
			}
		}
		return reg;
	}

	public int numberOfAdmins() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(users::add);
		List<User> admins = new ArrayList<User>();
		for (User user : users) {
			if (user.getRole().getName().equals("Administrator")) {
				admins.add(user);
			}
		}
		return admins.size();
	}

	public List<User> getOnlineRegisteredUsers() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(users::add);
		List<User> reg = new ArrayList<User>();
		for (User user : users) {
			if (user.getRole().getName().equals("Registrirani korisnik") && user.isCurrentlyActive()) {
				reg.add(user);
			}
		}
		return reg;
	}

	public List<User> getOnlineAdmins() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(users::add);
		List<User> admins = new ArrayList<User>();
		for (User user : users) {
			if (user.getRole().getName().equals("Administrator") && user.isCurrentlyActive()) {
				admins.add(user);
			}
		}
		return admins;
	}

}
