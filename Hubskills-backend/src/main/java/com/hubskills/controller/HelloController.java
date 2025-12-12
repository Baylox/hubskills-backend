// HelloController.java
package com.hubskills.controller;

import com.hubskills.model.HelloResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public HelloResponse getHelloMessage() {
        return new HelloResponse("Hello HubSkills!", "HubSkills Backend");
    }
}
