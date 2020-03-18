package com.shishuheng.framework.servicegateway.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shishuheng
 * @date 2020/3/18 11:46 上午
 */
@Configuration
public class SwaggerDocumentationConfig implements SwaggerResourcesProvider {
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> list = new ArrayList<>();
        list.add(swaggerResource("/authentication/v2/api-docs", "系统管理", "1.0", null));
        list.add(swaggerResource("/music/v2/api-docs", "音乐管理", "1.0", null));
        return list;
    }

    private SwaggerResource swaggerResource(String location, String name, String version, String url) {
        SwaggerResource resource = new SwaggerResource();
        resource.setLocation(location);
        resource.setName(name);
        resource.setSwaggerVersion(version);
        resource.setUrl(url);
        return resource;
    }
}
