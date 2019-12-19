package fr.d2factory.libraryapp.member;

public class WalletNotEnoughException extends RuntimeException {

    public WalletNotEnoughException(String message) {
        super(message);
    }

}
