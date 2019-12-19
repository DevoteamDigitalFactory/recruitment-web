package fr.d2factory.libraryapp.member;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Resident extends Member {
    public static final BigDecimal LATE_DAY_FEE = new BigDecimal(0.20);
    public static final int FISRT_60_DAYS = 60;

    @Override
    public void payBook(int numberOfDays) throws WalletNotEnoughException {
        BigDecimal borrowFee;
        if (numberOfDays <=60) {
            borrowFee = DAY_FEE.multiply(new BigDecimal(numberOfDays));
        } else {
            borrowFee = DAY_FEE.multiply(new BigDecimal(FISRT_60_DAYS)) // the first 60 days is charged by 10 cents
                        .add(new BigDecimal(numberOfDays-FISRT_60_DAYS).multiply(LATE_DAY_FEE)); // the rest of days are charged by 20 cents
        }
        borrowFee.setScale(2, RoundingMode.HALF_EVEN);
        if (getWallet().compareTo(borrowFee) < 0){
            throw new WalletNotEnoughException("Wallet is not enough to pay the book borrow");
        } else {
            setWallet(getWallet().subtract(borrowFee));
        }

    }

}
