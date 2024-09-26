package com.aidis.teama.user.model;

import com.aidis.teama.user.db.GoogleUserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;



@Getter
@Setter
public class CustomUserDetails implements UserDetails {

    private final GoogleUserEntity user;

    public CustomUserDetails(GoogleUserEntity user) {
        this.user = user;
    }

    // GoogleUserEntity에서 필요한 필드를 직접 접근할 수 있게 Getter 추가
    public GoogleUserEntity getUser() {
        return this.user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한이 필요하다면 GrantedAuthority 목록을 반환하도록 구현 가능
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }


    @Override
    public boolean isAccountNonExpired() {
        // 계정 만료 여부, 필요시 로직 변경
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정이 잠금 상태가 아닌지 여부
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 자격 증명이 만료되지 않았는지 여부
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 계정이 활성화되었는지 여부
        return true;
    }
}
