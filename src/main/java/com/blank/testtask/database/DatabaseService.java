package com.blank.testtask.database;

import com.blank.testtask.model.Person;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseService implements ApplicationRunner {
    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_PEOPLE = "INSERT INTO people (first_name, last_name, birth_date) values (?,?,?)";

    public DatabaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Integer peopleFlag = jdbcTemplate.queryForList("SELECT count(id) FROM people", Integer.class).get(0);
        if (peopleFlag < 1) fillPeople(DataGenerator.createPeople());
    }
    private void fillPeople(List<Person> people) {
        for (Person person : people) {
            jdbcTemplate.update(INSERT_PEOPLE,
                    person.getFirstName(),
                    person.getLastName(),
                    person.getBirthDate());
        }
    }
}
