package com.expense.expense_tracker.dto;

import com.expense.expense_tracker.entities.UserInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoDto extends UserInfo {
    private String username;
    private String lastName;
    private Long phoneNumber;

    private String email;

}
