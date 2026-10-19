package com.example.productos.controller;

import com.example.productos.dto.CategoryRequest;
import com.example.productos.dto.CategoryResponse;
import com.example.productos.model.Category;
import com.example.productos.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service){
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponse>> findAll(){
        List<CategoryResponse> listResponse = new ArrayList<>();
        for (Category c: service.getAll()){
            listResponse.add(toResponse(c));
        }
        return ResponseEntity.ok(listResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id){
        Optional<Category> category = service.findById(id);

        if(!category.isEmpty()){
            return ResponseEntity.ok(toResponse(category.get()));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<CategoryResponse> save(@Valid @RequestBody CategoryRequest category){
        if (category.name().isEmpty()){
            ResponseEntity.badRequest().body("error");
        }
        Category categorySaved = service.save(toEntity(category));

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(categorySaved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boolean deleted = service.delete(id);
        if (deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id,@Valid @RequestBody CategoryRequest category){
        Optional<Category> optional = service.update(
                id,
                toEntity(category)
        );
        if(optional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(optional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    private CategoryResponse toResponse (Category category){
        return new CategoryResponse(category.getId(), category.getName());
    }

    private Category toEntity(CategoryRequest request){
        Category category = new Category();
        category.setName(request.name());

        return category;
    }
}
