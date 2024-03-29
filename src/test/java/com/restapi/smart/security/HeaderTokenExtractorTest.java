package com.restapi.smart.security;

import com.restapi.smart.security.jwt.HeaderTokenExtractor;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class HeaderTokenExtractorTest {

    private HeaderTokenExtractor extractor = new HeaderTokenExtractor();
    private String header;

    @Before
    public void setUp(){
        this.header = "Bearer asdfhak.asdfdhfkefjdsalfj";
    }

    @Test
    public void TEST_JWT_EXTRACT(){
        assertThat(extractor.extract(this.header), is("asdfhak.asdfdhfkefjdsalfj"));
    }

}