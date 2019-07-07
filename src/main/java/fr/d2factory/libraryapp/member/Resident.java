package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.library.WalletNotEnoughException;

public class Resident extends Member {

    @Override
    public void payBook(int numberOfDays) {
        int moneyToPay;
        if (numberOfDays <= 60) {
            moneyToPay = numberOfDays * 10;
        } else {
            moneyToPay = (numberOfDays - 60) * 20 + 10 * 60;
        }

        if(moneyToPay > this.getWallet()) {
            throw new WalletNotEnoughException();
        } else {
            this.setWallet(this.getWallet() - moneyToPay);
        }
    }

    public Resident(int wallet) {
        super(wallet);
        MaxDaysToKeep = 60;
    }
}
