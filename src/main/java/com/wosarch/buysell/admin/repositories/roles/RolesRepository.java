package com.wosarch.buysell.admin.repositories.roles;

import com.wosarch.buysell.admin.model.roles.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends MongoRepository<Role, String>, TemplateRolesRepository {

}
