package infrastructure.repositories;

import domain.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryCategoryRepository implements CategoryRepository {
    private Map<String, Category> categories = new HashMap<>();

    @Override
    public void save(Category category) {
        categories.put(category.getId(), category);
    }

    @Override
    public List<Category> findAll() {
        return categories.values().stream().collect(Collectors.toList());
    }

    @Override
    public Category findById(String id) {
        return categories.get(id);
    }

    @Override
    public void delete(String id) {
        categories.remove(id);
    }
}