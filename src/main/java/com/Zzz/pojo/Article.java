package com.Zzz.pojo;
import lombok.Data;

import java.time.LocalDateTime;
@Data

public class Article {
    private Integer id;
    private String title;
    private String content;
    private String coverImg;
    private String state;//发布状态
    private Integer categoryId;
    private Integer createUser;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
