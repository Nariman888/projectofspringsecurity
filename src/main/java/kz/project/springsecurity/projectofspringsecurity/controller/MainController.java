package kz.project.springsecurity.projectofspringsecurity.controller;

import kz.project.springsecurity.projectofspringsecurity.models.Role;
import kz.project.springsecurity.projectofspringsecurity.models.User;
import kz.project.springsecurity.projectofspringsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    UserService userService;
    @GetMapping(value = "/")
    public String indexPage(Model model) {
        return "index";
    }
    @GetMapping(value = "/signin")
    public String signin(Model model) {
        return "signin";
    }
    @GetMapping(value = "/forbidden")
    public String forbidden(Model model){
        return "forbidden";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/profile")
    public String profile(Model model) {
        return "profile";
    }

    @GetMapping(value = "/signup")
    public String signupPage(Model model){
        return "signup";
    }
    @PostMapping(value = "/signup")
    public String signup(@RequestParam(name = "user_email") String email,
                         @RequestParam(name = "user_password") String password,
                         @RequestParam(name = "user_re_password") String rePassword,
                         @RequestParam(name = "full_name") String fullName) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFullName(fullName);
        if (userService.addUser(user,rePassword)!=null){
            return "redirect:/signup?success";
        }
        return "redirect:/signup?error";
    }

    @PostMapping(value = "/updatepassword")
    @PreAuthorize("isAuthenticated()")
    public String updatePassword(@RequestParam(name = "user_old_password") String oldPassword,
                                 @RequestParam(name = "user_new_password") String newPassword,
                                 @RequestParam(name = "user_re_new_password") String newRePassword) {
        if (userService.updateUserPassword(oldPassword, newPassword, newRePassword)){
            return "redirect:/profile?success";
        }
        return "redirect:/profile?error";
    }
}
