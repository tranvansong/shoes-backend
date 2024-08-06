package com.example.shoeswebbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private float total_price;

    @Column(nullable = false)
    private String order_status;

    @Column(nullable = false)
    private String full_name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone_number;

    private String note;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp order_date;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created_at;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updated_at;
}
