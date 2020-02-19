package hr.fer.opp.projekt.audioVodic.repository;

import org.springframework.data.repository.CrudRepository;

import hr.fer.opp.projekt.audioVodic.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
