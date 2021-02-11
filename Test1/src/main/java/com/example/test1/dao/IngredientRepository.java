package com.example.test1.dao;

import com.example.test1.model.Ingredient;
import com.example.test1.model.Shampoo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
