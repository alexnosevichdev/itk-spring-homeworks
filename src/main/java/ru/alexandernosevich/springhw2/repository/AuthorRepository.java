package ru.alexandernosevich.springhw2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandernosevich.springhw2.entity.Author;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Optional<Author> findByLastName(String lastName);
}
