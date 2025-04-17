package com.wosarch.buysell.admin.model.users;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@AllArgsConstructor
public class LocalUserCreationRequest {

    @NotNull
    @NotEmpty(message = "User Id can not be a null or empty")
    private String userId;
}
