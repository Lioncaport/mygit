package database;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "car", schema = "gai")
public class CarEntity {
    private int idCar;
    private String carNumber;
    private String mark;
    private String model;
    private String color;
    private Date techInspectDate;
    private DriverEntity driverByIdDriver;

    @Id
    @Column(name = "idCar")
    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    @Basic
    @Column(name = "CarNumber")
    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    @Basic
    @Column(name = "Mark")
    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Basic
    @Column(name = "Model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "Color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Basic
    @Column(name = "TechInspectDate")
    public Date getTechInspectDate() {
        return techInspectDate;
    }

    public void setTechInspectDate(Date techInspectDate) {
        this.techInspectDate = techInspectDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarEntity carEntity = (CarEntity) o;

        if (idCar != carEntity.idCar) return false;
        if (carNumber != null ? !carNumber.equals(carEntity.carNumber) : carEntity.carNumber != null) return false;
        if (mark != null ? !mark.equals(carEntity.mark) : carEntity.mark != null) return false;
        if (model != null ? !model.equals(carEntity.model) : carEntity.model != null) return false;
        if (color != null ? !color.equals(carEntity.color) : carEntity.color != null) return false;
        if (techInspectDate != null ? !techInspectDate.equals(carEntity.techInspectDate) : carEntity.techInspectDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCar;
        result = 31 * result + (carNumber != null ? carNumber.hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (techInspectDate != null ? techInspectDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idDriver", referencedColumnName = "idDriver", nullable = false)
    public DriverEntity getDriverByIdDriver() {
        return driverByIdDriver;
    }

    public void setDriverByIdDriver(DriverEntity driverByIdDriver) {
        this.driverByIdDriver = driverByIdDriver;
    }
}
