package org.example.capstone2controlleradvise.Repository;

import org.example.capstone2controlleradvise.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query("select c from Category c where c.id=?1")
    Category findCategoryById(Integer id);

}
