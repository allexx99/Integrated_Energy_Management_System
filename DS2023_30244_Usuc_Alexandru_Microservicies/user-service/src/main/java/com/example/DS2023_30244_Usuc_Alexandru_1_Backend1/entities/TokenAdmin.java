package com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties("admin")
@Table(name = "token_admin")
@Getter
@Setter
@Entity
public class TokenAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "token", unique = true)
    private String token;
    @Column(name = "date")
    private Date date;
    @Column(name = "expired")
    private boolean expired;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;
}
