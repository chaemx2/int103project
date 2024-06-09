package infrastructure.repositories;

import domain.Category;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCategoryRepository implements CategoryRepository {
    private List<Category> categories = new ArrayList<>();

    @Override
    public void save(Category category) {
        categories.add(category);
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories);
    }
}