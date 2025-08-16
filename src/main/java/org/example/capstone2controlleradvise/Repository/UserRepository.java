package org.example.capstone2controlleradvise.Repository;

import org.example.capstone2controlleradvise.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserById(Integer id);

    @Query("select (count(u) > 0) from User u where u.id = :id and lower(u.role) = 'admin'")
    Boolean findUserAndCheckAdmin(Integer id);

}
