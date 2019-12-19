package fr.d2factory.libraryapp.book;

public class AlreadyExistBookException extends RuntimeException {

    public AlreadyExistBookException(String message){
        super(message);
    }
}
