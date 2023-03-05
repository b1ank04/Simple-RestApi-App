package com.blank.testtask.database;

import com.blank.testtask.model.Person;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {
    private static final Random random = new Random();

    private DataGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Person> createPeople() {
        List<String> firstNames = List.of("Artur", "Bogdan", "Vadim", "Ivan",
                "Dmitriy", "Egor", "Denis", "Anton", "Yaroslav", "Matvei",
                "Evgeniy", "Alla", "Maria", "Anastasia", "Polina", "Alisa",
                "Svetlana", "Julia", "Konstantin", "Victoria");
        List<String> lastNames = List.of("Shurko", "Prokopenko", "Ivanov(a)", "Svetlov(a)",
                "Taran", "Grachov(a)", "Kuzmenko", "Kravchenko", "Popov(a)", "Petrov(a)",
                "Smirnov(a)", "Sokolov(a)", "Golubev(a)", "Orlov(a)", "Antonov(a)", "Pavlov(a)",
                "Fedorov(a)", "Galkin(a)", "Gromov(a)", "Blohin(a)");
        Set<Person> people = new HashSet<>();
        while (people.size() < 20) {
            String firstName = firstNames.get(random.nextInt(firstNames.size()));
            String lastName = lastNames.get(random.nextInt(lastNames.size()));
            Date birthDate = randomDate();
            Person student = new Person(null, firstName, lastName, birthDate);
            people.add(student);
        }
        return people.stream().toList();
    }

    private static Date randomDate() {
        long start = LocalDate.of(1970, Month.JANUARY, 1).toEpochDay();
        long end = LocalDate.now().toEpochDay();
        long random = ThreadLocalRandom.current()
                .nextLong(start, end);
        return Date.valueOf(LocalDate.ofEpochDay(random));
    }
}
