package fr.d2factory.libraryapp.book;

import java.util.Objects;

/**
 * A simple representation of a book
 */
public class Book {
    private String title;
    private String author;
    private ISBN isbn;

    public Book() {}

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public ISBN getIsbn() {
        return isbn;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if(o instanceof Book) {
            Book book = (Book) o;
            result = Objects.equals(title, book.title) &&
                    Objects.equals(author, book.author) &&
                    Objects.equals(isbn, book.isbn);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, isbn);
    }
}
