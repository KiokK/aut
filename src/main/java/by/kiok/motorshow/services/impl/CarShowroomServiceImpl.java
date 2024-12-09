package by.kiok.motorshow.services.impl;

import by.kiok.motorshow.dtos.req.CarShowroomDtoReq;
import by.kiok.motorshow.dtos.resp.CarShowroomInfoDto;
import by.kiok.motorshow.dtos.resp.CarShowroomPageDto;
import by.kiok.motorshow.exceptions.EntityException;
import by.kiok.motorshow.mappers.CarShowroomMapper;
import by.kiok.motorshow.models.CarShowroom;
import by.kiok.motorshow.services.CarShowroomService;
import by.kiok.motorshow.utils.HibernateUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarShowroomServiceImpl implements CarShowroomService {

    private final CarShowroomMapper showroomMapper;

    @Override
    public CarShowroomInfoDto createCarShowroom(CarShowroomDtoReq carShowroomDtoReq) {
        CarShowroom carShowroom = showroomMapper.toCarShowroom(carShowroomDtoReq);
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(carShowroom);

            transaction.commit();

            return showroomMapper.toCarShowroomInfoDto(carShowroom);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        throw new EntityException();
    }

    @Override
    public void deleteById(Long id) {
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
    public CarShowroomInfoDto updateCarShowroom(Long id, CarShowroomDtoReq carShowroomDtoReq) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            CarShowroom carShowroom = session.get(CarShowroom.class, id);

            carShowroom.setName(carShowroomDtoReq.name);
            carShowroom.setAddress(carShowroomDtoReq.address);

            session.merge(carShowroom);
            transaction.commit();

            return showroomMapper.toCarShowroomInfoDto(carShowroom);
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        throw new EntityException();
    }

    @Override
    public CarShowroomPageDto findAllCarShowrooms(Pageable pageable) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            List<CarShowroom> carShowrooms = session.createQuery("from CarShowroom").list();
            transaction.commit();

            return showroomMapper.toCarShowroomPageDto(carShowrooms, pageable);
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        throw new EntityNotFoundException();
    }

    @Override
    public CarShowroomInfoDto findById(long id) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            CarShowroom carShowroom = session.get(CarShowroom.class, id);

            transaction.commit();

            return showroomMapper.toCarShowroomInfoDto(carShowroom);
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        throw new EntityNotFoundException();
    }
}
