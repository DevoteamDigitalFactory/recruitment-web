package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.Config;
import fr.d2factory.libraryapp.library.WalletNotEnoughException;

public class Student extends Member {

    private boolean firstYear;

    public boolean isFirstYear() {
        return firstYear;
    }

    public Student(boolean firstYear, int wallet) {
        super(wallet);
        this.firstYear = firstYear;
        MaxDaysToKeep = Config.getDaysBeforeLateStudent();
    }
    @Override
    public void payBook(int numberOfDays) {
        int moneyToPay;
        if(numberOfDays <= MaxDaysToKeep){
            moneyToPay = numberOfDays * Config.getStudentPricebeforeLate();
        } else {
            moneyToPay = (numberOfDays - MaxDaysToKeep) * Config.getFirstYearFree()
                    + MaxDaysToKeep * Config.getStudentPricebeforeLate();
        }

        if(this.isFirstYear()) {
            moneyToPay -= (numberOfDays > Config.getFirstYearFree() ? Config.getFirstYearFree(): numberOfDays)
                    * Config.getStudentPricebeforeLate();
        }
        if(this.getWallet() > moneyToPay) {
            this.setWallet(this.getWallet() - moneyToPay);
        }else {
            throw new WalletNotEnoughException();
        }
    }




}
