package org.example.capstone2controlleradvise.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone2controlleradvise.Api.ApiResponse;
import org.example.capstone2controlleradvise.Model.Category;
import org.example.capstone2controlleradvise.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<?> getCategories() {
        if (categoryService.getCategories().isEmpty())
            return ResponseEntity.ok(new ApiResponse("No Categories found"));
        return ResponseEntity.ok(categoryService.getCategories());

    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@Valid @RequestBody Category category) {
        categoryService.addCategory(category);
        return ResponseEntity.ok(new ApiResponse("Category Added Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @Valid @RequestBody Category category) {
        categoryService.updateCategory(id, category);
        return ResponseEntity.ok(new ApiResponse("Category Added Successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse("category Deleted Successfully"));
    }
}
