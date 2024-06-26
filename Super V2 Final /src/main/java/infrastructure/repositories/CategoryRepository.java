package infrastructure.repositories;

import domain.Category;

import java.util.List;

public interface CategoryRepository {
    void save(Category category);
    List<Category> findAll();
    Category findById(String id);
    void delete(String id);
}