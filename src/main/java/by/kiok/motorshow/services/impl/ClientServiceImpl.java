package by.kiok.motorshow.services.impl;

import by.kiok.motorshow.models.Car;
import by.kiok.motorshow.models.Client;
import by.kiok.motorshow.services.ClientService;
import by.kiok.motorshow.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Override
    public Client createClient(Client client) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(client);
            transaction.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return client;
    }

    @Override
    public void updateClient(Client clientUpdate, Long id) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = session.get(Client.class, id);
            client.setName(clientUpdate.getName());
            client.setContact(clientUpdate.getContact());
            session.update(client);

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteClient(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = session.get(Client.class, id);
            session.remove(client);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean buyCar(Long clientId, Long carId) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = session.get(Client.class, clientId);
            Car car = session.get(Car.class, carId);
            car.setShowroom(null);
            List<Car> cars = List.of(car);
            List<Client> clients = List.of(client);
            client.setCars(cars);
            car.setClients(clients);
            session.update(client);
            session.update(car);

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public List<Client> findAllClients(int pageNumber, int pageSize) {
        List<Client> clients = new ArrayList<>();
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = """
                    SELECT c FROM Clients c
                     JOIN FETCH c.cars
                     JOIN FETCH c.reviews""";
            Query<Client> query = session.createQuery(hql, Client.class)
                    .setFirstResult((pageNumber - 1) * pageSize)
                    .setMaxResults(pageSize);
            clients = query.list();

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
