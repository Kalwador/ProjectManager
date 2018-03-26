package com.project.manager.repositories;

import com.project.manager.entities.UserModel;
import com.project.manager.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    /**
     * This method is searching the {@link UserModel} with passed username in database
     * @param username this parameter is username to identify the user in database
     * @return the user with the same username ass passed in parameter
     */
    UserModel findByUsername(String username);

    /**
     * This method is searching the {@link UserModel} with passed email in database
     * @param email this parameter is emial to identify the user in database
     * @return the user with the same email ass passed in parameter
     */
    UserModel findByEmail(String email);

    @Query(value = "SELECT USER_MODEL.USERNAME FROM USER_MODEL", nativeQuery = true)
    Optional<List<String>> findAllUsernames();
    /**
     * This method is returning all users from database with specify {@link UserRole} in parameter
     * @param userRole this is the role which help to specify what users we are want to return
     * @return the List of {@link UserModel } with the same role ass passed in parameter
     */
    List<UserModel> getAllByRole(UserRole userRole);
}
