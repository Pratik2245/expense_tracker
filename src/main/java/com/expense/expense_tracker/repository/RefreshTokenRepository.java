package com.expense.expense_tracker.repository;

import com.expense.expense_tracker.entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String token);
}
