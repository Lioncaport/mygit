package database;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "driver", schema = "gai")
public class DriverEntity {
    private int idDriver;
    private String lastName;
    private String firstName;
    private Date licenseDate;

    @Id
    @Column(name = "idDriver")
    public int getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(int idDriver) {
        this.idDriver = idDriver;
    }

    @Basic
    @Column(name = "LastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "FirstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LicenseDate")
    public Date getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(Date licenseDate) {
        this.licenseDate = licenseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriverEntity that = (DriverEntity) o;

        if (idDriver != that.idDriver) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (licenseDate != null ? !licenseDate.equals(that.licenseDate) : that.licenseDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDriver;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (licenseDate != null ? licenseDate.hashCode() : 0);
        return result;
    }
}
