package com.shishuheng.framework.music.service;

import com.shishuheng.framework.common.module.domain.permission.Permission;
import com.shishuheng.framework.common.module.service.BaseCommonService;
import com.shishuheng.framework.music.domain.music.Music;
import com.shishuheng.framework.music.domain.music.MusicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

/**
 * @author shishuheng
 * @date 2020年 03月 13日 00:31:23
 */
@Slf4j
@Service
public class MusicService extends BaseCommonService<Music> {
    @Autowired
    private MusicRepository repository;

    @Override
    public Set<Permission> initPermission() {
        return null;
    }

    @Override
    public void initStatus() {

    }

    @Override
    public void initEntity() {
        Music qilixiang = new Music();
        qilixiang.setAlbum("七里香");
        qilixiang.setArtist("周杰伦");
        qilixiang.setTitle("七里香");
        qilixiang.setType("Mandarin-Pop");
        qilixiang.setUrl("http://www.baidu.com");
        qilixiang.setCreatedBy("系统初始化");
        qilixiang.setCreatedDate(new Date());
        repository.save(qilixiang);
        log.info("七里香 初始化成功");
    }
}
