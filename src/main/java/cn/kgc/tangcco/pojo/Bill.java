package cn.kgc.tangcco.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Bill {


    private Integer id;//主键

    private String billCode;//账单编码

    private String productName;//商品名称

    private String productDesc;//商品描述

    private String productUnit;//商品单位

    private double productCount;//商品数量

    private double totalPrice;//商品总数;

    private Integer isPayment;//是否支付

    private Integer createdBy;//创建者id

    private Date creationDate;//创建时间

    private Integer modifyBy;//更新者

    private Date modifyDate;//更新时间

    private Integer providerId;//供应商id

    private String providerName;
    //供应商名称



}
