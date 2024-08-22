package ns.task2.controller;

import ns.task2.entity.ProductEntity;
import ns.task2.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000")  // Modify this URL based on your frontend application
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // POST /products - Create a new product
    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity productEntity) {
        ProductEntity createdProduct = productService.saveProduct(productEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    // GET /products - Get all products
    @GetMapping
    public List<ProductEntity> getAllProducts() {
        return productService.getAllProducts();
    }

    // GET /products/{code} - Get a product by code
    @GetMapping("/{code}")
    public ResponseEntity<ProductEntity> getProductByCode(@PathVariable String code) {
        ProductEntity product = productService.getProductById(code);
        return ResponseEntity.ok(product);
    }

    // PUT /products/{code} - Update a product by code
    @PutMapping("/{code}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable String code, @RequestBody ProductEntity updatedProduct) {
        ProductEntity updated = productService.updateProduct(code, updatedProduct);
        return ResponseEntity.ok(updated);
    }

    // DELETE /products/{code} - Delete a product by code
    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteProduct(@PathVariable String code) {
        productService.deleteProduct(code);
        return ResponseEntity.ok("Product with code '" + code + "' deleted successfully.");
    }
}
