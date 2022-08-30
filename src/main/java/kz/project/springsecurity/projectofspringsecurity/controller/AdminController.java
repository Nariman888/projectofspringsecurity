package kz.project.springsecurity.projectofspringsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/adminpage")
    public String adminpage(){
        return "adminindex";
    }
}
