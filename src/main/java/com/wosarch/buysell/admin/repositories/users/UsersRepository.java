package com.wosarch.buysell.admin.repositories.users;

import com.wosarch.buysell.admin.model.users.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<User, String>, TemplateUsersRepository {

    Optional<User> findById(String id);
}
