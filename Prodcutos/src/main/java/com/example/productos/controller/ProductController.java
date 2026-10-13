package com.example.productos.controller;

import com.example.productos.dto.ProductRequest;
import com.example.productos.dto.ProductResponse;
import com.example.productos.model.Product;
import com.example.productos.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> findAll(){
        List<ProductResponse> listResponse = new ArrayList<>();
        for (Product p: service.getAll()){
            listResponse.add(toResponse(p));
        }
        return ResponseEntity.ok(listResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id){
        Optional<Product> product = service.findById(id);

        if(!product.isEmpty()){
            return ResponseEntity.ok(toResponse(product.get()));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<ProductResponse> save(@Valid @RequestBody ProductRequest product){
        if (product.name().isEmpty()){
            ResponseEntity.badRequest().body("error");
        }
        Product productSaved = service.save(toEntity(product), product.categoryId());

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(productSaved));
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
    public ResponseEntity<ProductResponse> update(@PathVariable Long id,@Valid @RequestBody ProductRequest product){
        Optional<Product> optional = service.update(id, toEntity(product));
        if(optional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(optional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    private ProductResponse toResponse (Product product){
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock(), 0L, "");
    }

    private Product toEntity(ProductRequest request){
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setStock(request.stock());

        return product;
    }
}
