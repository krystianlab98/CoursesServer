package com.github.course.features.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao;
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryDao.findAllCategories();
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryDao.findCategoryById(id).get();
    }

    @Override
    public List<Category> findCategoryByTitle(String title) {
        List<Category> titles = categoryDao.findAllCategories()
                .stream()
                .map(category -> findInString(title, category.getTitle()))
                .filter(str -> str != null)
                .map(finded -> categoryDao.findCategoryByTitle(finded))
                .collect(Collectors.toList());

        return titles;
    }

    @Override
    public void add(Category category) {
        categoryDao.save(category);
    }

    //chwilowy brak możliwości edycji kursów
    @Override
    public void update(Category newCategory, Long id) {
        categoryRepository.findById(id)
                .map(category -> {
                    category.setTitle(newCategory.getTitle());
                    category.replaceCourses(newCategory.getCourses());
                    return categoryRepository.save(category);
                }).orElseThrow();
    }

    @Override
    public void delete(Long id) {
        categoryDao.delete(id);
    }

    private String findInString(String word, String text) {
        return text.toLowerCase().indexOf(word.toLowerCase()) > -1 ? text : null;
    }
}
