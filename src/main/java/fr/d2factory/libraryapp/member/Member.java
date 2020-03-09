package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.library.Library;

import java.util.Objects;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {


    private String id;

    private String firstName;

    private String lastName;

    /**
     * An initial sum of money the member has
     */
    private float wallet;

    private Profil config;


    public Member( String id, String firstName, String lastName,float wallet, Profil config) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.wallet = wallet;
        this.config = config;
    }

    /**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     */
    public abstract void payBook(int numberOfDays);


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setConfig(Profil config) {
        this.config = config;
    }


    public float getWallet() {

        return wallet;
    }

    public void setWallet(float wallet) {

        this.wallet = wallet;
    }

    public Profil getConfig() {
        return config;
    }


    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if(o instanceof Member){
            Member member = (Member)o;
            result = id.equals(member.id) &&
                    firstName.equals(member.firstName) &&
                    lastName.equals(member.lastName);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
