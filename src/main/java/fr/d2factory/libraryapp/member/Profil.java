package fr.d2factory.libraryapp.member;

public enum Profil {

    RESIDENT(60, 0, 0.10f, 0.20f),
    STUDENT(30,0,0.10f,0.10f),
    STUDENT_1ST_YEAR(30,15,0.10f,0.10f);

    private final int maxPeriod;
    private final int freePeriod;
    private final float amountChargedBefore;
    private final float amountChargedAfter;

    Profil(int maxPeriod, int freePeriod, float amountChargedBefore, float amountChargedAfter) {
        this.maxPeriod = maxPeriod;
        this.freePeriod = freePeriod;
        this.amountChargedBefore = amountChargedBefore;
        this.amountChargedAfter = amountChargedAfter;
    }

    public int getMaxPeriod() {
        return maxPeriod;
    }

    public int getFreePeriod() {
        return freePeriod;
    }

    public float getAmountChargedBefore() {
        return amountChargedBefore;
    }

    public float getAmountChargedAfter() {
        return amountChargedAfter;
    }
}
