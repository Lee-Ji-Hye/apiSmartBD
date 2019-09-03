# https://github.com/auth0/java-jwt 
# https://github.com/wheejuni/spring-jwt / youtube 봄이네집 스프링 - (1) Spring Security - Auth0 JWT Library
# JPA -> Oracle mybatis로 변경해서 사용함
# Spring-boot-REST-API
## Build Maven Project
## kosmo50 team project

###mybatis
Ojdbc Oracle 연동 드라이버를 수동으로 lib폴더에 넣고 pom에서 불러와지도록 했음

인증된 사용자는 모든 controller 접근 가능(default설정값)

/test만 모든 사용자 접근 가능(SecurityConfig.java 에서 설정)

