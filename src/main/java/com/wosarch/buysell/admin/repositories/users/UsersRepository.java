package com.wosarch.buysell.admin.repositories.users;

import com.wosarch.buysell.admin.model.users.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User, String>, TemplateUsersRepository {

    @Cacheable("user")
    @Override
    Optional<User> findById(String id);
}
