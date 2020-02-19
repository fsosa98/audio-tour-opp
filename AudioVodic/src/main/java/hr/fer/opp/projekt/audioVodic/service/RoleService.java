package hr.fer.opp.projekt.audioVodic.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.audioVodic.model.Role;
import hr.fer.opp.projekt.audioVodic.repository.RoleRepository;

@Service
@Transactional
public class RoleService {

	private final RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public void addRole(Role role) {
		roleRepository.save(role);
	}

	public Role getRoleByName(String name) {
		List<Role> roles = new ArrayList<Role>();
		roleRepository.findAll().forEach(roles::add);
		for (Role role : roles) {
			if (name.equals(role.getName())) {
				return role;
			}
		}
		return null;
	}

}
