package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.library.NoEnoughFundInTheWallet;

public class ResidentMember extends Member {


    public ResidentMember(String id, String firstName, String lastName, float wallet, Profil config) {
        super(id, firstName, lastName, wallet, config);
    }


    @Override
    public void payBook(int numberOfDays) {
        int maxPeriod =  getConfig().getMaxPeriod();
        float amountToPay = Math.min(maxPeriod, numberOfDays ) * getConfig().getAmountChargedBefore()
                + Math.max(0, numberOfDays - maxPeriod) * getConfig().getAmountChargedAfter();
        if(amountToPay < getWallet()){
            setWallet(getWallet()-amountToPay);
        }else{
            throw new NoEnoughFundInTheWallet();

        }
    }
}
