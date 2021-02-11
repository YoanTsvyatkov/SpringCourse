package com.example.test1.main;

import com.example.test1.dao.IngredientRepository;
import com.example.test1.dao.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    ShampooRepository shampooRepository;

    @Override
    public void run(String... args) throws Exception {
        shampooRepository.findAllBySize(0).forEach(System.out::println);
        System.out.println();
        shampooRepository.findAllByPriceGreaterThan(6).forEach(System.out::println);
        System.out.println();
        shampooRepository.findAllByPriceGreaterThanEqualAndPriceLessThanEqual(5, 10).forEach(System.out::println);
        System.out.println();
        ingredientRepository.findAll().forEach(System.out::println);
    }
}
