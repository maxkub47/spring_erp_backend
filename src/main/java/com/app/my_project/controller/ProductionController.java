package com.app.my_project.controller;

import com.app.my_project.entity.ProductionEntity;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.my_project.repository.ProductionRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/production")
public class ProductionController {
  private final ProductionRepository productionRepository;

  public ProductionController(ProductionRepository productionRepository){
    this.productionRepository = productionRepository;
  }

  @GetMapping
  public List<ProductionEntity> getAllProductions() {
      return productionRepository.findAll();
  }

  @GetMapping("/{id}")
  public ProductionEntity getProduction(@PathVariable Long id) {
    return productionRepository.findById(id).orElse(null);
  }

  @PostMapping
  public ProductionEntity createProduction(@RequestBody ProductionEntity production) {
      return productionRepository.save(production);
  }
  
  @PutMapping("/{id}")
  public ProductionEntity updateProduction(@PathVariable Long id, @RequestBody ProductionEntity production) {
    ProductionEntity productionToUpdate = productionRepository.findById(id).orElse(null);
    if (productionToUpdate == null) {
      throw new IllegalArgumentException("Production Not Found");
    }

    productionToUpdate.setName(production.getName());
    productionToUpdate.setDetail(production.getDetail());

    return productionRepository.save(productionToUpdate);
    

  }

  @DeleteMapping("/{id}")
  public void deleteProduct(@PathVariable Long id) {
    productionRepository.deleteById(id);
  }

  @PutMapping("/updatePrice/{id}")
    public void updatePrice(
            @PathVariable Long id,
            @RequestBody ProductionEntity productionEntity) {
        ProductionEntity p = productionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("production not found"));
        p.setPrice(productionEntity.getPrice());
        productionRepository.save(p);
    }
}
