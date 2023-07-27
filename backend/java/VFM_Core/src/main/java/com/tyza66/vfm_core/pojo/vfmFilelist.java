package com.tyza66.vfm_core.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: tyza66
 * Date: 2023/7/27 17:30
 * Github: https://github.com/tyza66
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class vfmFilelist {
    Integer id;
    String size;
    String name;
    String end;
    String path;
    String about;
}
