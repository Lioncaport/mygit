package work;

import database.*;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.List;

public class EntityUpdater
{
    public void updateDriver(int entityIdDriver,String entityLastName,String entityFirstName,String entityLicenseDate) throws errorGai
    {
        if (entityLastName.isEmpty() || entityFirstName.isEmpty() || entityLicenseDate.isEmpty())
        {
            throw new errorGai("Enter all field");
        }

        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        DriverEntity de = em.find(DriverEntity.class,entityIdDriver);
        de.setLastName(entityLastName);
        de.setFirstName(entityFirstName);
        de.setLicenseDate(Date.valueOf(entityLicenseDate));
        em.getTransaction().commit();
    }

    public void updateCar(int entityIdCar,String entityCarNumber,String entityMark,String entityModel,String entityColor,String entityTechInspectDate,String entityIdDriver) throws errorGai
    {
        if (entityCarNumber.isEmpty() || entityMark.isEmpty() || entityModel.isEmpty() || entityColor.isEmpty() || entityTechInspectDate.isEmpty() || entityIdDriver.isEmpty())
        {
            throw new errorGai("Enter all field");
        }

        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List <CarEntity> car_list = em.createQuery("SELECT c FROM CarEntity c WHERE c.carNumber='" + entityCarNumber + "'").getResultList();
        em.getTransaction().commit();

        if (!car_list.isEmpty())
        {
            throw new errorGai("Сar with this number already exists");
        }

        em.getTransaction().begin();
        List <DriverEntity> driver_list = em.createQuery("SELECT d FROM DriverEntity d WHERE d.idDriver='" + Integer.parseInt(entityIdDriver) + "'").getResultList();
        em.getTransaction().commit();

        if (!driver_list.isEmpty())
        {
            em.getTransaction().begin();
            CarEntity ce = em.find(CarEntity.class,entityIdCar);
            ce.setCarNumber(entityCarNumber);
            ce.setMark(entityMark);
            ce.setModel(entityModel);
            ce.setColor(entityColor);
            ce.setTechInspectDate(Date.valueOf(entityTechInspectDate));
            ce.setDriverByIdDriver(driver_list.get(0));
            em.getTransaction().commit();
        }
        else
        {
            throw new errorGai("there are no such drivers");
        }
    }

    public void updateViolation(int entityIdViolation,String entityViolationName,String entityDate,String entityFine,String entityOtherPunishment,String entityIdCar) throws errorGai
    {
        if (entityViolationName.isEmpty() || entityDate.isEmpty() || entityFine.isEmpty() || entityIdCar.isEmpty())
        {
            throw new errorGai("Enter all field");
        }

        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List <CarEntity> car_list = em.createQuery("SELECT c FROM CarEntity c WHERE c.idCar='" + Integer.parseInt(entityIdCar) + "'").getResultList();
        em.getTransaction().commit();

        if (!car_list.isEmpty())
        {
            em.getTransaction().begin();
            ViolationEntity ve = em.find(ViolationEntity.class, entityIdViolation);
            ve.setViolationName(entityViolationName);
            ve.setDate(Date.valueOf(entityDate));
            ve.setFine(Integer.parseInt(entityFine));
            ve.setOtherPunishment(entityOtherPunishment);
            ve.setCarByIdCar(car_list.get(0));
            em.getTransaction().commit();
        }
        else
        {
            throw new errorGai("there are no such cars");
        }
    }
}