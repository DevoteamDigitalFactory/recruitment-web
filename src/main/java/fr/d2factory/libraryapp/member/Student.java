package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.library.WalletNotEnoughException;

public class Student extends Member {

    private boolean firstYear;

    public boolean isFirstYear() {
        return firstYear;
    }

    public Student(boolean firstYear, int wallet) {
        super(wallet);
        this.firstYear = firstYear;
        MaxDaysToKeep = 30;
    }
    @Override
    public void payBook(int numberOfDays) {
        int moneyToPay;
        if(numberOfDays <= 30){
            moneyToPay = numberOfDays * 10;
        } else {
            moneyToPay = (numberOfDays - 30) * 15 + 30 * 10;
        }

        if(this.isFirstYear()) {
            moneyToPay -= (numberOfDays > 15 ? 15: numberOfDays)* 10;
        }
        if(this.getWallet() > moneyToPay) {
            this.setWallet(this.getWallet() - moneyToPay);
        }else {
            throw new WalletNotEnoughException();
        }
    }




}
