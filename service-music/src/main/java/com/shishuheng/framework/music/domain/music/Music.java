package com.shishuheng.framework.music.domain.music;

import com.shishuheng.framework.common.module.domain.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author shishuheng
 * @date 2020年 03月 13日 00:25:00
 */
@Data
@Entity
public class Music extends BaseEntity {
    @Column(name = "title")
    @ApiModelProperty(value = "标题")
    private String title;

    @Column(name = "artist")
    @ApiModelProperty(value = "艺术家")
    private String artist;

    @Column(name = "album")
    @ApiModelProperty(value = "专辑")
    private String album;

    @Column(name = "type")
    @ApiModelProperty(value = "音乐流派")
    private String type;

    @Column(name = "url")
    @ApiModelProperty(value = "链接")
    private String url;
}
