package com.project.project.entity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "地址表结构")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address implements BaseEntity {
    @Id
    @Column(name = "ID", length = 64, nullable = false)
    private String id;

    @Column(name = "ADDRESS_ID", length = 64, unique = true, nullable = true)
    private String addressId;

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "PHONE", length = 20, nullable = false)
    private String phone;

    @Column(name = "DETAILED_ADDRESS", length = 255, nullable = false)
    private String detailedAddress;

    @Column(name = "STATE", length = 2, nullable = false)
    private String state;

    @Column(name = "PROVINCE_ID", length = 20, nullable = false)
    private String provinceId;

    @Column(name = "CITY_ID", length = 20, nullable = false)
    private String cityId;

    @Column(name = "AREA_ID", length = 20, nullable = false)
    private String areaId;

    @Column(name = "COMPLETED_ADDRESS", length = 255, nullable = false)
    private String completedAddress;

    @Column(name = "CREATE_TIME", nullable = false)
    private LocalDate createTime;

    @Column(name = "UPDATE_TIME", nullable = false)
    private LocalDate updateTime;
}