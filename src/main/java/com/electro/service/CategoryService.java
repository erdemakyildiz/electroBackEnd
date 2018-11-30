package com.electro.service;

import com.electro.exception.CategoryNotFoundException;
import com.electro.exception.UserNotFoundException;
import com.electro.models.Category;
import com.electro.models.User;
import com.electro.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public Category findOrCreateCategory(String name) {
        Category category = categoryRepository.findFirstByNameEquals(name);

        if (category == null){
            category = new Category();
            category.setName(name);
            category.setActive(true);
            category.setCreateDate(new Date());
            category.setStreaks(new ArrayList<>());

            categoryRepository.save(category);
        }

        return category;
    }

    public Category findCategory(String name) throws CategoryNotFoundException {
        Category category = categoryRepository.findFirstByNameEquals(name);

        if (category == null)
            throw new CategoryNotFoundException(String.format("kategori bulunamadı isim:%s", name));

        return category;
    }

    public void activeCategory(Category category) throws CategoryNotFoundException {
        final Category categoryFromDB = getCategoryFromDB(category.getId());
        categoryFromDB.setActive(true);

        categoryRepository.save(category);
    }

    public void passiveCategory(Category category) throws CategoryNotFoundException {
        final Category categoryFromDB = getCategoryFromDB(category.getId());
        categoryFromDB.setActive(false);

        categoryRepository.save(category);
    }

    private Category getCategoryFromDB(long id) throws CategoryNotFoundException {
        final Optional<Category> optional = categoryRepository.findById(id);
        final Category category = optional.orElse(null);

        if (category == null)
            throw new CategoryNotFoundException(String.format("kategori bulunamadı id:%s", id));

        return category;
    }

}
