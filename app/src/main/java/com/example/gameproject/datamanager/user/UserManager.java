package com.example.gameproject.datamanager.user;

import java.io.Serializable;
import java.util.ArrayList;

public class UserManager implements Serializable {

  private ArrayList<User> users;

  private User currentUser;

  private static final long serialVersionUID = 1L;

  public UserManager() {
    this.users = new ArrayList<>();
  }

  public User getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
  }

  public void addUser(User user) {
    this.users.add(user);
  }

  public String getPasswordByUsername(String username) {
    for (User user : this.users) {
      if (user.getName().equals(username)) return user.getPassword();
    }
    return null;
  }

  public User getUserByUsername(String username) {
    for (User user : this.users) {
      if (user.getName().equals(username)) return user;
    }
    return null;
  }

  public boolean isValidUsername(String username) {
    for (User user : this.users) {
      if (user.getName().equals(username)) return false;
    }
    return true;
  }

//    private boolean removeUserByUsername(String username) {
//        User userRemove = null;
//        for (User user : users) {
//            if (user.getName().equals(username)) userRemove = user;
//        }
//        if (userRemove != null) {
//            this.users.remove(userRemove);
//            return true;
//        }
//        return false;
//    }
//
//    public boolean updateUser(User user) {
//        if (removeUserByUsername(user.getName())) {
//            this.users.add(user);
//            return true;
//        }
//        return false;
//    }
}
