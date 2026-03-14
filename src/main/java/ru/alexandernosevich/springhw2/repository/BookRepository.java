package ru.alexandernosevich.springhw2.repository;

import ru.alexandernosevich.springhw2.entity.Book;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByBookName(String bookName);
}
