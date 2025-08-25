package com.cdac.repository;

import com.cdac.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByCategoryName(String CategoryName);
    boolean existsByCategoryName(String name);
}
