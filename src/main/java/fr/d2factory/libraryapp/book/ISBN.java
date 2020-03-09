package fr.d2factory.libraryapp.book;

import java.io.Serializable;

public class ISBN {
    long isbnCode;

    public ISBN(){
    }

    public ISBN(long isbnCode) {
        this.isbnCode = isbnCode;
    }


    public long getIsbnCode() {
        return isbnCode;
    }

    public void setIsbnCode(long isbnCode) {
        this.isbnCode = isbnCode;
    }

    @Override
    public int hashCode(){
        return Long.hashCode(isbnCode);
    }

    @Override
    public boolean equals(Object o){
        boolean result = false;
        if(o instanceof ISBN){
            ISBN isbn = (ISBN)o;
            result = isbnCode == isbn.getIsbnCode();
        }
        return result;
    }
}
