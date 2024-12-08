package by.kiok.motorshow.services;

import by.kiok.motorshow.models.Review;

import java.util.List;

public interface ReviewService {

    Review createReview(Review reviewData, Long clientId, Long cardId);

    void deleteReview(Long id);

    void updateReview(Long id, Review reviewUpdate);

    List<Review> searchReviewsByKeywords(String keywords);

    List<Review> searchReviews(String keywords, Integer minRank, Integer maxRank);

}
