package org.example.capstone2controlleradvise.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone2controlleradvise.Api.ApiException;
import org.example.capstone2controlleradvise.Model.Category;
import org.example.capstone2controlleradvise.Model.Place;
import org.example.capstone2controlleradvise.Model.User;
import org.example.capstone2controlleradvise.Repository.CategoryRepository;
import org.example.capstone2controlleradvise.Repository.PlaceRepository;
import org.example.capstone2controlleradvise.Repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor

public class PlaceService {

    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    public List<Place> getPlaces(){
        String url;

        return placeRepository.findAll();
    }

    public Place getPlaceById(Integer id){
        Place place = placeRepository.findPlaceById(id);
        if(place == null)
            throw new ApiException("Place Not Found");
        return place;
    }
    public List<Place> getTopPlacesInUserDistrict(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) throw new ApiException("User not found");
        return placeRepository.findTopPlacesByDistrict(user.getDistrict(), PageRequest.of(0, 5));
    }

    public List<Place> searchPlaces(String district, Integer categoryId) {
        if (district == null && categoryId == null) {
            return placeRepository.findAll(Sort.by(Sort.Direction.DESC, "avgRating"));
        } else if (district != null && categoryId == null) {
            return placeRepository.findByDistrictIgnoreCaseOrderByAvgRatingAsc(district);
        } else if (district == null) {
            return placeRepository.findByCategoryIdOrderByAvgRatingDesc(categoryId);
        }
        return placeRepository.findByDistrictIgnoreCaseAndCategoryIdOrderByAvgRatingDesc(district, categoryId);
    }

    public void addPlace(Place place){
        Category category = categoryRepository.findCategoryById(place.getCategoryId());
        User user = userRepository.findUserById(place.getCreated_by_id());
        if(category == null)
            throw new ApiException("Category Not Found");

        if(user == null)
            throw new ApiException("User Not Found");

        if(!user.getRole().equalsIgnoreCase("admin"))
            throw new  ApiException("UnAuthorized to add");

        if(placeRepository.existsByNameAndDistrict(place.getName(), place.getDistrict()))
            throw new ApiException("Place Already Added.");
        placeRepository.save(place);
    }



    public void updatePlace(Integer id,Place place){
        Place oldPlace = placeRepository.findPlaceById(id);

        if(oldPlace == null)
            throw new ApiException("Place Not Found");

        oldPlace.setName(place.getName());
        oldPlace.setDescription(place.getDescription());
        oldPlace.setDistrict(place.getDistrict());
        oldPlace.setCategoryId(place.getCategoryId());
        oldPlace.setLocation(place.getLocation());
        oldPlace.setOpen_time(place.getOpen_time());
        oldPlace.setClose_time(place.getClose_time());
        oldPlace.setContact_number(place.getContact_number());
        oldPlace.setImage_url(place.getImage_url());
        oldPlace.setPrice_level(place.getPrice_level());
        placeRepository.save(oldPlace);
    }

    public void deletePlace(Integer id){
        Place place = placeRepository.findPlaceById(id);
        if(place == null)
            throw new ApiException("Place Not Found");
        placeRepository.delete(place);
    }



    public List<Place> getSeasonalPlaces(String district) {
        int seasonalVisitThreshold = 50;
        int seasonalCurrentVisitorsThreshold = 10;

        return placeRepository.findByDistrictAndSeasonal(
                district,
                seasonalVisitThreshold,
                seasonalCurrentVisitorsThreshold
        );
    }

    private void ensureSubscriber(Integer userId){
        User u = userRepository.findUserById(userId);
        if (u == null) throw new ApiException("User not found");
        if (u.getIsSubscriber() == null || !u.getIsSubscriber())
            throw new ApiException("Subscription required");
    }



    public List<Place> subscriberDiscounts(Integer userId, String district){
        ensureSubscriber(userId);
        Instant now = Instant.now();
        return placeRepository.findByDistrictAndIsPartnerTrueAndDealPercentGreaterThanAndActive(district, 0, now);
    }

}
