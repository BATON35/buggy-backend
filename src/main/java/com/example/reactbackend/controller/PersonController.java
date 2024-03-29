package com.example.reactbackend.controller;

import com.example.reactbackend.model.Person;
import com.example.reactbackend.service.PersonServiceI;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
public class PersonController {

    public static final String ID_NOT_FOUND_ERROR_MSG = "Person Not Found, id: ";

    private PersonServiceI personService;

    public PersonController(PersonServiceI personService) {
        this.personService = personService;
    }

    @GetMapping("/people")
    List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/people/{id}")
    Person findById(@PathVariable Long id) {
        return personService.find(id);
    }

    @PostMapping("/people")
    Person save(@RequestBody Person person) {
        return personService.save(person);
    }

    @PutMapping("/people")
    Person update(@RequestBody Person person) {
        Person personToUpdate;
        try {
            personToUpdate = personService.find(person.getId());
        } catch (NoSuchElementException nse) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ID_NOT_FOUND_ERROR_MSG + person.getId(), nse);
        }
        personToUpdate.setFirstName(person.getFirstName());
        personToUpdate.setLastName(person.getLastName());

        return personService.save(personToUpdate);
    }

    @DeleteMapping("/people/{id}")
    void delete(@PathVariable Long id) {
        Person personToDelete;
        try {
            personToDelete = personService.find(id);
        } catch (NoSuchElementException nse) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ID_NOT_FOUND_ERROR_MSG + id, nse);
        }
        personService.delete(personToDelete);
    }
}
