package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.member.Member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookLibrary implements Library {

    // dependency injection
    // composition
    public BookLibrary(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private BookRepository bookRepository;

    @Override
    public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws BookNotFoundException, HasLateBooksException {
        for (Book b : member.getHeldBooks()) {
            LocalDate borrowedDate = bookRepository.findBorrowedBookDate(b.getIsbn());
            if (borrowedDate != null && ChronoUnit.DAYS.between(borrowedDate, LocalDate.now()) > member.getMaxDaysToKeep()) {
                throw new HasLateBooksException();
            }
        }

        Book borrowedBook = bookRepository.findBook(isbnCode);
        if (borrowedBook == null) {
            throw new BookNotFoundException();
        }
        bookRepository.saveBookBorrow(borrowedBook, borrowedAt);
        member.getHeldBooks().add(borrowedBook);
        return borrowedBook;
    }

    @Override
    public void returnBook(Book book, Member member) {
        LocalDate borrowedDate = bookRepository.findBorrowedBookDate(book.getIsbn());
        int difference = (int) ChronoUnit.DAYS.between(borrowedDate, LocalDate.now());
        member.payBook(difference);
    }
}
