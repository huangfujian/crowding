package com.gx.crowd.entity.vo;

import jdk.internal.org.objectweb.asm.util.ASMifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 14:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO implements Serializable {
    private Integer id;
    private String loginacct;
    private String userpswd;
    private String email;
    private String phoneNum;
    private String code;
}
