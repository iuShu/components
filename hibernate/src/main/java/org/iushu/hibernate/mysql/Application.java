package org.iushu.hibernate.mysql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.iushu.hibernate.mysql.entity.Department;
import org.iushu.hibernate.mysql.entity.Staff;

import java.util.Random;

public class Application {

    static void gettingStarted() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Staff staff = session.get(Staff.class, 3);
        System.out.println(staff);

        session.getTransaction().commit();
        sessionFactory.close();
    }

    static void officialSample() {


    }

    static void autoDDL() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Department department = session.get(Department.class, 1);
        if (department == null) {
            department = new Department();
            department.setManager(2);
            department.setName("R&D " + new Random().nextInt(100));
            session.saveOrUpdate(department);
        }

        session.getTransaction().commit();
        sessionFactory.close();
    }

    public static void main(String[] args) {
//        gettingStarted();
        autoDDL();
    }

}
