package com.example.productos.service;

import com.example.productos.exception.ResourceNotFoundException;
import com.example.productos.model.Category;
import com.example.productos.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo ){
        this.repo = repo;
    }

    public List<Category> getAll(){
        return repo.findAll();
    }

    public Optional<Category> findById(Long id){
        return repo.findById(id);
    }

    public Category save(Category category){
        return repo.save(category);
    }

    public Optional<Category> update(Long id, Category category){

        Category categoryDB = repo.findById(id).orElseThrow(
                ()->
                        new ResourceNotFoundException("Category not found with id: " + id)
        );
        //Product productDB = optional.get();

        categoryDB.setName(category.getName());
        return Optional.of(repo.save(categoryDB));
    }

    public boolean delete(Long id){
        if (!repo.existsById(id)){
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
