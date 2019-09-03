package com.restapi.smart.security.dtos


import com.fasterxml.jackson.annotation.JsonProperty

data class FormLoginDto(

        @field:JsonProperty("id")
        val id: String? = null,

        @field:JsonProperty("password")
        val password: String? = null
)