package com.project.project.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "订单表结构") // Use your actual table name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements BaseEntity {

    @Id
    @Column(name = "ID", length = 64, nullable = false)
    private String id;

    @Column(name = "ORDER_ID", length = 64, unique = true)
    private String orderId;

    @Column(name = "USER_ID", length = 64, nullable = false)
    private String userId;

    @Column(name = "GOODS_ID", length = 64, nullable = false)
    private String goodsId;

    @Column(name = "PURCHASE", precision = 3, scale = 0, nullable = false)
    private BigDecimal purchase;

    @Column(name = "ADDRESS_ID", length = 64, nullable = false)
    private String addressId;

    @Column(name = "ORDER_STATE", length = 20, nullable = false)
    private String orderState;

    @Column(name = "PAYKEY", length = 20, nullable = false)
    private String paykey;

    @Column(name = "TOTAL_MONEY", precision = 7, scale = 0, nullable = false)
    private BigDecimal totalMoney;

    @Column(name = "CANCEL_TIME", nullable = false)
    private LocalDate cancelTime;

    @Column(name = "CREATE_TIME", nullable = false)
    private LocalDate createTime;

    @Column(name = "UPDATE_TIME", nullable = false)
    private LocalDate updateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GOODS_ID", referencedColumnName = "GOODS_ID", insertable = false, updatable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID", insertable = false, updatable = false)
    private Address address;
}
