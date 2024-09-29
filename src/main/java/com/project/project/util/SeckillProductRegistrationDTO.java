package com.project.project.util;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object for product registration details.
 * Contains fields for goodsId, goodsName, prices, inventory, and more.
 */
@Getter
public class SeckillProductRegistrationDTO {
    private String goodsId;
    private String goodsName;
    private BigDecimal originalPrice;
    private BigDecimal discountPrice;
    private String masterImg;
    private String intro;
    private String address;
    private LocalDate beginTime;
    private LocalDate endTime;
    private Integer postage;
    private Integer inventory;
    private Integer saleVolume;
    private String videoUrl;
}
