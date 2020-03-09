package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.library.NoEnoughFundInTheWallet;

public class StudentMember extends Member {

    public StudentMember(String id, String firstName, String lastName, float wallet, Profil config) {
        super(id, firstName, lastName, wallet, config);
    }


    @Override
    public void payBook(int numberOfDays) {
        float amountToPay = Math.max(0, numberOfDays-getConfig().getFreePeriod()) * getConfig().getAmountChargedBefore();
        if(amountToPay < getWallet()){
            setWallet(getWallet()-amountToPay);
        }else{
            throw new NoEnoughFundInTheWallet();
        }
    }
}
