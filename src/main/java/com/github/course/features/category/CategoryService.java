package com.github.course.features.category;

import java.util.List;

public interface CategoryService {

    List<Category> findAllCategories();

    Category findCategoryById(Long id);

    List<Category> findCategoryByTitle(String title);

    void add(Category category);

    void update(Category newCategory, Long id);

    void delete(Long id);

}
