package ru.alexandernosevich.springhwdatajdbc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.alexandernosevich.springhwdatajdbc.entity.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BookRepository{
  private final JdbcTemplate jdbcTemplate;

  public BookRepository(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
  }

  //Создаем книгу
  public Book save(Book book){
      //Если книги еще нет в базе
      if(book.getId()==null){
          UUID newId = UUID.randomUUID();
          jdbcTemplate.update("INSERT INTO books(id, title, author, publication_year)" +
                  "VALUES(?, ?, ?, ?)", newId, book.getTitle(), book.getAuthor(),
          book.getPublicationYear());

          book.setId(newId);
      } else {
          //Если книга есть - обновляем
          jdbcTemplate.update("UPDATE books SET title=?, author=?, publication_year=? " +
                          "WHERE id=",
                  book.getTitle(), book.getAuthor(),
                  book.getPublicationYear(), book.getId());
      }
      return book;
  }

  //Найти все книги
    public List<Book> findAll() {
      return jdbcTemplate.query("SELECT * FROM books", (rs, rowNum) ->
              new Book(
                      rs.getObject("id", UUID.class),
                      rs.getString("title"),
                      rs.getString("author"),
                      rs.getInt("publication_year")
              ));
    }

  //Найти книгу по АйДи
  public Optional<Book> findById(UUID id) {
      List<Book> books = jdbcTemplate.query("SELECT * FROM books WHERE id=?", (rs, rowNum) ->
      new Book(
              rs.getObject("id", UUID.class),
              rs.getString("title"),
              rs.getString("author"),
              rs.getInt("publication_year")
      ), id);
      return books.stream()
              .findFirst();
  }

  //Удаление книги
  public void deleteById(UUID id) {
      jdbcTemplate.update("DELETE FROM books WHERE id=?", id);
  }

  //Поиск по названию
  public List<Book> findByTitle(String title) {
      return jdbcTemplate.query("SELECT * FROM books WHERE title=?",
              (rs, rowNum)->
               new Book(
                       rs.getObject("id", UUID.class),
                       rs.getString("title"),
                       rs.getString("author"),
                       rs.getInt("publication_year")
               ), title);
  }
}
