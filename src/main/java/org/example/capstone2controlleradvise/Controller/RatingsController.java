package org.example.capstone2controlleradvise.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone2controlleradvise.Api.ApiResponse;
import org.example.capstone2controlleradvise.Model.Ratings;
import org.example.capstone2controlleradvise.Service.RatingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/rate")
@RequiredArgsConstructor
public class RatingsController {

    private final RatingsService ratingsService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllRatings() {
        return ResponseEntity.ok(ratingsService.getRatings());
    }
    @PostMapping("/add")
    public ResponseEntity<?> createRating(@RequestBody @Valid Ratings rating) {
        ratingsService.createRating(rating);
        return ResponseEntity.ok(new ApiResponse("Rating created successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRating(
            @PathVariable Integer id,
            @RequestBody @Valid Ratings rating) {
        ratingsService.updateRating(id, rating);
        return ResponseEntity.ok(new ApiResponse("Rating updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteRating(@PathVariable Integer id) {
        ratingsService.deleteRating(id);
        return ResponseEntity.ok(new ApiResponse("Rating deleted successfully"));
    }





}
