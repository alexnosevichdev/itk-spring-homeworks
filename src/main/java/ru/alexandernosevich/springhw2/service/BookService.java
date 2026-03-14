package ru.alexandernosevich.springhw2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandernosevich.springhw2.entity.Book;
import ru.alexandernosevich.springhw2.exception.ResourseOrBookNotFoundException;
import ru.alexandernosevich.springhw2.repository.BookRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

//    public List<Book> getAllBooks(){
//        return bookRepository.findAll();
//    }

    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public List<Book> getBookByBookName(String bookName) {
        return bookRepository.findByBookName(bookName);
    }

    public Book getById(UUID bookId) {
        return bookRepository.findById(bookId).orElseThrow(() ->
                new ResourseOrBookNotFoundException("Книга не найдена"));
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBookByBookId(UUID bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new ResourseOrBookNotFoundException("Книга не найдена");
        }
        bookRepository.deleteById(bookId);
    }

}
