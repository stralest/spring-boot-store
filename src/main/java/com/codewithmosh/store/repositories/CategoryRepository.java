package com.codewithmosh.store.repositories;

import com.codewithmosh.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends JpaRepository<Category, Byte> {
}