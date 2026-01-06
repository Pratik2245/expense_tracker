package com.expense.expense_tracker.entities;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.Instant;


@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Table(name = "tokens")
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String token;
  private Instant expiryDate;
  @OneToOne
  @JoinColumn(
          name = "id",
          referencedColumnName = "user_id"
  )
  private UserInfo userInfo;
}

