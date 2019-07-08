package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.Config;
import fr.d2factory.libraryapp.library.WalletNotEnoughException;

public class Resident extends Member {

    @Override
    public void payBook(int numberOfDays) {
        int moneyToPay;
        if (numberOfDays <= Config.getDaysBeforeLateResident()) {
            moneyToPay = numberOfDays * Config.getResidentPriceBeforeLate();
        } else {
            moneyToPay = (numberOfDays - Config.getDaysBeforeLateResident()) * Config.getResidentPriceafterLate()
                    + Config.getResidentPriceBeforeLate() * Config.getDaysBeforeLateResident();
        }

        if(moneyToPay > this.getWallet()) {
            throw new WalletNotEnoughException();
        } else {
            this.setWallet(this.getWallet() - moneyToPay);
        }
    }

    public Resident(int wallet) {
        super(wallet);
        MaxDaysToKeep = Config.getDaysBeforeLateResident();
    }
}
