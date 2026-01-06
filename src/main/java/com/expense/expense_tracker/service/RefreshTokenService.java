package com.expense.expense_tracker.service;

import com.expense.expense_tracker.entities.RefreshToken;
import com.expense.expense_tracker.entities.UserInfo;
import com.expense.expense_tracker.repository.RefreshTokenRepository;
import com.expense.expense_tracker.repository.UserRepository;
import org.hibernate.boot.model.process.internal.UserTypeResolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    public RefreshToken createToken(String username){
        UserInfo userInfoExtracted=userRepository.findByUsername(username);
        RefreshToken refreshToken=RefreshToken.builder()
                .userInfo(userInfoExtracted)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }
    public RefreshToken verifyExpiration(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken()+" Refresh token Expired Please Make a new login");
        }
        return refreshToken;

    }
    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }
}
