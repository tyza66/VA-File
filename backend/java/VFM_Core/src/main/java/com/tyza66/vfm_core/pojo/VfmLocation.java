package com.tyza66.vfm_core.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: tyza66
 * Date: 2023/7/22 14:49
 * Github: https://github.com/tyza66
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VfmLocation {
    Integer id;
    String vfmlLocation;
    String vfmlLocationUuid;
    String createUserId;
    String createUserName;
    String updateUserId;
    String updateUserName;
    Boolean isDelete;
}
