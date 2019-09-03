package com.restapi.smart.security.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//com.restapi.smart.security.domain.Account
@Data   @NoArgsConstructor
public class Account {
    private String userid;
    private String userpw;
    private String enabled;
    private List<UserGrantedAuthority> auth;
}
/*
package com.restapi.smart.security.domain

import javax.persistence.*

@Entity
@Table(name = "ACCOUNT")
data class Account(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,

        @Column(name = "ACCOUNT_USERNAME")
        val username: String? = null,

        @Column(name = "ACCOUNT_LOGINID")
        val userId: String? = "emalyun@naver.com",

        @Column(name = "ACCOUNT_PASSWORD")
        var password: String? = "1234",

        @Column(name = "ACCOUNT_ROLE")
        @Enumerated(value = EnumType.STRING)
        var userRole: UserRole? = UserRole.USER,

        @Column(name = "ACCOUNT_SOCIAL_ID")
        var socialId: Long? = null,

        @Column(name = "ACCOUNT_SOCIAL_PROVIDER")
        @Enumerated(value = EnumType.STRING)
        var socialProvider: SocialProviders? = null,

        @Column(name = "ACCOUNT_SOCIAL_PROFILEPIC")
        var profileHref: String? = null) {
}
 */
