package fr.d2factory.libraryapp.book;

public class ISBN {
    long isbnCode;

    public ISBN(long isbnCode) {
        this.isbnCode = isbnCode;
    }

    public long getIsbnCode() {
        return isbnCode;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(isbnCode);
    }
}
