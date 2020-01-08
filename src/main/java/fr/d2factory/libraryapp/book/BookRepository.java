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
    private Map<Book, LocalDate> borrowedBooks = new HashMap<>();

    public void addBooks(List<Book> books){

        books.forEach(book -> {
            if (availableBooks.containsKey(book.getISBN())) {
                throw new AlreadyExistBookException("Book with ISBN " +book.isbn.isbnCode+ " is already exist");
            } else {
                availableBooks.put(book.getISBN(),book);
            }
        });
    }

    public Book findBook(long isbnCode) {
        return availableBooks.get(new ISBN(isbnCode));
    }

    public void saveBookBorrow(Book book, LocalDate borrowedAt) throws BookAlreadyBorrowedException {
        if (borrowedBooks.containsKey(book)) {
            throw new BookAlreadyBorrowedException("Book with ISBN "+book.isbn.isbnCode+" is already borrowed");
        } else {
            borrowedBooks.put(book, borrowedAt);
        }
    }

    public void removeBookFromBorrowedBooks(Book book){
        borrowedBooks.remove(book);
    }

    public LocalDate findBorrowedBookDate(Book book) {
        return borrowedBooks.get(book);
    }
}
