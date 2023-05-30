package Novoe.LoyaltySystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="t_forgot")
public class Forgot extends BaseEntity{

    @Column(name = "email")
    private String email;

    @Column(name = "token")
    private String token;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;
}
