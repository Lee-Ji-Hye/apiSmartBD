package com.restapi.smart.security.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.restapi.smart.security.domain.SocialProviders

data class SocialLoginDto(
        @field:JsonProperty("provider")
        val provider: SocialProviders? = null,

        @field:JsonProperty("token")
        val token: String? = null
)