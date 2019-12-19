package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LibraryImpl implements Library {

    BookRepository bookRepository;

    public LibraryImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException {
        Book book = bookRepository.findBook(isbnCode);
        if (book != null) {

            member.getBooks().forEach(borrowedBook -> {
                int numberOfDays = getNumberOfDays(borrowedBook);

                if ((member instanceof Student && numberOfDays > 30)
                        || (member instanceof Resident && numberOfDays > 60)){
                    throw new HasLateBooksException();
                }
            });

            member.addBook(book);
            bookRepository.saveBookBorrow(book, borrowedAt);
            return book;

        }
        return null;
    }

    @Override
    public void returnBook(Book book, Member member) {
        int numberOfDays = getNumberOfDays(book);
        member.payBook(numberOfDays);
        bookRepository.removeBookFromBorrowedBooks(book);
        member.removeBook(book);
    }

    private int getNumberOfDays(Book borrowedBook) {
        LocalDate borrowingDate = bookRepository.findBorrowedBookDate(borrowedBook);
        Long intervalPeriod = ChronoUnit.DAYS.between(borrowingDate, LocalDate.now());
        return intervalPeriod.intValue();
    }
}
