package com.suyh5802.web.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author suyh
 * @since 2023-12-27
 */
@TableName(value = "tb_recharge", autoResultMap = true)
@Data
public class RechargeEntity extends BaseMqVo {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String uid;
    private Long ctime; // 原来这个值的单位是s
    private BigDecimal goodsAmt;
    private String channel;
    private String chips;
    private Long vungoRechargeId;
    private String gaid;
    private String originChannel;
    private Long day;
    @TableField("`order`")
    private String order;
    private Long cts;
    private String pn;
    private Long mtime;
    private String loginChannel;
    private String registerChannel;

    // 业务数据
    @TableField(exist = false)
    private Long regDate;
    @TableField(exist = false)
    private Long statRegDate;
    @TableField(exist = false)
    private Long rechargeDate;
}
