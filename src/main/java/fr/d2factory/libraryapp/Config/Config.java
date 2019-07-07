package fr.d2factory.libraryapp.Config;

public class Config {


    private static final int daysBeforeLateStudent = 30;
    private static final int daysBeforeLateResident = 60;
    private static final int ResidentPriceBeforeLate = 10;
    private static final int ResidentPriceafterLate  = 20;
    private static final int StudentPriceafterLate  = 15;
    private static final int StudentPricebeforeLate  = 10;
    private static final int firstYearFree = 15;

    public static int getFirstYearFree() {
        return firstYearFree;
    }

    public static int getDaysBeforeLateStudent() {
        return daysBeforeLateStudent;
    }

    public static int getDaysBeforeLateResident() {
        return daysBeforeLateResident;
    }

    public static int getResidentPriceBeforeLate() {
        return ResidentPriceBeforeLate;
    }

    public static int getResidentPriceafterLate() {
        return ResidentPriceafterLate;
    }

    public static int getStudentPriceafterLate() {
        return StudentPriceafterLate;
    }

    public static int getStudentPricebeforeLate() {
        return StudentPricebeforeLate;
    }
}
