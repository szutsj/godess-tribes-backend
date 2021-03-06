package com.greenfoxacademy.goddesstribesbackend.services;

import com.greenfoxacademy.goddesstribesbackend.models.entities.User;
import com.greenfoxacademy.goddesstribesbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  private UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public boolean checkPassword(String passwordToCheck) {
    return passwordToCheck != null && passwordToCheck.length() >= 8;
  }

  public boolean checkUserByName(String usernameToCheck) {
    return userRepository.findUserByUsername(usernameToCheck).isPresent();
  }

  public boolean checkUserByNameAndPassword(String usernameToCheck, String passwordToCheck) {
    return userRepository.findUserByUsernameAndPassword(usernameToCheck, passwordToCheck).isPresent();
  }

  public Optional<User> findUserByName(String username) {
    return userRepository.findUserByUsername(username);
  }

  public User saveUser(String username, String password) {
    if (!checkUserByName(username) && checkPassword(password)) {
      User newUser = new User(username, password);
      return userRepository.save(newUser);
    }
    return null;
  }

  public void loginUser(String username) {
    if (checkUserByName(username)) {
      User loggedInUser = findUserByName(username).get();
      loggedInUser.setLoggedIn(true);
      userRepository.save(loggedInUser);
    }
  }

}
