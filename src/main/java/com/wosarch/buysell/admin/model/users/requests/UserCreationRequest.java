package com.wosarch.buysell.admin.model.users.requests;

import lombok.Data;

@Data
public class UserCreationRequest {

    private String name;

    private String password;

    private String email;
}
