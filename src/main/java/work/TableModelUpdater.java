package work;

import database.*;

import javax.persistence.EntityManager;
import javax.swing.table.DefaultTableModel;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class TableModelUpdater
{
    public static String[] columns;
    public static DefaultTableModel model;

    public void updateDriver()
    {
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<DriverEntity> driver_list = em.createQuery("SELECT d FROM DriverEntity d ORDER BY d.lastName, d.firstName").getResultList();
        em.getTransaction().commit();

        columns = new String[] {"idDriver","Last name","First name","License date"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        for (int i=0;i<driver_list.size();i++)
        {
            DriverEntity de = driver_list.get(i);
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String[] row = {Integer.toString(de.getIdDriver()),de.getLastName(),de.getFirstName(),date.format(de.getLicenseDate())};
            model.addRow(row);
        }

        form_GUI.table_model = model;
    }

    public void updateCar()
    {
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<CarEntity> car_list = em.createQuery("SELECT c FROM CarEntity c ORDER BY c.idCar").getResultList();
        em.getTransaction().commit();

        columns = new String[] {"idCar","Car number","Mark","Model","Color","TechInspect date","idDriver"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        for (int i=0;i<car_list.size();i++)
        {
            CarEntity ce = car_list.get(i);
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String[] row = {Integer.toString(ce.getIdCar()),ce.getCarNumber(),ce.getMark(),ce.getModel(),ce.getColor(),date.format(ce.getTechInspectDate()),Integer.toString(ce.getDriverByIdDriver().getIdDriver())};
            model.addRow(row);
        }

        form_GUI.table_model = model;
    }

    public void updateViolation()
    {
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<ViolationEntity> violation_list = em.createQuery("SELECT v FROM ViolationEntity v ORDER BY v.violationName").getResultList();
        em.getTransaction().commit();

        columns = new String[] {"idViolation","Violation name","Date","Fine","Other punishment","idCar"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        for (int i=0;i<violation_list.size();i++)
        {
            ViolationEntity ve = violation_list.get(i);
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String[] row = {Integer.toString(ve.getIdViolation()),ve.getViolationName(),date.format(ve.getDate()),Integer.toString(ve.getFine()),ve.getOtherPunishment(),Integer.toString(ve.getCarByIdCar().getIdCar())};
            model.addRow(row);
        }

        form_GUI.table_model = model;
    }

    public void updateFindViolations(String entityIdDriver) throws errorGai
    {
        if (entityIdDriver.isEmpty())
        {
            throw new errorGai("Enter idDriver");
        }
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List <DriverEntity> driver_list = em.createQuery("SELECT d FROM DriverEntity d WHERE d.idDriver='" + Integer.parseInt(entityIdDriver) + "'").getResultList();
        em.getTransaction().commit();

        if (!driver_list.isEmpty())
        {
            columns = new String[] {"idDriver","Last name","First name","Car number","Violation name","Date","Fine","Other punishment"};
            model = new DefaultTableModel(columns, 0)
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            DriverEntity de = driver_list.get(0);

            em.getTransaction().begin();
            List <CarEntity> car_list = em.createQuery("SELECT c FROM CarEntity c WHERE c.driverByIdDriver.idDriver ='" + de.getIdDriver() + "'").getResultList();
            em.getTransaction().commit();

            if (!car_list.isEmpty())
            {
                for (int i=0; i<car_list.size(); i++)
                {
                    em.getTransaction().begin();
                    List <ViolationEntity> violation_list = em.createQuery("SELECT v FROM ViolationEntity v WHERE v.carByIdCar.idCar = '" + car_list.get(i).getIdCar() + "'").getResultList();
                    em.getTransaction().commit();

                    if (!violation_list.isEmpty())
                    {
                        for (int j=0; j<violation_list.size(); j++)
                        {
                            String[] Row = new String[8];
                            Row[0]=Integer.toString(de.getIdDriver());
                            Row[1]=de.getLastName();
                            Row[2]=de.getFirstName();
                            Row[3]=car_list.get(i).getCarNumber();
                            Row[4]=violation_list.get(j).getViolationName();
                            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                            Row[5]=date.format(violation_list.get(j).getDate());
                            Row[6]=Integer.toString(violation_list.get(j).getFine());
                            Row[7]=violation_list.get(j).getOtherPunishment();
                            model.addRow(Row);
                        }
                    }
                    else
                    {
                        String[] Row = new String[8];
                        Row[0]=Integer.toString(de.getIdDriver());
                        Row[1]=de.getLastName();
                        Row[2]=de.getFirstName();
                        Row[3]=car_list.get(i).getCarNumber();
                        Row[4]="";
                        Row[5]="";
                        Row[6]="";
                        Row[7]="";
                        model.addRow(Row);
                    }
                }
            }
            else
            {
                String[] row = new String[8];
                row[0]=Integer.toString(de.getIdDriver());
                row[1]=de.getLastName();
                row[2]= de.getFirstName();
                row[3]="";
                row[4]="";
                row[5]="";
                row[6]="";
                row[7]="";
                model.addRow(row);
            }
            form_GUI.table_model = model;
        }
        else
        {
            throw new errorGai("there are no such drivers");
        }
    }

    public void updateFindDriver(String idCar) throws errorGai
    {
        if (idCar.isEmpty())
        {
            throw new errorGai("Enter idCar");
        }
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List <CarEntity> car_list = em.createQuery("SELECT c FROM CarEntity c WHERE c.idCar ='" + Integer.parseInt(idCar) + "'").getResultList();
        em.getTransaction().commit();

        if (!car_list.isEmpty())
        {
            columns = new String[]{"Last name", "First name", "Car number","Mark","Model","Color","TechInspect date"};
            model = new DefaultTableModel(columns, 0)
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String[] row = {
                    car_list.get(0).getDriverByIdDriver().getLastName(),
                    car_list.get(0).getDriverByIdDriver().getFirstName(),
                    car_list.get(0).getCarNumber(),
                    car_list.get(0).getMark(),
                    car_list.get(0).getModel(),
                    car_list.get(0).getColor(),
                    date.format(car_list.get(0).getTechInspectDate())
            };
            model.addRow(row);
            form_GUI.table_model = model;
        }
        else
        {
            throw new errorGai("there are no such cars");
        }
    }

    public void updateReport(String startDate, String endDate) throws errorGai
    {
        if (startDate.isEmpty() || endDate.isEmpty())
        {
            throw new errorGai("Enter Start/End dates");
        }
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List <ViolationEntity> violation_list = em.createQuery("SELECT v FROM ViolationEntity v WHERE v.date >= '" + Date.valueOf(startDate) + "' AND v.date <= '" + Date.valueOf(endDate) + "'").getResultList();
        em.getTransaction().commit();

        if (!violation_list.isEmpty())
        {
            columns = new String[] {"Last name","First name","Car number","Violation name","Date","Fine","Other punishment"};
            model = new DefaultTableModel(columns, 0)
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            for (int i=0; i<violation_list.size(); i++) {
                DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                String[] row = {
                        violation_list.get(i).getCarByIdCar().getDriverByIdDriver().getLastName(),
                        violation_list.get(i).getCarByIdCar().getDriverByIdDriver().getFirstName(),
                        violation_list.get(i).getCarByIdCar().getCarNumber(),
                        violation_list.get(i).getViolationName(),
                        date.format(violation_list.get(i).getDate()),
                        Integer.toString(violation_list.get(i).getFine()),
                        violation_list.get(i).getOtherPunishment()};
                model.addRow(row);
            }
            form_GUI.table_model=model;
        }
        else
        {
            throw new errorGai("no violations were found");
        }
    }
}