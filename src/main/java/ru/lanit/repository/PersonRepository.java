package ru.lanit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lanit.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {}
