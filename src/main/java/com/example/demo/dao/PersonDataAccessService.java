package com.example.demo.dao;

import com.example.demo.model.Person;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override public int insertPerson(UUID id, Person person) {
        final String sql = "INSERT INTO person(id,name) VALUES(?, ?)";

        jdbcTemplate.update(sql, id, person.getName());
        return 0;
    }

    @Override public List<Person> selectAllPeople() {
        final String sql = "SELECT id, name FROM person";

        return jdbcTemplate.query(sql, ((resultSet, i) -> {
            UUID uuid = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(uuid, name);
        }));
    }

    @Override public Optional<Person> selectPersonById(UUID id) {
        final String sql = "" +
            "SELECT id, name FROM person " +
            "WHERE id=?";
        Person person = null;
        try {
            person = jdbcTemplate.queryForObject(
                sql,
                new Object[] { id },
                ((resultSet, i) -> {
                    UUID uuid = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    return new Person(uuid, name);
                })
            );
        }
        catch (EmptyResultDataAccessException ignored) {
        }
        return Optional.ofNullable(person);
    }

    @Override public int deletePersonById(UUID id) {
        String sql = "" +
            "DELETE FROM person " +
            "WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override public int updatePersonById(UUID id, Person person) {
        return 0;
    }
}
