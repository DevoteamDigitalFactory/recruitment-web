package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.book.AlreadyExistBookException;
import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.library.Library;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {

    public static final BigDecimal DAY_FEE = new BigDecimal(0.10);

    private String name;

    private List<Book> books = new ArrayList<>();

    /**
     * An initial sum of money the member has
     */
    private static BigDecimal wallet;

    /**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     */
    public abstract void payBook(int numberOfDays) throws WalletNotEnoughException;

    public BigDecimal getWallet() {
        return wallet.setScale(2, RoundingMode.HALF_EVEN);
    }

    public void setWallet(BigDecimal wallet) {
        this.wallet = wallet.setScale(2, RoundingMode.HALF_EVEN);
    }

    public void addBook(Book book) {
        if (books.contains(book)) {
            throw new AlreadyExistBookException("Book with ISBN "+book.getISBN()+" is already borrowed");
        } else {
            books.add(book);
        }
    }

    public void removeBook(Book book){
        books.remove(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(name, member.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
