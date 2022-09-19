package com.lara.Auth2ActiveDirectory.config;

import com.azure.spring.cloud.autoconfigure.aad.implementation.webapp.AadOAuth2UserService;
import com.azure.spring.cloud.autoconfigure.aad.properties.AadAuthenticationProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lara.Auth2ActiveDirectory.model.User;
import net.minidev.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.io.DataInput;
import java.util.HashSet;
import java.util.Set;

public class CustomADOAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    AadOAuth2UserService aadOAuth2UserService;

    public CustomADOAuth2UserService(AadAuthenticationProperties aadAuthProps) {
        this.aadOAuth2UserService = new AadOAuth2UserService(aadAuthProps);
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser user = aadOAuth2UserService.loadUser(userRequest);
        Set<GrantedAuthority> mapAuthorities = new HashSet<>(user.getAuthorities());
        String userName = (String) user.getIdToken().getClaims().get("unique_name");
        System.out.println(String.format("El user es el siguiente: ", userName));
        return user;
    }

    // TODO se puede usar para manejar respuestas staticas crenado un objecto vacio
    private String getUserNameAttrName(OAuth2UserRequest userRequest) {
        String userNameAttrName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        if (userNameAttrName.isEmpty()) {
            userNameAttrName = "name";
        }
        return userNameAttrName;
    }
}
