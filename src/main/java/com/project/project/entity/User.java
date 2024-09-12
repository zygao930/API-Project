package com.project.project.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "用户表结构")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements BaseEntity {
    @Id
    @Column(name = "ID", length = 64, nullable = false)
    private String id;

    @Column(name = "USER_ID", length = 64, unique = true, nullable = false)
    private String userId;

    @Column(name = "EMAIL", length = 20, unique = true)
    private String email;

    @Column(name = "PASSWORD", length = 128, nullable = false)
    private String password;

    @Column(name = "NICK_NAME", length = 128)
    private String nickName;

    @Column(name = "GENDER", length = 5)
    private String gender;

    @Column(name = "AVATAR_URL", length = 255)
    private String avatarUrl;

    @Column(name = "BALANCE", precision = 7, scale = 2, nullable = false)
    private BigDecimal balance;

    @Column(name = "PAYKEY", length = 64, nullable = false)
    private String paykey;

    @Column(name = "CREATE_TIME", nullable = false)
    private LocalDate createTime;

    @Column(name = "UPDATE_TIME", nullable = false)
    private LocalDate updateTime;
}
