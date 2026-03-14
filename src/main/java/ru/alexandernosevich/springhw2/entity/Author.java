package ru.alexandernosevich.springhw2.entity;

import ru.alexandernosevich.springhw2.entity.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="author")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID authorId;

    @NotBlank(message = "Заполните имя автора")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Введите фамилию автора")
    @Column(nullable = false)
    private String lastName;


    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> bookList = new ArrayList<>();

    //Добавить книгу
    public void addBook(Book book){
        bookList.add(book);
        book.setAuthor(this);
    }

    //Удалить книгу
    public void deleteBook(Book book) {
        bookList.remove(book);
        book.setAuthor(null);
    }
}

