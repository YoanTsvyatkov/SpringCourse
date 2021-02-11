package com.example.test1.dao;

import com.example.test1.model.Shampoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findAllBySize(int size);
    List<Shampoo> findAllByPriceGreaterThan(double price);
    List<Shampoo> findAllByPriceGreaterThanEqualAndPriceLessThanEqual(double startPrice, double endPrice);
}
