package com.chameleon.categorization.Controller;

import com.chameleon.categorization.Entity.Category;
import com.chameleon.categorization.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/add")
    public Category addCategory(@RequestBody Category category) {
        categoryRepository.save(category);
        return category;
    }

    @GetMapping
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
