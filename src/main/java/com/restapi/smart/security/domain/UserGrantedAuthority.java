package com.restapi.smart.security.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

//com.restapi.smart.security.domain.UserGrantedAuthority
public class UserGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final String comp_auth;//유저에 대한 권한
    private final String comp_loc; //소속 업체
    private final String comp_seq; //업체 번호


    public UserGrantedAuthority() {
        this.comp_auth = "ROLE_DEFAULT";
        this.comp_loc = null;
        this.comp_seq = null;
    }
    public UserGrantedAuthority(String comp_auth, String comp_loc, String comp_seq) {
        this.comp_auth = comp_auth;
        this.comp_loc = comp_loc;
        this.comp_seq = comp_seq;
    }

    @Override
    public String getAuthority() {
        return comp_auth;
    }

    public String getComp_loc() {
        return comp_loc;
    }

    public String getComp_seq() {
        return comp_seq;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof UserGrantedAuthority) {
            return comp_auth.equals(((UserGrantedAuthority) obj).comp_auth);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.comp_auth.hashCode();
    }

    @Override
    public String toString() {
        return this.comp_auth;
    }

}
