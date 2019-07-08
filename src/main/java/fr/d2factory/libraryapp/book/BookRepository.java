package fr.d2factory.libraryapp.book;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {

    private Map<ISBN, Book> availableBooks = new HashMap<>();
    private Map<ISBN, LocalDate> borrowedBooks = new HashMap<>();

    public void addBooks(List<Book> books){
        for (Book b : books) {
            availableBooks.put(b.getIsbn(), b);
        }
    }

    public Book findBook(long isbnCode) {
        ISBN foo = new ISBN(isbnCode);
        return availableBooks.get(foo);
    }

    public void saveBookBorrow(Book book, LocalDate borrowedAt){
        borrowedBooks.put(availableBooks.remove(book.getIsbn()).getIsbn(), borrowedAt);
    }

    public LocalDate findBorrowedBookDate(ISBN isbn) {
        return borrowedBooks.get(isbn);
    }
}
