package cl.ecomarket.usermanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/logout-confirmado")
    public String logoutConfirmacion() {
        return "logout-confimacion";
    }

}
