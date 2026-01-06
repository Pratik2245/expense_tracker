package com.expense.expense_tracker.repository;

import com.expense.expense_tracker.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<UserInfo,Integer> {
   public UserInfo findByUsername(String username);
}
