package by.kiok.motorshow.services.impl;

import by.kiok.motorshow.models.CarShowroom;
import by.kiok.motorshow.services.CarShowroomService;
import by.kiok.motorshow.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CarShowroomServiceImpl implements CarShowroomService {

    @Override
    public CarShowroom createCarShowroom(CarShowroom carShowroom) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(carShowroom);

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return carShowroom;
    }

    @Override
    public void removeCarShowroom(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            CarShowroom current = session.get(CarShowroom.class, id);
            session.remove(current);

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCarShowroom(CarShowroom carShowroomUpdate, Long id) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            CarShowroom carShowroom = session.get(CarShowroom.class, id);

            carShowroom.setName(carShowroomUpdate.getName());
            carShowroom.setAddress(carShowroomUpdate.getAddress());

            session.merge(carShowroom);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CarShowroom> findAllCarShowrooms() {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            List<CarShowroom> carShowrooms = session.createQuery("from CarShowroom").list();
            transaction.commit();

            return carShowrooms;
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return List.of();
    }
}
