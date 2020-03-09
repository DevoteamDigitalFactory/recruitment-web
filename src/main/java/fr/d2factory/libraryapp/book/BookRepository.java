package fr.d2factory.libraryapp.book;

import fr.d2factory.libraryapp.member.Member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {
    private Map<ISBN, Book> availableBooks = new HashMap<>();
    private Map<Book, LocalDate> borrowedBooks = new HashMap<>();
    private Map<Book, Member> borrower = new HashMap<>();

    public void addBooks(List<Book> books){
        books.stream().filter(b->!availableBooks.containsKey(b.getIsbn()))
                .forEach(b->availableBooks.put(b.getIsbn(), b));
    }

    public Book findBook(long isbnCode) {
        return availableBooks.get(new ISBN(isbnCode));
    }

    public Member findBorrower(Book book) {
        return borrower.get(book);
    }

    public void saveBookBorrow(Book book, LocalDate borrowedAt){
        availableBooks.remove(book.getIsbn());
        borrowedBooks.put(book, borrowedAt);
    }

    public void saveBorrower(Book book, Member member){
        borrower.put(book, member);
    }

    public LocalDate findBorrowedBookDate(Book book) {
        return borrowedBooks.get(book);
    }

    public void returnBook(Book book){
        borrowedBooks.remove(book);
        availableBooks.put(book.getIsbn(), book);
        borrower.remove(book);
    }


    public Map<Member, List<Book>> booksBorrowedByMember(){
        return borrower.entrySet()
                .stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
    }

}
