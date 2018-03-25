package com.project.manager.repositories;

import com.project.manager.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserModel findByUsername(String username);

    UserModel findByEmail(String email);

    UserModel findByUsernameOrEmail(String username, String email);


}
