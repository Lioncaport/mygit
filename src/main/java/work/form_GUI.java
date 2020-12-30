package work;

import database.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import database.DriverEntity;

import javax.persistence.EntityManager;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class form_GUI extends JDialog
{
    private JPanel ExternalPanel;
    private JTabbedPane TabbedPane;
    private JPanel driverPanel;
    private JButton AddUpdateButton;
    private JTextField driverLastnameTextField;
    private JTextField driverFirstnameTextField;
    private JTextField driverLicensedateTextField;
    private JButton deleteButton;
    private JPanel carPanel;
    private JButton renewButton;
    private JTextField driverViolationsTextField;
    private JButton driverOkButton;
    private JTextField carFinddirverTextField;
    private JButton carOkButton;
    private JPanel violationPanel;
    private JButton exportReportButton;
    private JPanel FreeTablePanel;
    private JTable table;
    private JTextField carNumberTextField;
    private JTextField carMarkTextField;
    private JTextField carModelTextField;
    private JTextField carColorTextField;
    private JTextField carTechInspectdateTextField;
    private JTextField caridDriverTextField;
    private JTextField violationNameTextField;
    private JTextField violationDateTextField;
    private JTextField violationFineTextField;
    private JTextField violationOtherpunishmentTextField;
    private JTextField violationidCarTextField;
    private JTextField violationStartdateTextField;
    private JTextField violationEnddateTextField;
    private JPanel TablePanel;
    private JScrollPane TableScrollPane;
    private JTextField countTextField;
    private JButton reportOkButton;

    public static DefaultTableModel table_model;

    public static final SessionFactory ourSessionFactory;
    static
    {
        Configuration configuration = new Configuration();
        configuration.configure();
        ourSessionFactory = configuration.buildSessionFactory();
    }
    public static Session getSession() throws HibernateException
    {
        return ourSessionFactory.openSession();
    }

    TableModelUpdater tmUpdater = new TableModelUpdater();
    EntityAdder entityAdder = new EntityAdder();
    EntityUpdater entityUpdater = new EntityUpdater();

    public static int count_of_drivers()
    {
        EntityManager em = ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List count = em.createQuery("select d from DriverEntity d").getResultList();
        em.getTransaction().commit();

        return(count.size());
    }

    public static int count_of_cars()
    {
        EntityManager em = ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List count = em.createQuery("select c.idCar from CarEntity c").getResultList();
        em.getTransaction().commit();

        return(count.size());
    }

    public static int count_of_violations()
    {
        EntityManager em = ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List count = em.createQuery("select v.idViolation from ViolationEntity v").getResultList();
        em.getTransaction().commit();

        return(count.size());
    }

    public void clear_GUI()
    {
        AddUpdateButton.setText("Add new record");
        driverLastnameTextField.setText("");
        driverFirstnameTextField.setText("");
        driverLicensedateTextField.setText("");
        driverViolationsTextField.setText("");

        carFinddirverTextField.setText("");
        carNumberTextField.setText("");
        carMarkTextField.setText("");
        carModelTextField.setText("");
        carColorTextField.setText("");
        carTechInspectdateTextField.setText("");
        caridDriverTextField.setText("");

        violationNameTextField.setText("");
        violationDateTextField.setText("");
        violationFineTextField.setText("");
        violationOtherpunishmentTextField.setText("");
        violationidCarTextField.setText("");
        violationStartdateTextField.setText("");
        violationEnddateTextField.setText("");

        int tabIndex = TabbedPane.getSelectedIndex();
        switch (tabIndex)
        {
            case 0:
                tmUpdater.updateDriver();
                countTextField.setText(Integer.toString(count_of_drivers()));
                break;
            case 1:
                tmUpdater.updateCar();
                countTextField.setText(Integer.toString(count_of_cars()));
                break;
            case 2:
                tmUpdater.updateViolation();
                countTextField.setText(Integer.toString(count_of_violations()));
                break;
        }
        table.setModel(table_model);
    }

    public form_GUI()
    {
        clear_GUI();
        setContentPane(ExternalPanel);
        //setModal(true);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                exit();
            }
        });

        TabbedPane.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                clear_GUI();
            }
        });

        table.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                AddUpdateButton.setText("Update record");
                int tabIndex = TabbedPane.getSelectedIndex();

                switch (tabIndex) {
                    case 0:
                        String driverLastName = table_model.getValueAt(table.getSelectedRow(), 1).toString();
                        String driverFirstName = table_model.getValueAt(table.getSelectedRow(), 2).toString();
                        String driverLicenseDate = table_model.getValueAt(table.getSelectedRow(), 3).toString();
                        driverLastnameTextField.setText(driverLastName);
                        driverFirstnameTextField.setText(driverFirstName);
                        driverLicensedateTextField.setText(driverLicenseDate);
                        break;
                    case 1:
                        String carNumber = table_model.getValueAt(table.getSelectedRow(), 1).toString();
                        String carMark = table_model.getValueAt(table.getSelectedRow(), 2).toString();
                        String carModel = table_model.getValueAt(table.getSelectedRow(), 3).toString();
                        String carColor = table_model.getValueAt(table.getSelectedRow(), 4).toString();
                        String carTechInspectDate = table_model.getValueAt(table.getSelectedRow(), 5).toString();
                        String carIdDriver = table_model.getValueAt(table.getSelectedRow(), 6).toString();
                        carNumberTextField.setText(carNumber);
                        carMarkTextField.setText(carMark);
                        carModelTextField.setText(carModel);
                        carColorTextField.setText(carColor);
                        carTechInspectdateTextField.setText(carTechInspectDate);
                        caridDriverTextField.setText(carIdDriver);
                        break;
                    case 2:
                        String violationName = table_model.getValueAt(table.getSelectedRow(), 1).toString();
                        String violationDate = table_model.getValueAt(table.getSelectedRow(), 2).toString();
                        String violationFine = table_model.getValueAt(table.getSelectedRow(), 3).toString();
                        String violationOtherPunishment = table_model.getValueAt(table.getSelectedRow(), 4).toString();
                        String violationIdCar = table_model.getValueAt(table.getSelectedRow(), 5).toString();
                        violationNameTextField.setText(violationName);
                        violationDateTextField.setText(violationDate);
                        violationFineTextField.setText(violationFine);
                        violationidCarTextField.setText(violationIdCar);
                        violationOtherpunishmentTextField.setText(violationOtherPunishment);
                        break;
                }
            }
        });

        AddUpdateButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int tabIndex = TabbedPane.getSelectedIndex();
                int rowIndex = table.getSelectedRow();

                if (rowIndex==-1)
                {
                    switch (tabIndex)
                    {
                        case 0:
                            String driverLastName = driverLastnameTextField.getText();
                            String driverFirstName = driverFirstnameTextField.getText();
                            String driverLicenseDate = driverLicensedateTextField.getText();

                            try
                            {
                                entityAdder.addDriver(driverLastName, driverFirstName, driverLicenseDate);
                                tmUpdater.updateDriver();
                            }
                            catch (errorGai error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 1:
                            String carNumber = carNumberTextField.getText();
                            String carMark = carMarkTextField.getText();
                            String carModel = carModelTextField.getText();
                            String carColor = carColorTextField.getText();
                            String carTechInspectDate = carTechInspectdateTextField.getText();
                            String carIdDriver = caridDriverTextField.getText();

                            try
                            {
                                entityAdder.addCar(carNumber, carMark, carModel, carColor, carTechInspectDate, carIdDriver);
                                tmUpdater.updateCar();
                            }
                            catch (errorGai error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 2:
                            String violationName = violationNameTextField.getText();
                            String violationDate = violationDateTextField.getText();
                            String violationFine = violationFineTextField.getText();
                            String violationOtherPunishment = violationOtherpunishmentTextField.getText();
                            String violationIdCar = violationidCarTextField.getText();

                            try
                            {
                                entityAdder.addViolation(violationName, violationDate, violationFine, violationOtherPunishment, violationIdCar);
                                tmUpdater.updateViolation();
                            }
                            catch (errorGai error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                    }
                }
                else
                {
                    EntityManager em = ourSessionFactory.createEntityManager();
                    switch (tabIndex)
                    {
                        case 0:
                            em.getTransaction().begin();
                            DriverEntity de = em.find(DriverEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                            em.getTransaction().commit();

                            String driverLastName = driverLastnameTextField.getText();
                            String driverFirstName = driverFirstnameTextField.getText();
                            String driverLicenseDate = driverLicensedateTextField.getText();

                            try
                            {
                                entityUpdater.updateDriver(de.getIdDriver(),driverLastName, driverFirstName, driverLicenseDate);
                                tmUpdater.updateDriver();
                            }
                            catch (errorGai error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 1:
                            em.getTransaction().begin();
                            CarEntity ce = em.find(CarEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                            em.getTransaction().commit();

                            String carNumber = carNumberTextField.getText();
                            String carMark = carMarkTextField.getText();
                            String carModel = carModelTextField.getText();
                            String carColor = carColorTextField.getText();
                            String carTechInspectDate = carTechInspectdateTextField.getText();
                            String carIdDriver = caridDriverTextField.getText();

                            try
                            {
                                entityUpdater.updateCar(ce.getIdCar(),carNumber, carMark, carModel, carColor, carTechInspectDate, carIdDriver);
                                tmUpdater.updateCar();
                            }
                            catch (errorGai error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 2:
                            em.getTransaction().begin();
                            ViolationEntity ve = em.find(ViolationEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                            em.getTransaction().commit();

                            String violationName = violationNameTextField.getText();
                            String violationDate = violationDateTextField.getText();
                            String violationFine = violationFineTextField.getText();
                            String violationOtherPunishment = violationOtherpunishmentTextField.getText();
                            String violationIdCar = violationidCarTextField.getText();

                            try
                            {
                                entityUpdater.updateViolation(ve.getIdViolation(),violationName, violationDate, violationFine, violationOtherPunishment, violationIdCar);
                                tmUpdater.updateViolation();
                            }
                            catch (errorGai error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                    }
                }

                table.setModel(table_model);
                clear_GUI();
            }
        });

        deleteButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int tabIndex = TabbedPane.getSelectedIndex();
                int rowIndex = table.getSelectedRow();
                EntityManager em = ourSessionFactory.createEntityManager();
                em.getTransaction().begin();

                switch (tabIndex)
                {
                    case 0:
                        DriverEntity de = em.find(DriverEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                        em.remove(de);
                        em.getTransaction().commit();
                        tmUpdater.updateDriver();
                        break;
                    case 1:
                        CarEntity ce = em.find(CarEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                        em.remove(ce);
                        em.getTransaction().commit();
                        tmUpdater.updateCar();
                        break;
                    case 2:
                        ViolationEntity ve = em.find(ViolationEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                        em.remove(ve);
                        em.getTransaction().commit();
                        tmUpdater.updateViolation();
                        break;
                }
                table.setModel(table_model);
                clear_GUI();
            }
        });

        renewButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clear_GUI();
            }
        });

        driverOkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String driverIdCar = driverViolationsTextField.getText();
                try
                {
                    tmUpdater.updateFindViolations(driverIdCar);
                    table.setModel(table_model);
                    countTextField.setText(Integer.toString(table.getRowCount()));
                }
                catch (errorGai error)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        carOkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String idCar = carFinddirverTextField.getText();
                try
                {
                    tmUpdater.updateFindDriver(idCar);
                    table.setModel(table_model);
                    countTextField.setText(Integer.toString(table.getRowCount()));
                }
                catch (errorGai error)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        reportOkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String startDate = violationStartdateTextField.getText();
                String endDate = violationEnddateTextField.getText();
                try
                {
                    tmUpdater.updateReport(startDate,endDate);
                    table.setModel(table_model);
                    countTextField.setText(Integer.toString(table.getRowCount()));
                }
                catch (errorGai error)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exportReportButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String startDate = violationStartdateTextField.getText();
                String endDate = violationEnddateTextField.getText();
                try
                {
                    tmUpdater.updateReport(startDate,endDate);
                    table.setModel(table_model);
                    ExportPdf.createPdf("Reports/period1.pdf", new String[]{"Last name","First name","Car number","Violation name","Date","Fine","Other punishment"}, table_model);
                }
                catch (errorGai error)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception exception)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, "Cant export: " + exception.getClass() + ':' + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void exit()
    {
        dispose();
    }

    public static void main(String[] args)
    {
        form_GUI dialog = new form_GUI();
        dialog.setTitle("GAI");
        dialog.setSize(1000,380);
        dialog.setLocation(300,150);
        dialog.setVisible(true);
    }
}