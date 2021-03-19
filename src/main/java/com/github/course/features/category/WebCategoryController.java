package com.github.course.features.category;


import com.github.course.features.category.dto.CategoryDto;
import com.github.course.features.category.dto.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "web/api/categories")
public class WebCategoryController {

    CategoryService categoryService;

    @Autowired
    public WebCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping
    public ResponseEntity<List<CategoryDto>> readCategories() {
        try {
            List<CategoryDto> categories = categoryService.findAllCategories()
                    .stream()
                    .map(CategoryMapper::convertCategoryToDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception appear at Get method on /categories endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/byTitle/{title}")
    public ResponseEntity<List<CategoryDto>> readCategoryByTitle(@PathVariable String title) {
        try {
            List<CategoryDto> categories = categoryService.findCategoryByTitle(title).stream()
                    .map(CategoryMapper::convertCategoryToDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception appear at Get method on /categories/{title} endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> readCategoryById(@PathVariable Long id) {
        try {
            CategoryDto category = CategoryMapper.convertCategoryToDto(categoryService.findCategoryById(id));
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception appear at Get method on /categories/{id} endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createCategory(@RequestBody CategoryDto categoryDto) {
        try {
            categoryService.add(CategoryMapper.convertDtoToCategory(categoryDto));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Exception appear at POST method on /categories endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Long id) {
        try {
            categoryService.update(CategoryMapper.convertDtoToCategory(categoryDto), id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception appear at PUT method on /categories/{id} endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            log.error("Exception appear at Delete method on /categories/{id} endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


}
