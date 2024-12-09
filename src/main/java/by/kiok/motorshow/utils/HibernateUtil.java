package by.kiok.motorshow.utils;

import by.kiok.motorshow.models.Car;
import by.kiok.motorshow.models.CarShowroom;
import by.kiok.motorshow.models.Category;
import by.kiok.motorshow.models.Client;
import by.kiok.motorshow.models.Review;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;

@UtilityClass
public class HibernateUtil {

    @Getter
    private final static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Car.class)
                    .addAnnotatedClass(CarShowroom.class)
                    .addAnnotatedClass(Category.class)
                    .addAnnotatedClass(Client.class)
                    .addAnnotatedClass(Review.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void initializeIndex(Session session) {
        try {
            SearchSession searchSession = Search.session(session);
            MassIndexer indexer = searchSession.massIndexer(Review.class);
            indexer.startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void shutdownSession() {
        getSessionFactory().close();
    }

    public static void dropAllTables() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            String sql = "DROP SCHEMA public CASCADE; CREATE SCHEMA public;";
            Query query = session.createNativeQuery(sql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        HibernateUtil.shutdownSession();
    }
}
