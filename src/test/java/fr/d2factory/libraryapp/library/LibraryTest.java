package fr.d2factory.libraryapp.library;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookAlreadyBorrowedException;
import fr.d2factory.libraryapp.book.BookRepository;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;
import static org.junit.jupiter.api.Assertions.*;

import fr.d2factory.libraryapp.member.WalletNotEnoughException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Do not forget to consult the README.md :)
 */
public class LibraryTest {
    private BookRepository bookRepository = new BookRepository();
    private Library library = new LibraryImpl(bookRepository);
    private static List<Book> books = new ArrayList<>();


    @BeforeEach
    void setup() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        File booksJson = new File("src/test/resources/books.json");
        books = mapper.readValue(booksJson, new TypeReference<List<Book>>() {
        });
        bookRepository.addBooks(books);
    }

    @Test
    void findBookISBN968787565445(){
        assertNotNull(bookRepository.findBook(968787565445L));
    }

    @Test
    void member_can_borrow_a_book_if_book_is_available(){
        Member member = new Student();
        member.setName("mahdi");
        Book book = library.borrowBook(46578964513L, member,LocalDate.now());
        assertEquals(book.getISBN().getIsbnCode(),46578964513L);
    }

    @Test
    void borrowed_book_is_no_longer_available(){
        Student student = new Student();
        student.setName("mahdi");
        student.setWallet(new BigDecimal(10));
        student.setStudyingStartDate(LocalDate.of(2019, 01, 13));
        Book book = library.borrowBook(46578964513L, student,LocalDate.of(2019, 11, 13));
        library.returnBook(book, student);
        assertNull(bookRepository.findBorrowedBookDate(book));
    }

    @Test
    void residents_are_taxed_10cents_for_each_day_they_keep_a_book(){
        Resident resident = new Resident();
        resident.setName("mahdi");
        resident.setWallet(new BigDecimal(10));
        LocalDate borrowedAt = LocalDate.of(2019, Month.NOVEMBER, 19);
        Book book = library.borrowBook(968787565445L, resident, borrowedAt);

        //calculating charge
        Long intervalPeriod = ChronoUnit.DAYS.between(borrowedAt, LocalDate.now());
        BigDecimal expectedCharge = new BigDecimal(0.10).multiply(new BigDecimal(intervalPeriod)).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal expectedWallet = new BigDecimal(10).subtract(expectedCharge).setScale(2, RoundingMode.HALF_EVEN);

        library.returnBook(book,resident);

        assertEquals(expectedWallet,resident.getWallet());
    }

    @Test
    void students_pay_10_cents_the_first_30days(){
        Student student = new Student();
        student.setName("mahdi");
        student.setWallet(new BigDecimal(10));
        student.setStudyingStartDate(LocalDate.of(2018, Month.JANUARY, 13));
        LocalDate borrowedAt = LocalDate.of(2019, Month.DECEMBER, 15);

        //calculating charge
        Long intervalPeriod = ChronoUnit.DAYS.between(borrowedAt, LocalDate.now());
        BigDecimal expectedCharge = new BigDecimal(0.10).multiply(new BigDecimal(intervalPeriod)).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal expectedWallet = new BigDecimal(10).subtract(expectedCharge).setScale(2, RoundingMode.HALF_EVEN);

        Book book = library.borrowBook(968787565445L, student, borrowedAt);

        library.returnBook(book,student);

        assertEquals(expectedWallet,student.getWallet());
    }

    @Test
    void students_in_1st_year_are_not_taxed_for_the_first_15days(){
        Student student = new Student();
        student.setName("mahdi");
        student.setWallet(new BigDecimal(10));
        student.setStudyingStartDate(LocalDate.of(2019, Month.JANUARY, 13));
        LocalDate borrowedAt = LocalDate.of(2019, Month.NOVEMBER, 30);

        //calculating charge
        Long intervalPeriod = ChronoUnit.DAYS.between(borrowedAt, LocalDate.now());
        BigDecimal expectedCharge = new BigDecimal(0.10).multiply(new BigDecimal(intervalPeriod-15)).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal expectedWallet = new BigDecimal(10).subtract(expectedCharge).setScale(2, RoundingMode.HALF_EVEN);

        Book book = library.borrowBook(968787565445L, student, borrowedAt);
        library.returnBook(book,student);

        assertEquals(expectedWallet,student.getWallet());
    }

    @Test
    void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days(){
        Resident resident = new Resident();
        resident.setName("mahdi");
        resident.setWallet(new BigDecimal(10));
        LocalDate borrowedAt = LocalDate.of(2019, Month.OCTOBER, 15);
        Book book = library.borrowBook(968787565445L, resident, borrowedAt);

        //calculating charge
        Long intervalPeriod = ChronoUnit.DAYS.between(borrowedAt, LocalDate.now());
        BigDecimal chargeFor60Days = new BigDecimal(60).multiply(new BigDecimal(0.10));
        BigDecimal chargeForTheRest = new BigDecimal(intervalPeriod-60).multiply(new BigDecimal(0.20));

        BigDecimal expectedCharge = chargeFor60Days.add(chargeForTheRest).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal expectedWallet = new BigDecimal(10).subtract(expectedCharge).setScale(2, RoundingMode.HALF_EVEN);

        library.returnBook(book,resident);

        assertEquals(expectedWallet,resident.getWallet());
    }

    @Test
    void members_cannot_borrow_book_if_they_have_late_books(){
        Resident resident = new Resident();
        resident.setName("mahdi");
        resident.setWallet(new BigDecimal(10));
        LocalDate borrowedAt = LocalDate.of(2019, Month.OCTOBER, 15);
        Book book = library.borrowBook(968787565445L, resident, borrowedAt);

        LocalDate borrowedAt2 = LocalDate.of(2019, Month.DECEMBER, 15);

        assertThrows(HasLateBooksException.class, () -> {
            Book book2 = library.borrowBook(465789453149L, resident, borrowedAt2);
        });
    }

    @Test
    void wallet_is_not_enough(){
        Resident resident = new Resident();
        resident.setName("mahdi");
        resident.setWallet(new BigDecimal(10));
        LocalDate borrowedAt = LocalDate.of(2019, Month.MARCH, 15);

        Book book = library.borrowBook(968787565445L, resident, borrowedAt);
        assertThrows(WalletNotEnoughException.class, () -> {
            library.returnBook(book, resident);
        });
    }

    @Test
    void book_is_borrowed_by_a_member(){
        Student student = new Student();
        student.setName("mahdi");
        student.setWallet(new BigDecimal(10));
        student.setStudyingStartDate(LocalDate.of(2019, 01, 13));
        Book book = library.borrowBook(46578964513L, student,LocalDate.of(2019, 11, 13));

        Optional<Book> bookOptional = student.getBooks().stream().filter(borrowedBook -> {
            return book.getISBN().equals(book.getISBN());
        }).findFirst();

        assertTrue(bookOptional.isPresent());
    }

    @Test
    void book_is_already_borrowed_by_a_member(){
        Student student = new Student();
        student.setName("mahdi");
        student.setWallet(new BigDecimal(10));
        student.setStudyingStartDate(LocalDate.of(2019, 01, 13));

        Book book = library.borrowBook(46578964513L, student,LocalDate.of(2019, 11, 13));

        Resident resident = new Resident();
        resident.setName("raddadi");
        resident.setWallet(new BigDecimal(10));
        LocalDate borrowedAt = LocalDate.of(2019, Month.MARCH, 15);

        assertThrows(BookAlreadyBorrowedException.class, () -> {
            library.borrowBook(46578964513L, resident, borrowedAt);
        });

    }

}
