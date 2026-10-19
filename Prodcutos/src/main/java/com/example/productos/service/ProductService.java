package com.example.productos.service;

import com.example.productos.exception.ResourceConflictException;
import com.example.productos.exception.ResourceNotFoundException;
import com.example.productos.model.Category;
import com.example.productos.model.Product;
import com.example.productos.repository.CategoryRepository;
import com.example.productos.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repo;
    private final CategoryRepository repoCategory;

    public ProductService(ProductRepository repo, CategoryRepository repoCategory){
        this.repo = repo;
        this.repoCategory = repoCategory;
    }

    public Product changeProductStock(String word, Long id, int cantidad){
        Product productDB = repo.findById(id).orElseThrow(
                ()->
                        new ResourceNotFoundException("Product not found with id: " + id)
        );
        if (word.equals("add")){
            productDB.setStock(productDB.getStock() + cantidad);
            repo.save(productDB);
        }else if (word.equals("remove")){

            if (productDB.getStock() > cantidad){
                productDB.setStock(productDB.getStock() - cantidad);
                repo.save(productDB);
            }else {
                throw new ResourceConflictException("Stock insuficiente");
            }
        }else {
            throw new ResourceConflictException("Palabra incorrecta");
        }
        return productDB;
    }



    public List<Product> getAll(){
        return repo.findAll();
    }

    public Optional<Product> findById(Long id){
        return repo.findById(id);
    }

    public Product save(Product product, Long categoryId){
        Category category = repoCategory.findById(categoryId).
                orElseThrow(
                ()->
                        new ResourceNotFoundException("Category not found with id: " + categoryId)
        );
        product.setCategory(category);
        return repo.save(product);

    }

    public Product update(Long id, Product product, Long categoryId){

        Product productDB = repo.findById(id).orElseThrow(
                ()->
                        new ResourceNotFoundException("Product not found with id: " + id)
        );
        Category category = repoCategory.findById(categoryId).orElseThrow(
                ()->
                new ResourceNotFoundException("Category not found with id: " + categoryId)
        );
        //Product productDB = optional.get();

        productDB.setName(product.getName());
        productDB.setPrice(product.getPrice());
        productDB.setStock(product.getStock());
        productDB.setCategory(category);
        return repo.save(productDB);
    }

    public boolean delete(Long id){
        if (!repo.existsById(id)){
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
