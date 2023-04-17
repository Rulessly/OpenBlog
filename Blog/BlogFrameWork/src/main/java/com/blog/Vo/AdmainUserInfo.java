package com.blog.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmainUserInfo {
    private List<String> permissions;
    private List<String> roles;
    private UserInfo user;
}
