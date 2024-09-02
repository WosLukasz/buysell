package com.wosarch.buysell.admin.repositories.roles;

import com.wosarch.buysell.admin.model.roles.Role;
import com.wosarch.buysell.admin.repositories.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class TemplateRolesRepositoryImpl extends AdminRepository implements TemplateRolesRepository {

    Logger logger = LoggerFactory.getLogger(TemplateRolesRepositoryImpl.class);

    @Override
    public void createRole(String code, List<String> rights) {
        Role role = Role.builder()
                .code(code)
                .rights(rights)
                .build();

        mongoTemplate.save(role);
    }

    @Override
    public void deleteRole(String code) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Role.Fields.CODE).is(code));

        mongoTemplate.remove(query);
    }

    @Override
    public void addRightsToRole(String code, List<String> rightsToAdd) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Role.Fields.CODE).is(code));
        Role role = mongoTemplate.findOne(query, Role.class);
        if (Objects.isNull(role)) {
            logger.error("No role found for code {}", code);
            throw new RuntimeException(); // to change to 404
        }

        if (CollectionUtils.isEmpty(role.getRights())) {
            role.setRights(new ArrayList<>());
        }

        role.getRights().addAll(rightsToAdd);
        mongoTemplate.save(role);
    }

    @Override
    public void removeRightsFromRole(String code, List<String> rightsToRemove) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Role.Fields.CODE).is(code));
        Role role = mongoTemplate.findOne(query, Role.class);
        if (Objects.isNull(role)) {
            logger.error("No role found for code {}", code);
            throw new RuntimeException(); // to change to 404
        }

        if (CollectionUtils.isEmpty(role.getRights())) {
            role.setRights(new ArrayList<>());
        }

        role.getRights().removeIf(rightsToRemove::contains);
        mongoTemplate.save(role);
    }

}
