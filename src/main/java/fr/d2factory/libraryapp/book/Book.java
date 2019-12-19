package fr.d2factory.libraryapp.book;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * A simple representation of a book
 */
public class Book implements Serializable {
    String title;
    String author;

    @JsonProperty("isbn")
    ISBN isbn;

    public Book(ISBN isbn, String title, String author) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public Book() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @JsonProperty("isbn")
    public ISBN getISBN() {
        return isbn;
    }

    @JsonProperty("isbn")
    public void setISBN(ISBN isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, author, isbn);
    }
}
