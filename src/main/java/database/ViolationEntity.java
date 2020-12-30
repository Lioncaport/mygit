package database;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "violation", schema = "gai")
public class ViolationEntity {
    private int idViolation;
    private String violationName;
    private Date date;
    private int fine;
    private String otherPunishment;
    private CarEntity carByIdCar;

    @Id
    @Column(name = "idViolation")
    public int getIdViolation() {
        return idViolation;
    }

    public void setIdViolation(int idViolation) {
        this.idViolation = idViolation;
    }

    @Basic
    @Column(name = "ViolationName")
    public String getViolationName() {
        return violationName;
    }

    public void setViolationName(String violationName) {
        this.violationName = violationName;
    }

    @Basic
    @Column(name = "Date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "Fine")
    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    @Basic
    @Column(name = "OtherPunishment")
    public String getOtherPunishment() {
        return otherPunishment;
    }

    public void setOtherPunishment(String otherPunishment) {
        this.otherPunishment = otherPunishment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViolationEntity that = (ViolationEntity) o;

        if (idViolation != that.idViolation) return false;
        if (fine != that.fine) return false;
        if (violationName != null ? !violationName.equals(that.violationName) : that.violationName != null)
            return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (otherPunishment != null ? !otherPunishment.equals(that.otherPunishment) : that.otherPunishment != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idViolation;
        result = 31 * result + (violationName != null ? violationName.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + fine;
        result = 31 * result + (otherPunishment != null ? otherPunishment.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idCar", referencedColumnName = "idCar", nullable = false)
    public CarEntity getCarByIdCar() {
        return carByIdCar;
    }

    public void setCarByIdCar(CarEntity carByIdCar) {
        this.carByIdCar = carByIdCar;
    }
}
