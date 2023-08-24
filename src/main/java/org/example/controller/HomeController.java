package org.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping("/admin")
    public String admin() {
        return "<h2>Welcome Admin!</h2>";
    }

    @RequestMapping("/user")
    public String user() {
        return "<h2>Welcome User!</h2>";
    }

    @RequestMapping("/all")
    public String all() {
        return "<h2>Hello Everyone!</h2>";
    }
}
