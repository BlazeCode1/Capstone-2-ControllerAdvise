package org.example.capstone2controlleradvise.Repository;

import org.example.capstone2controlleradvise.Model.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingsRepository extends JpaRepository<Ratings,Integer> {

    Ratings findRatingsById(Integer id);

    List<Ratings> findAllByPlaceId(Integer placeId);

}
