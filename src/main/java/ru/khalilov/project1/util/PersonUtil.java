package ru.khalilov.project1.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.khalilov.project1.models.Person;

@Component
public class PersonUtil implements Validator {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonUtil(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (jdbcTemplate.query("SELECT * FROM person WHERE fullname=?", new Object [] {person.getFullName()},
                new BeanPropertyRowMapper<>(Person.class)).size()>0) {
            errors.rejectValue("fullName", "", "Такой пользователь уже существует");
        }
    }
}
