# Book Friends

## 목차
- [소개](#소개)
- [개발 환경](#개발-환경)
- [주요 기능](#주요-기능)
- [라이브러리 및 의존성](#라이브러리-및-의존성)
- [업데이트 로그](#업데이트-로그)

## 소개
Book Friends는 독서 애호가들을 위한 커뮤니티 플랫폼입니다. 회원들은 책 리뷰를 작성하고, 자유롭게 소통하며, 독서 모임을 모집할 수 있습니다. 또한, 국립중앙도서관 API를 통해 다양한 도서 정보를 검색할 수 있습니다.

이 프로젝트는 [BookFriends_Servlet](https://github.com/ehyunseok/BookFriends_Servlet.git)의 리팩토링 프로젝트입니다. JSP와 Servlet을 사용하여 시작된 이 프로젝트는 이제 Spring, Gradle, JPA 환경으로 리팩토링되고 있습니다.

### 초기 프로젝트 배경
BookFriends_Servlet 프로젝트는 JSP와 Servlet만을 사용하여 개발되었습니다. 이는 웹 애플리케이션 개발의 기초를 이해하고, 서블릿과 JSP를 통한 웹 개발의 기본 원리를 학습하기 위한 목적이었습니다. 이러한 환경은 웹 개발의 전반적인 흐름과 작동 방식을 파악하는 데 큰 도움이 되었으며, 클라이언트-서버 간의 상호작용을 깊이 이해할 수 있게 해주었습니다.

### 리팩토링의 필요성
기본적인 웹 애플리케이션이 완성된 후, 다음과 같은 이유로 프로젝트를 Spring, Gradle, JPA 환경에서 리팩토링하게 되었습니다:

1. **유지보수성 향상**: JSP와 Servlet만을 사용하는 애플리케이션은 규모가 커질수록 코드의 복잡성이 증가하고, 유지보수가 어려워집니다. Spring 프레임워크는 계층화된 아키텍처와 다양한 모듈을 제공하여 이러한 문제를 해결합니다.
2. **생산성 증대**: Spring Boot는 복잡한 설정 없이도 빠르게 애플리케이션을 설정하고 실행할 수 있게 해주며, Gradle은 빌드 자동화 도구로서 프로젝트의 빌드, 테스트, 배포 과정을 효율적으로 관리할 수 있게 합니다.
3. **데이터 처리의 효율성**: JPA (Java Persistence API)는 객체 관계 매핑(ORM)을 통해 데이터베이스 작업을 더욱 직관적이고 효율적으로 처리할 수 있게 해줍니다. 이는 데이터베이스와의 상호작용을 단순화하고, 코드의 가독성을 높입니다.
4. **확장성 및 안정성**: Spring 생태계는 다양한 기능과 확장성을 제공하며, 보안 및 트랜잭션 관리와 같은 엔터프라이즈급 기능을 쉽게 구현할 수 있게 해줍니다.
5. **커뮤니티 및 지원**: Spring은 대규모 커뮤니티와 풍부한 문서, 다양한 서드파티 라이브러리의 지원을 받습니다. 이는 문제 해결과 기능 확장에 큰 도움이 됩니다.

또한, **향후 Redis를 도입할 예정**입니다. Redis를 통해 데이터 캐싱, 세션 관리, 실시간 기능 구현 등의 성능 최적화를 달성할 것입니다.

### MVC 패턴
Book Friends 프로젝트는 Model-View-Controller (MVC) 패턴을 따릅니다. 이는 애플리케이션을 세 개의 주요 구성 요소로 분리하여 유지보수성과 확장성을 높이는 데 도움이 됩니다.

1. **Model**: 애플리케이션의 데이터와 비즈니스 로직을 관리합니다. JPA를 사용하여 데이터베이스와 상호작용하며, 도메인 객체와 비즈니스 로직을 정의합니다.
2. **View**: 사용자에게 데이터를 표시하는 부분입니다. JSP를 사용하여 데이터를 렌더링하고, HTML, CSS, JavaScript를 통해 사용자 인터페이스를 구성합니다.
3. **Controller**: 사용자의 요청을 처리하고, Model과 View를 연결하는 역할을 합니다. Spring MVC 프레임워크를 사용하여 HTTP 요청을 처리하고, 적절한 View로 데이터를 전달합니다.

### 프로젝트 구조
Book Friends 프로젝트는 다음과 같은 계층 구조를 따릅니다:

1. **Entity**: 데이터베이스 테이블과 매핑되는 도메인 객체를 정의합니다.
2. **DTO (Data Transfer Object)**: 계층 간 데이터 교환을 위한 객체를 정의합니다. 주로 컨트롤러와 서비스 간의 데이터 전달에 사용됩니다.
3. **Service**: 비즈니스 로직을 처리하며, 트랜잭션을 관리합니다.
4. **Controller**: 클라이언트의 HTTP 요청을 처리하고, 서비스 계층을 호출하여 결과를 반환합니다.
5. **Repository**: 데이터베이스 접근을 담당하며, JPA를 사용하여 데이터 조작을 수행합니다.

## 개발 환경
- **Backend**: Spring Boot 3.3.0, JPA
- **Frontend**: JSP, JavaScript, CSS
- **Database**: MySQL
- **Build Tool**: Gradle

## 주요 기능
1. **회원 관리**
   - 회원 가입 (이메일 인증 포함)
   - 로그인 및 로그아웃
2. **서평 관리**
   - 서평 작성, 검색, 추천, 수정, 삭제
   - 조건부 조회 (최신순, 추천순)
3. **자유게시판**
   - 게시글 작성, 댓글 작성, 추천, 수정, 삭제, 검색
   - 조건부 조회 (최신순, 추천순, 조회수순, 댓글순)
4. **실시간 채팅**
5. **독서 모임 모집**
   - 모집글 작성, 수정, 삭제
   - 조건부 조회 (최신순, 모집상태)
6. **국립중앙도서관 API 연동**
   - 전국 공공도서관 소장자료, ISBN 서지정보, 사서추천도서 검색

## 라이브러리 및 의존성
- **Spring Boot**
  - `spring-boot-starter-data-jpa:3.3.0`
  - `spring-boot-starter-security:3.3.0`
  - `spring-boot-starter-validation:3.3.0`
  - `spring-boot-starter-web:3.3.0`
  - `spring-boot-starter-mail:3.3.0`
  - `spring-boot-devtools:3.3.0`
- **JPA**
  - `spring-boot-starter-data-jpa:3.3.0`
- **MySQL**
  - `mysql-connector-java:8.0.33`
- **Lombok**
  - `org.projectlombok:lombok:1.18.22`
- **JUnit**
  - `spring-boot-starter-test:3.3.0`
  - `spring-security-test:6.3.0`
  - `junit-vintage-engine:5.8.2`
  - `h2database:1.4.200`
- **ModelMapper**
  - `org.modelmapper:modelmapper:3.2.0`
- **JSP**
  - `tomcat-embed-jasper:10.1.20`
  - `jakarta.servlet.jsp.jstl:3.0.0`
- **JWT**
  - `java-jwt:4.4.0`
- **Spring Security**
  - `spring-security-core:6.3.0`
  - `spring-security-web:6.3.0`
  - `spring-security-config:6.3.0`

## 업데이트 로그
- 2024.06.04.
  - 프로젝트 시작
  - 개발 환경 설정
- 2024.06.05.
  - 회원가입 기능 구현
- 2024.06.06.
  - 로그인 및 로그아웃 기능 구현
  - 회원가입 - 이메일 인증 구현
  - 자유게시판 등록,수정
