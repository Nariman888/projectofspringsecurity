package kz.project.springsecurity.projectofspringsecurity.services.imp;

import kz.project.springsecurity.projectofspringsecurity.models.User;
import kz.project.springsecurity.projectofspringsecurity.repositories.UserRepository;
import kz.project.springsecurity.projectofspringsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User foundUser = userRepository.findByEmail(username);
            if (foundUser!=null) {
                return foundUser;
            } else {
                throw new UsernameNotFoundException("USER NOT FOUND");
        }
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User addUser(User user, String rePassword) {
        if (user.getPassword().equals(rePassword)) {
            User foundUser = getUser(user.getEmail());
            if (foundUser==null) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.save(user);
            }
        }
        return null;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    @Override
    public boolean updateUserPassword(String oldPassword, String newPassword, String reNewPassword) {
        if (newPassword.equals(reNewPassword)){
            User currentUser = getCurrentUser();
            if (currentUser!=null && !oldPassword.equals(newPassword) &&
                    passwordEncoder.matches(oldPassword,getCurrentUser().getPassword())){
                currentUser.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(currentUser);
                return true;
            }
        }
        return false;
    }
}
