package com.wosarch.buysell.admin.services.users;

import com.wosarch.buysell.admin.model.users.User;
import com.wosarch.buysell.admin.model.users.UsersService;
import com.wosarch.buysell.admin.model.users.requests.UserCreationRequest;
import com.wosarch.buysell.admin.repositories.users.UsersRepository;
import com.wosarch.buysell.common.model.sequence.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    @Qualifier("adminSequence")
    private SequenceService sequenceService;

    @Override
    public User create(UserCreationRequest request) {
        User user = new User();
        user.setId(sequenceService.getNext(User.SEQUENCE_NAME));
        user.setName(request.getName());

        return usersRepository.save(user);
    }

    @Override
    public User get(String id) {
        return usersRepository.findById(id).get();
    }

}
