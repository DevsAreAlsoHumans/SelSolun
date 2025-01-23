package fr.doranco.selsolunback.controllers;


import fr.doranco.selsolunback.entites.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/api//auth/")
@RestController
public class UserController {

    @GetMapping(value = "user")
    ResponseEntity<User> getUser() {
        return ResponseEntity.ok(new User());
    }



}
