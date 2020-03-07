package com.shishuheng.webdemo.domain.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.shishuheng.webdemo.domain.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author shishuheng
 * @date 2020/3/6 3:53 下午
 */
@Data
@Entity
@ApiModel(value = "客户端")
public class Client extends BaseEntity implements ClientDetails {
    @Column(name = "client_id", nullable = false, unique = true)
    @ApiModelProperty(value = "客户端id")
    private String clientId;

    @Column(name = "client_secret", nullable = false)
    @ApiModelProperty(value = "客户端密码")
    private String clientSecret;

    @Column(name = "description")
    @ApiModelProperty(value = "客户端描述")
    private String description;

    @Column(name = "access_token_expire", nullable = false)
    @ApiModelProperty(value = "客户端AccessToken过期时间")
    private Integer accessTokenExpire;

    @Column(name = "refresh_token_expire", nullable = false)
    @ApiModelProperty(value = "客户端RefreshToken过期时间")
    private Integer refreshTokenExpire;

    @Column(name = "resource_json")
    @ApiModelProperty(value = "资源id的JSON")
    private String resourceJson;

    @Column(name = "scope_json")
    @ApiModelProperty(value = "范围的JSON")
    private String scopeJson;

    @Column(name = "grant_type_json")
    @ApiModelProperty(value = "授权类型JSON")
    private String grantTypeJson;

    @Column(name = "redirect_uri_json")
    @ApiModelProperty(value = "重定向地址")
    private String redirectUriJson;

    @Column(name = "authorities_json")
    @ApiModelProperty(value = "权限JSON")
    private String authoritiesJson;

    @Transient
    @ApiModelProperty(value = "附加信息")
    private Map<String, Object> additionalInformation;

    @Override
    public Set<String> getResourceIds() {
        return jsonStringToStringSet(resourceJson);
    }

    @Override
    public boolean isSecretRequired() {
        return false;
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        return jsonStringToStringSet(scopeJson);
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return jsonStringToStringSet(grantTypeJson);
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return jsonStringToStringSet(redirectUriJson);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return jsonStringToAuthoritySet(authoritiesJson);
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenExpire;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenExpire;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }

    /**
     * json转字符串set
     *
     * @param json
     * @return
     */
    private Set<String> jsonStringToStringSet(String json) {
        if (null == json || "".equals(json)) {
            return null;
        }
        Set<String> set = new HashSet<>();
        try {
            JSONArray array = JSON.parseArray(json);
            for (int i = 0; i < array.size(); i++) {
                String value = array.getString(i);
                set.add(value);
            }
        } catch (Exception e) {
            // 若转换失败则认为此处非json字符串
            set.add(json);
        }
        return set;
    }

    /**
     * json转权限set
     *
     * @param json
     * @return
     */
    private Set<GrantedAuthority> jsonStringToAuthoritySet(String json) {
        if (null == json || "".equals(json)) {
            return null;
        }
        Set<GrantedAuthority> set = new HashSet<>();
        try {
            JSONArray array = JSON.parseArray(json);
            for (int i = 0; i < array.size(); i++) {
                String value = array.getString(i);
                GrantedAuthority authority = new SimpleGrantedAuthority(value);
                set.add(authority);
            }
        } catch (Exception e) {
            // 若转换失败则认为此处非json字符串
            GrantedAuthority authority = new SimpleGrantedAuthority(json);
            set.add(authority);
        }
        return set;
    }
}
