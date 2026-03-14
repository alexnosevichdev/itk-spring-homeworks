package ru.alexandernosevich.springhw2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandernosevich.springhw2.entity.Author;
import ru.alexandernosevich.springhw2.exception.ResourseOrBookNotFoundException;
import ru.alexandernosevich.springhw2.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getByAuthorLastName(String lastName) {
        return authorRepository.findByLastName(lastName)
                .orElseThrow(() -> new ResourseOrBookNotFoundException("Автор не найден"));
    }

    public Author createAuthor(Author author){
        return authorRepository.save(author);
    }

    public void deleteAuthor(UUID authorId){
        authorRepository.deleteById(authorId);
    }
}
