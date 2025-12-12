package com.hubskills.controller;

import com.hubskills.model.User;
import com.hubskills.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // GET /api/users - Récupérer tous les users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // GET /api/users/{id} - Récupérer un user par ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // GET /api/users/email/{email} - Récupérer un user par email
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    // POST /api/users - Créer un nouvel utilisateur
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // PUT /api/users/{id} - Modifier un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Créer un nouvel user avec les données mises à jour
        User updatedUser = new User(
                id,
                userDetails.getEmail(),
                userDetails.getPassword(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getRole());
        User saved = userService.createUser(updatedUser);

        return ResponseEntity.ok(saved);
    }

    // DELETE /api/users/{id} - Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
