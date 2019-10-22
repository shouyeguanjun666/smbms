package cn.kgc.tangcco.pojo;

import lombok.Data;

import java.util.Date;
@Data
public class Provider {

    private Integer id;
    private String proCode;
    private String proName;
    private String proDesc;
    private String proContact;
    private String proPhone;
    private String proAddress;
    private String proFax;
    private Integer createdBy;
    private Date creationDate;
    private Date modifyDate;
    private Integer modifyBy;

}
