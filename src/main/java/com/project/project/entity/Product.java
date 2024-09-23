package com.project.project.entity;
import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "商品表结构")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements BaseEntity {
    @Id
    @Column(name = "ID", length = 64, nullable = false)
    private String id;

    @Column(name = "GOODS_ID", length = 64, unique = true, nullable = false)
    private String goodsId;

    @Column(name = "GOODS_NAME", length = 64, nullable = false)
    private String goodsName;

    @Column(name = "ORIGINAL_PRICE", precision = 7, scale = 2, nullable = false)
    private BigDecimal originalPrice;

    @Column(name = "DISCOUNT_PRICE", precision = 7, scale = 2, nullable = false)
    private BigDecimal discountPrice;

    @Column(name = "MASTER_IMG", length = 255, nullable = false)
    private String masterImg;

    @Column(name = "INTRO", length = 512, nullable = false)
    private String intro;

    @Column(name = "ADDRESS", length = 64, nullable = false)
    private String address;

    @Column(name = "BEGIN_TIME", nullable = false)
    private LocalDate beginTime;

    @Column(name = "END_TIME", nullable = false)
    private LocalDate endTime;

    @Column(name = "POSTAGE", nullable = false)
    private Integer postage;

    @Column(name = "INVENTORY", nullable = false)
    private Integer inventory;

    @Column(name = "SALE_VOLUME", nullable = false)
    private Integer saleVolume;

    @Column(name = "VIDEO_URL", length = 255, nullable = false)
    private String videoUrl;

    @Column(name = "CREATE_TIME", nullable = false)
    private LocalDate createTime;

    @Column(name = "UPDATE_TIME", nullable = false)
    private LocalDate updateTime;

}