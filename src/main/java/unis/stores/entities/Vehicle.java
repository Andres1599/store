package unis.stores.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "vehicle")
public class Vehicle {

    @Id
    @Column(name = "universal_code", nullable = false)
    private String universalCode;

    @OneToOne
    private Brand brand;

    @OneToOne
    private Line line;

    @Column(name = "year")
    private String year;

    public Vehicle() {
    }

    public String getUniversalCode() {
        return universalCode;
    }

    public void setUniversalCode(String universalCode) {
        this.universalCode = universalCode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Vehicle guest = (Vehicle) obj;
        return this.universalCode.equals(guest.getUniversalCode());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((universalCode == null) ? 0 : universalCode.hashCode());
        result = prime * result
                + ((getBrand() == null) ? 0 : getBrand().hashCode());
        result = prime * result
                + ((getLine() == null) ? 0 : getLine().hashCode());
        result = prime * result
                + ((getYear() == null) ? 0 : getYear().hashCode());
        return result;
    }
}
