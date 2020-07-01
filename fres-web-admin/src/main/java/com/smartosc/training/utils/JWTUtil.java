package com.smartosc.training.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;

import com.smartosc.training.entity.AppUserDetails;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

    public static String getJwtTokenFromSecurityContext() {
        return ((AppUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getJwtToken();
    }

    public static HttpHeaders getHearder() {
        String authToken = JWTUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        header.setContentType(MediaType.APPLICATION_JSON);
		return header;
    }

}
