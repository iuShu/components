package org.iushu.hibernate.mysql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.cfgxml.spi.LoadedConfig;
import org.hibernate.boot.cfgxml.spi.MappingReference;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.event.spi.LoadEvent;
import org.hibernate.event.spi.LoadEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.persister.entity.EntityPersister;
import org.iushu.hibernate.HibernateConcepts;
import org.iushu.hibernate.mysql.entity.Department;
import org.iushu.hibernate.mysql.entity.Staff;
import org.iushu.hibernate.mysql.jpa.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

import static org.hibernate.boot.registry.StandardServiceRegistryBuilder.DEFAULT_CFG_RESOURCE_NAME;

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

    /**
     * @see org.hibernate.service.ServiceRegistry
     * @see org.hibernate.boot.MetadataSources
     * @see org.hibernate.internal.SessionFactoryImpl
     * @see org.hibernate.internal.SessionImpl
     */
    static void officialSample() {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        Metadata metadata = metadataSources.buildMetadata();
        SessionFactory sessionFactory = metadata.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Staff staff = session.get(Staff.class, 4);
        System.out.println(staff);

        session.getTransaction().commit();
        sessionFactory.close();
    }

    /**
     * Annotated at data object
     * @see Student
     */
    static void annotationCase() {
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        LoadedConfig loadedConfig = serviceRegistryBuilder.getConfigLoader().loadConfigXmlResource(DEFAULT_CFG_RESOURCE_NAME);

        // adding mapping reference manually (or adding at mapping tag in cfg.xml)
        List<MappingReference> mappingReferences = loadedConfig.getMappingReferences();
        mappingReferences.add(new MappingReference(MappingReference.Type.CLASS, "org.iushu.hibernate.mysql.jpa.Student"));

        StandardServiceRegistry serviceRegistry = serviceRegistryBuilder.configure(loadedConfig).build();
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Student student = session.get(Student.class, 1);
        if (student == null) {
            Random random = new Random();
            student = new Student();
            student.setName("Katy Perry");
            student.setGender((byte) 1);
            student.setAge(random.nextInt(18));
            student.setClazz(random.nextInt(10));
            Integer newId = (Integer) session.save(student);
            student.setId(newId);
        }
        System.out.println(student);

        session.getTransaction().commit();
        sessionFactory.close();
    }

    static void annotationJPACase() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.iushu.hibernate.mysql.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Student student = entityManager.createQuery("from Student", Student.class).getSingleResult();
        System.out.println(student);

        entityManager.getTransaction().commit();
        entityManagerFactory.close();
    }

    /**
     * more details about auto ddl
     * @see HibernateConcepts#autoDDL()
     */
    static void autoDDL() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Department department = session.get(Department.class, 1);
        System.out.println(department);

        session.getTransaction().commit();
        sessionFactory.close();
    }

    /**
     * Save a transient object
     *
     * @see org.hibernate.Session#save(Object)
     * @see org.hibernate.internal.SessionImpl#save(Object)
     * @see org.hibernate.event.spi.SaveOrUpdateEvent
     * @see org.hibernate.internal.SessionImpl#fireSave(SaveOrUpdateEvent)
     * @see org.hibernate.event.internal.DefaultSaveOrUpdateEventListener#onSaveOrUpdate(SaveOrUpdateEvent)
     * @see org.hibernate.event.internal.DefaultSaveEventListener#performSaveOrUpdate(SaveOrUpdateEvent)
     * @see org.hibernate.event.internal.DefaultSaveOrUpdateEventListener#saveWithGeneratedOrRequestedId(SaveOrUpdateEvent)
     */
    static void persistentSave() {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Random random = new Random();
        Department department = new Department();
        department.setName("R&D " + random.nextInt(100));
        department.setManager(random.nextInt(8));
        Integer newId = (Integer) session.save(department);
        department.setId(newId);
        System.out.println(department);

        session.getTransaction().commit();
        sessionFactory.close();
    }

    /**
     * @see org.hibernate.internal.SessionImpl#get(Class, Serializable)
     * @see org.hibernate.internal.SessionImpl.IdentifierLoadAccessImpl#load(Serializable)  
     * @see org.hibernate.internal.SessionImpl.IdentifierLoadAccessImpl#doLoad(Serializable)
     * @see org.hibernate.event.internal.DefaultLoadEventListener#doLoad(LoadEvent, EntityPersister, EntityKey, LoadEventListener.LoadType)
     * @see org.hibernate.event.internal.DefaultLoadEventListener#loadFromDatasource(LoadEvent, EntityPersister)
     */
    static void persistentDataObject() {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Department department = session.get(Department.class, 3);
        System.out.println(department);

        session.getTransaction().commit();
        sessionFactory.close();
    }

    public static void main(String[] args) {
//        gettingStarted();
//        officialSample();
//        annotationCase();
        annotationJPACase();
//        autoDDL();
//        persistentSave();
//        persistentDataObject();
    }

}
