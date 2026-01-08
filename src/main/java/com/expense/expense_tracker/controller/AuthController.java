package com.expense.expense_tracker.controller;

import com.expense.expense_tracker.dto.UserInfoDto;
import com.expense.expense_tracker.entities.RefreshToken;
import com.expense.expense_tracker.response.JwtResponseDTO;
import com.expense.expense_tracker.service.JwtService;
import com.expense.expense_tracker.service.RefreshTokenService;
import com.expense.expense_tracker.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @PostMapping("auth/v1/signup")
    public ResponseEntity signUp(@RequestBody UserInfoDto userInfoDto) {
        try {
            boolean isSignedUp = userDetailsService.signUpUser(userInfoDto);
            if (!isSignedUp) {
                return new ResponseEntity<>("Already Exists", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createToken(userInfoDto.getUsername());
            String jwtToken = jwtService.GenerateToken(userInfoDto.getUsername());
            return new ResponseEntity(JwtResponseDTO.builder().accessToken(jwtToken).token(refreshToken.getToken()).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Exception in userServie ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
