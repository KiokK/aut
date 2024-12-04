package by.kiok.motorshow.services.impl;

import by.kiok.motorshow.models.Car;
import by.kiok.motorshow.models.Client;
import by.kiok.motorshow.models.Review;
import by.kiok.motorshow.services.ReviewService;
import by.kiok.motorshow.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;

import java.util.ArrayList;
import java.util.List;

import static by.kiok.motorshow.utils.HibernateUtil.initializeIndex;

public class ReviewServiceImpl implements ReviewService {

    @Override
    public Review createReview(Review reviewData, Long clientId, Long carId) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Car car = session.get(Car.class, carId);
            Client client = session.get(Client.class, clientId);
            reviewData.setCars(car);
            reviewData.setClient(client);
            session.save(reviewData);
            transaction.commit();
            return reviewData;

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return reviewData;
    }

    @Override
    public void deleteReview(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Review review = session.get(Review.class, id);
            session.delete(review);
            transaction.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateReview(Long id, Review reviewUpdate) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Review current = session.get(Review.class, id);
            current.setReviewText(reviewUpdate.getReviewText());
            current.setRank(reviewUpdate.getRank());
            session.merge(current);
            transaction.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Review> searchReviewsByKeywords(String keywords) {
        List<Review> reviews = new ArrayList<>();
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "FROM Review r WHERE r.reviewText LIKE :keywords";
            Query<Review> query = session.createQuery(hql, Review.class);
            query.setParameter("keywords", "%" + keywords + "%");

            reviews = query.getResultList();

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    @Override
    public List<Review> searchReviews(String keywords, Integer minRank, Integer maxRank) {
        List<Review> queries = new ArrayList<>();
        try (Session session = HibernateUtil.getSession()) {
            SearchSession searchSession = Search.session(session);
            initializeIndex(session);

            queries = searchSession.search(Review.class)
                    .where(f -> f.bool(b -> {
                        b.must(f.match()
                                .fields("reviewText")
                                .matching(keywords));
                        b.must(f.range()
                                .field("rank")
                                .atLeast(minRank));
                        b.must(f.range()
                                .field("rank")
                                .atMost(maxRank));

                    }))
                    .fetchAllHits();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return queries;
    }
}
