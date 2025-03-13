package com.wosarch.buysell.admin.repositories.roles;

import com.wosarch.buysell.admin.model.roles.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolesRepository extends CrudRepository<Role, Long>, TemplateRolesRepository {

    List<Role> findAll();

    Optional<Role> findByCode(String code);

    void deleteByCode(String code);

}
