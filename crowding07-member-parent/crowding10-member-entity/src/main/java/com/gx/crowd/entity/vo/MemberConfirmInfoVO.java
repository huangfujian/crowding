package com.gx.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 20:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberConfirmInfoVO implements Serializable {
    private Integer id;

    private Integer memberid;

    private String paynum;

    private String cardnum;
}
