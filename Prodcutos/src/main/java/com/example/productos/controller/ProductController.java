package com.example.productos.controller;

import com.example.productos.dto.ProductRequest;
import com.example.productos.dto.ProductResponse;
import com.example.productos.dto.StockRequest;
import com.example.productos.dto.StockResponse;
import com.example.productos.exception.ResourceNotFoundException;
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

    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StockResponse> changeProductStock( @PathVariable Long id, @Valid @RequestBody StockRequest stockRequest){
        Product changedProduct = service.changeProductStock(
                stockRequest.word(),
                id,
                stockRequest.cantidad()
        );

        return ResponseEntity.ok(toStockResponse(changedProduct, stockRequest.word()));
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
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id){
        Product product = service.findById(id).orElseThrow(
                ()->
                        new ResourceNotFoundException("Product not found with id: " + id)
        );

        return ResponseEntity.ok(toResponse(product));
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
        Product productService = service.update(
                id,
                toEntity(product),
                product.categoryId()
        );

        return ResponseEntity.ok(toResponse(productService));
    }

    private ProductResponse toResponse (Product product){
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock(), product.getCategory().getId(), product.getCategory().getName());
    }

    private Product toEntity(ProductRequest request){
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setStock(request.stock());

        return product;
    }

    private StockResponse toStockResponse(Product product, String word){
        return new StockResponse(word, product.getStock());
    }
}
