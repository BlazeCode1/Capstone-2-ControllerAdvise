package org.example.capstone2controlleradvise.Repository;

import org.example.capstone2controlleradvise.Model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {
    List<Challenge> findByDistrictAndIsActiveTrue(String district);
}

