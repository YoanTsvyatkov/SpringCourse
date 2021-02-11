package com.fmi.reviews.dao;

import com.fmi.reviews.model.MovieReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieReviewRepository extends JpaRepository<MovieReview, Long> {
}
