// HelloResponse.java
package com.hubskills.model;

public class HelloResponse {

    private String message;
    private String project;

    public HelloResponse(String message, String project) {
        this.message = message;
        this.project = project;
    }

    public String getMessage() {
        return message;
    }

    public String getProject() {
        return project;
    }
}
