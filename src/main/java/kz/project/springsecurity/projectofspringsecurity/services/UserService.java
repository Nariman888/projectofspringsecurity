package kz.project.springsecurity.projectofspringsecurity.services;

import kz.project.springsecurity.projectofspringsecurity.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
     User getUser(String email);
     User addUser(User user,String rePassword);
     User getCurrentUser();
     boolean updateUserPassword(String oldPassword,String newPassword,String reNewPassword);
}
