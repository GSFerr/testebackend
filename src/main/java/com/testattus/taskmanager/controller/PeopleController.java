package com.testattus.taskmanager.controller;

import com.testattus.taskmanager.People;
import com.testattus.taskmanager.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @PostMapping("/salvar")
    public ResponseEntity<People> savePeople(@RequestBody People people) {
        try {
            // Salvar a pessoa
            People savedPeople = peopleService.savePeople(people);
            return ResponseEntity.ok(savedPeople);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<People> editPeople(@PathVariable Long id, @RequestBody People people) {
        try {
            People editedPeople = peopleService.editPeople(id, people);
            return ResponseEntity.ok(editedPeople);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/listar-todas")
    public ResponseEntity<List<People>> listAllPeople() {
        List<People> pessoas = peopleService.listAllPeople();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/listar-por-cpf/{cpf}")
    public ResponseEntity<People> listByCpf(@PathVariable String cpf) {
        try {
            People people = peopleService.listByCpf(cpf);
            return ResponseEntity.ok(people);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
