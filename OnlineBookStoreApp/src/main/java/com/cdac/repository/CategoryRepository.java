package com.cdac.repository;

import com.cdac.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByCategoryNameContainingIgnoreCase(String name);
    boolean existsByCategoryNameContainingIgnoreCase(String name);
}
