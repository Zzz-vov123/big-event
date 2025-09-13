package com.Zzz.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Category {
    private Integer id;
    private String categoryName;
    private String categoryAlias;//分类别名
    private Integer createUser;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
