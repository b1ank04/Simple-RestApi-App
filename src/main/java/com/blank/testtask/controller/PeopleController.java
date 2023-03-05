package com.blank.testtask.controller;

import com.blank.testtask.model.PersonDto;
import com.blank.testtask.service.PeopleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PeopleController {
    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping("/api/getPerson")
    public ResponseEntity<Object> getPersonById(@RequestBody String personId) {
        try {
            Long id = Long.parseLong(personId);
            Optional<PersonDto> personDto = peopleService.findById(id);
            if (personDto.isPresent()) {
                return new ResponseEntity<>(personDto.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Person with such ID isn't presented", HttpStatus.NO_CONTENT);
            }
        } catch (Exception ignored) {
            return new ResponseEntity<>("Wrong format of provided ID", HttpStatus.BAD_REQUEST);
        }
    }
}
