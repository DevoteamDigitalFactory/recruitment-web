package fr.d2factory.libraryapp.member;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Student extends Member {

    private static final int FREE_DAYS = 15;
    private LocalDate studyingStartDate;

    @Override
    public void payBook(int numberOfDays) throws WalletNotEnoughException{
        BigDecimal borrowFee;
        if (studyingStartDate.getYear() == LocalDate.now().getYear()){
            // paying only the rest of after from 15 days
            if (numberOfDays > FREE_DAYS) {
                int restOfDays = numberOfDays - FREE_DAYS;
                borrowFee = DAY_FEE.multiply(new BigDecimal(restOfDays));
            } else {
                borrowFee = new BigDecimal(0);
            }
        } else {
            borrowFee = DAY_FEE.multiply(new BigDecimal(numberOfDays));
        }

        if (getWallet().compareTo(borrowFee) < 0){
            throw new WalletNotEnoughException("Wallet is not enough to pay the book borrow");
        } else {
            setWallet(getWallet().subtract(borrowFee));
        }
    }

    public LocalDate getStudyingStartDate() {
        return studyingStartDate;
    }

    public void setStudyingStartDate(LocalDate studyingStartDate) {
        this.studyingStartDate = studyingStartDate;
    }
}
