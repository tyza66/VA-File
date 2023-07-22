package com.tyza66.vfm_core.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: tyza66
 * Date: 2023/7/22 15:21
 * Github: https://github.com/tyza66
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VfmUser {
    Integer id;
    String vfmuNameUuid;
    String vfmuUsername;
    String vfmuPassword;
    String createUserId;
    String createUserName;
    String updateUserId;
    String updateUserName;
    Boolean isDelete;
}
