package fr.d2factory.libraryapp.library;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Profil;
import fr.d2factory.libraryapp.member.ResidentMember;
import fr.d2factory.libraryapp.member.StudentMember;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Do not forget to consult the README.md :)
 */
public class LibraryTest {

    private BookRepository bookRepository = new BookRepository();
    private Library library = new LibraryManagmentSystem(bookRepository) ;
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
    void member_can_borrow_a_book_if_book_is_available(){
        Member member = new StudentMember("Y2037978P", "Hicham", "Moustaid", 20.0f, Profil.STUDENT);
        Book book = library.borrowBook(46578964513L, member, LocalDate.now());
        Assertions.assertNotNull(book);
    }

    @Test
    void borrowed_book_is_no_longer_available(){
        Member member1 = new StudentMember("Y2037978P", "Hicham", "Moustaid", 20.0f, Profil.STUDENT);
        Member member2 = new StudentMember("TR56553D", "Simo", "Fenjero", 30.0f, Profil.RESIDENT);
        Book bookAvailable = library.borrowBook(46578964513L, member1, LocalDate.now());
        Book bookNotAvailable = library.borrowBook(46578964513L, member2, LocalDate.now());
        Assertions.assertNull(bookNotAvailable);
    }

    @Test
    void residents_are_taxed_10cents_for_each_day_they_keep_a_book(){
        Member member = new ResidentMember("Y2037978P", "Hicham", "Moustaid", 20.0f, Profil.RESIDENT);
        LocalDate dateBorrowed = LocalDate.now().minus(10, ChronoUnit.DAYS);
        Book book = library.borrowBook(46578964513L, member, dateBorrowed);
        library.returnBook(book, member);
        Assertions.assertEquals(19f, member.getWallet());
    }

    @Test
    void students_pay_10_cents_the_first_30days(){
        Member member = new StudentMember("Y2037978P", "Hicham", "Moustaid", 20.0f, Profil.STUDENT);
        LocalDate dateBorrowed = LocalDate.now().minus(30, ChronoUnit.DAYS);
        Book book = library.borrowBook(46578964513L, member, dateBorrowed);
        library.returnBook(book, member);
        Assertions.assertEquals(17f, member.getWallet());
    }

    @Test
    void students_in_1st_year_are_not_taxed_for_the_first_15days(){
        Member member = new StudentMember("Y2037978P", "Hicham", "Moustaid", 20.0f, Profil.STUDENT_1ST_YEAR);
        LocalDate dateBorrowed = LocalDate.now().minus(27, ChronoUnit.DAYS);
        Book book = library.borrowBook(46578964513L, member, dateBorrowed);
        library.returnBook(book, member);
        Assertions.assertEquals(18.8f, member.getWallet());
    }
    
    @Test
    void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days(){
        Member member = new ResidentMember("Y2037978P", "Hicham", "Moustaid", 20.0f, Profil.RESIDENT);
        member.payBook(65);
        Assertions.assertEquals(13f, member.getWallet());
    }

    @Test
    void members_cannot_borrow_book_if_they_have_late_books(){
        Member member = new ResidentMember("Y2037978P", "Hicham", "Moustaid", 20.0f, Profil.STUDENT);
        LocalDate dateBorrowed = LocalDate.now().minus(31, ChronoUnit.DAYS);
        Book book = library.borrowBook(46578964513L, member, dateBorrowed);
        Assertions.assertThrows(HasLateBooksException.class, ()-> library.borrowBook(3326456467846L, member, LocalDate.now()));
    }

}
