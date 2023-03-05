package com.blank.testtask.service;

import com.blank.testtask.model.Person;
import com.blank.testtask.model.PersonDto;
import com.blank.testtask.repository.PeopleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class PeopleService {
    private final PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Transactional(readOnly = true)
    public Optional<PersonDto> findById(Long id) {
        Optional<Person> optionalPerson = peopleRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person presentedPerson = optionalPerson.get();
            int age = Period.between(presentedPerson.getBirthDate().toLocalDate(), LocalDate.now()).getYears();
            PersonDto personDto = new PersonDto(presentedPerson.getFirstName(), presentedPerson.getLastName(), age);
            return Optional.of(personDto);
        } else return Optional.empty();
    }
}
