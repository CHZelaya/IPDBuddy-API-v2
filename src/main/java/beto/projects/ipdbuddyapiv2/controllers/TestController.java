package beto.projects.ipdbuddyapiv2.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping("/secure")
    public String securedEndpoint() {
        return "Access granted to secure endpoint!";
    }

}
