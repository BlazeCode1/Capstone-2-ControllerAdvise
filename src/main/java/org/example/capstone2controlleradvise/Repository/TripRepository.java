package org.example.capstone2controlleradvise.Repository;

import org.example.capstone2controlleradvise.Model.Trip;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip,Integer> {
    Trip findTripByUserId(Integer userId);
    Trip findTripById(Integer id);
    boolean existsByShareToken(String shareToken);
    Trip findByShareToken(String shareToken);
    List<Trip> findBySelectedPlaceIdIn(List<Integer> id);

    @Query("""
           select t.id, t.userId, t.selectedPlaceId, p.name, p.avgRating, t.start, t.imageUrl, t.categoryId
           from Trip t join Place p on p.id = t.selectedPlaceId
           where t.district = :district
           order by p.avgRating desc, t.start desc
           """)
    List<Object[]> topTripsByDistrict(String district, Pageable p);

    // أفضل الرحلات في الحي × فئة
    @Query("""
           select t.id, t.userId, t.selectedPlaceId, p.name, p.avgRating, t.start, t.imageUrl, t.categoryId
           from Trip t join Place p on p.id = t.selectedPlaceId
           where t.district = :district
             and t.categoryId = :categoryId
           order by p.avgRating desc, t.start desc
           """)
    List<Object[]> topTripsByDistrictCategory(String district,
                                              Integer categoryId,
                                              Pageable p);

    boolean existsByUserIdAndSelectedPlaceIdAndStartAfter(Integer userId, Integer selectedPlaceId, Instant after);


}
