package com.gx.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 14:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressVO implements Serializable {
    private Integer id;
    private String receiveName;
    private String phoneNum;
    private String address;
}
