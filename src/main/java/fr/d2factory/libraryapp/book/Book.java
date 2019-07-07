package fr.d2factory.libraryapp.book;

/**
 * A simple representation of a book
 */
public class Book {
    String title;
    String author;
    ISBN isbn;

    public Book(String title, String author, ISBN isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public ISBN getIsbn() {
        return isbn;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
