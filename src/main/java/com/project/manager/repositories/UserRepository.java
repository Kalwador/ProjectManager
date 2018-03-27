package com.project.manager.repositories;

import com.project.manager.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    @Override
    Optional<UserModel> findById(Long aLong);

    UserModel findByUsername(String username);

    UserModel findByEmail(String email);

    UserModel findByUsernameOrEmail(String username, String email);

}
