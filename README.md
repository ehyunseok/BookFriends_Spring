# Book Friends

## 목차
- [소개](#소개)
- [개발 환경](#개발-환경)
- [시스템 구조(System Architecture)](#시스템-구조)
- [주요 기능](#주요-기능)
- [실행 화면](#실행-화면)
- [ERD](#ERD)
- [라이브러리 및 의존성](#라이브러리-및-의존성)
- [트러블 슈팅](#트러블-슈팅)
- [업데이트 로그](#업데이트-로그)

## 소개
Book Friends는 독서 애호가들을 위한 커뮤니티 플랫폼입니다. 회원들은 책 리뷰를 작성하고, 자유롭게 소통하며, 독서 모임을 모집할 수 있습니다. 또한, 국립중앙도서관 API를 통해 다양한 도서 정보를 검색할 수 있습니다.

이 프로젝트는 [BookFriends_Servlet](https://github.com/ehyunseok/BookFriends_Servlet.git)의 리팩토링 프로젝트입니다.
JSP와 Servlet을 사용하여 시작된 프로젝트를 Spring, Gradle, JPA 환경으로 리팩토링하였습니다.
또한, Redis를 도입하여 데이터 캐싱과 성능 최적화를 달성하였습니다.
현재 Docker 컨테이너화 및 AWS 클라우드 인프라를 활용하여 배포 중입니다.

### 프로젝트 링크
#### [독서친구(Book Friends)](http://15.164.201.222:61084/bookfriends/)

### 프로젝트 기간
`2024.06.04.~2024.07.06.`


### 초기 프로젝트 배경
BookFriends_Servlet 프로젝트는 JSP와 Servlet만을 사용하여 개발되었습니다. 이는 웹 애플리케이션 개발의 기초를 이해하고, 서블릿과 JSP를 통한 웹 개발의 기본 원리를 학습하기 위한 목적이었습니다. 이러한 환경은 웹 개발의 전반적인 흐름과 작동 방식을 파악하는 데 큰 도움이 되었으며, 클라이언트-서버 간의 상호작용을 깊이 이해할 수 있게 해주었습니다.

### 리팩토링의 필요성
기본적인 웹 애플리케이션이 완성된 후, 다음과 같은 이유로 프로젝트를 Spring, Gradle, JPA 환경에서 리팩토링하게 되었습니다:

1. **유지보수성 향상**: JSP와 Servlet만을 사용하는 애플리케이션은 규모가 커질수록 코드의 복잡성이 증가하고, 유지보수가 어려워집니다. Spring 프레임워크는 계층화된 아키텍처와 다양한 모듈을 제공하여 이러한 문제를 해결합니다.
2. **생산성 증대**: Spring Boot는 복잡한 설정 없이도 빠르게 애플리케이션을 설정하고 실행할 수 있게 해주며, Gradle은 빌드 자동화 도구로서 프로젝트의 빌드, 테스트, 배포 과정을 효율적으로 관리할 수 있게 합니다.
3. **데이터 처리의 효율성**: JPA (Java Persistence API)는 객체 관계 매핑(ORM)을 통해 데이터베이스 작업을 더욱 직관적이고 효율적으로 처리할 수 있게 해줍니다. 이는 데이터베이스와의 상호작용을 단순화하고, 코드의 가독성을 높입니다.
4. **확장성 및 안정성**: Spring 생태계는 다양한 기능과 확장성을 제공하며, 보안 및 트랜잭션 관리와 같은 엔터프라이즈급 기능을 쉽게 구현할 수 있게 해줍니다.
5. **커뮤니티 및 지원**: Spring은 대규모 커뮤니티와 풍부한 문서, 다양한 서드파티 라이브러리의 지원을 받습니다. 이는 문제 해결과 기능 확장에 큰 도움이 됩니다.

### Redis 도입
Redis를 통해 데이터 캐싱, 세션 관리, 실시간 기능 구현 등의 성능 최적화를 달성하였습니다. 이를 통해 사용자 경험이 향상되고, 서버 부하를 줄일 수 있었습니다.

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

## 시스템 구조
### System Architecture
![architecture](https://github.com/ehyunseok/BookFriends_Spring/assets/121013391/9a11d805-25b3-4dd0-9457-b5e6d3c3adb0)



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
- 국립중앙도서관 소장자료 검색
- 사서추천도서 조회

## 실행 화면
### 회원가입
https://github.com/ehyunseok/BookFriends_Spring/assets/121013391/cdf9058b-5d7d-42d4-925f-efa695e84d7f

---

### 로그인
https://github.com/ehyunseok/BookFriends_Spring/assets/121013391/3c8bdb91-08a5-4fcd-82e2-31b1369c25a8

---

### 메인
![메인](https://github.com/ehyunseok/BookFriends_Spring/assets/121013391/713934aa-5d4b-4a11-b3dc-d5168ce44893)

---

### 서평
#### 서평 메인(조건부 조회/검색)
https://github.com/ehyunseok/BookFriends_Spring/assets/121013391/803ff783-5829-423f-b4a9-600c84168b94

---

#### 서평 게시글(조회/작성/수정/삭제)
https://github.com/ehyunseok/BookFriends_Spring/assets/121013391/0ba3e2fe-d159-4807-8976-3af65ca57749

---

### 국립중앙도서관 API
#### 사서 추천 도서(전체/조건부 조회)
https://github.com/ehyunseok/BookFriends_Spring/assets/121013391/5d69e895-a9fe-411c-a303-3024ec081e48

---

#### 국립중앙도서관 소장도서 검색
https://github.com/ehyunseok/BookFriends_Spring/assets/121013391/0021cdba-281f-447e-b143-5e41561447fe

---

### 독서모임
#### 독서모임 메인(조건부 조회/검색)
https://github.com/ehyunseok/BookFriends_Spring/assets/121013391/1c68fe98-2740-4fb8-b5b3-061e727bb76e

---

#### 독서모임 게시글(조회/작성/수정)
https://github.com/ehyunseok/BookFriends_Spring/assets/121013391/c571231d-6218-48f8-a7c9-1e99076f6ab8

---

#### 독서모임 댓글(작성/수정/삭제)
https://github.com/ehyunseok/BookFriends_Spring/assets/121013391/fb95615c-7386-4345-be7c-ee31e556e691

---

### 채팅
https://github.com/ehyunseok/BookFriends_Spring/assets/121013391/efb839db-2d6d-44c8-9e6a-1aef0ff4f91e

---


## ERD
![BookFriendsERD2](https://github.com/ehyunseok/BookFriends_Spring/assets/121013391/be562742-f169-4f59-af47-5539d8937aa9)

---


## 라이브러리 및 의존성
- **Spring Boot**
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-security`
  - `spring-boot-starter-validation`
  - `spring-boot-starter-web`
  - `spring-boot-starter-mail`
  - `spring-boot-devtools`
- **JPA**
  - `spring-boot-starter-data-jpa`
- **MySQL**
  - `mysql-connector-java:8.0.33`
- **Lombok**
  - `org.projectlombok:lombok`
- **JUnit**
  - `spring-boot-starter-test`
  - `spring-security-test`
  - `junit-vintage-engine`
  - `h2`
- **ModelMapper**
  - `org.modelmapper:modelmapper:3.2.0`
- **JSP**
  - `tomcat-embed-jasper:10.1.20`
  - `jakarta.servlet.jsp.jstl`
  - `org.glassfish.web:jakarta.servlet.jsp.jstl`
  - `jakarta.servlet:jakarta.servlet-api`
- **JWT**
  - `java-jwt:4.4.0`
- **Spring Security**
  - `spring-security-core:6.3.0`
  - `spring-security-web:6.3.0`
  - `spring-security-config:6.3.0`
  - `spring-security-taglibs:6.3.0`
- **Jackson**
  - `com.fasterxml.jackson.core:jackson-databind:2.17.1`
  - `com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.17.1`
- **XML Binding**
  - `jakarta.xml.bind:jakarta.xml.bind-api:4.0.2`
- **Redis**
  - `spring-boot-starter-data-redis:3.3.0`
- **Cache**
  - `spring-boot-starter-cache:3.3.0`

## 트러블 슈팅
### 1. **태그 라이브러리 의존성 문제**
- **문제** : 최신 버전의 의존성으로 업데이트한 후 JSP 페이지에서 태그 라이브러리 관련 오류 발생
- **상황**
  프로젝트의 의존성을  최신 버전으로 업데이트한 후, 기존에 잘 동작하던 JSP 페이지가 태그 라이브러리 관련 오류를 발생시켜 페이지가
  정상적으로 렌더링되지 않음

- **원인 분석**
  Spring Boot 3.x 이상 버전으로 업데이트하면서, 프로젝트의 의존성 중 JSP 및 태그 라이브러리 관련 부분이 Jakarta EE로 전환되었지만,
  기존 JSP 파일에서는 이전 버전의 태그 라이브러리를 참조하고 있었음
  특히, `<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>` 태그가
  최신 버전에서는 `<%@ taglib uri="jakarta.tags.core" prefix="c" %>`로 변경되어야 함

- **해결 방법**
  - **의존성 확인**:
    `build.gradle` 파일을 확인하여 JSP 및 태그 라이브러리 관련 의존성이  Jakarta EE와 호환되는 최신 버전으로 설정되었는지 확인
  - **코드 수정**:
    모든 JSP 페이지에서 태그 라이브러리 참조를 최신 Jakarta EE 버전에 맞게 수정
  - **테스트**:
    JSP 페이지를 로드하여 태그 라이브러리 참조 문제가 해결되었는지 확인하고, 전체 기능에 대한 회귀 테스트를 수행

- **결과**
  태그 라이브러리 의존성 불일치 문제를 해결하여 JSP 페이지가 정상적으로 렌더링 됨.
  최신 의존성에 맞게 프로젝트가 안정화되고, 코드 호환성이 확보됨


### 2. **XML 및 JSON  충돌 데이터 형식 충돌**
- **문제**: XML 및 JSON 데이터 형식 충돌로 인한 데이터 처리 오류 발생
- **상황**
  국립중앙도서관 API를 통해 사서 추천 도서 및 소장자료를 조회하는 기능을 구현했으나, 해당 API가 XML 형식으로 데이터를 반환함.
  프로젝트의 다른 기능은 JSON 혁식을 사용하고 있어 데이터 형식 불일치로 인한 충돌이 발생하여 데이터 처리에 문제 발생

- **원인 분석**
  프로젝트에서 XML 형식과 JSON 형식을 동시에 처리하는 로직이 필요했으나, 이를 명확히 구분하지 않고 데이터 처리를 시도하면서 오류 발생

- **해결 방법**
  - **의존성 추가**:
    `build.gradle` 파일에 XML 및 JSON 관련 라이브러리를 추가하여 데이터 형식 변환을 지원
  - **WebConfig 설정**:
    WebConfig 파일을 수정하여 XML과 JSON 형식을 명확히 구분하고 처리할 수 있도록 설정
  - **데이터 변환 로직 구현**:
    국립중앙도서관 API의 XML 데이터를 JSON 형식으로 변환하는 로직을 추가하여 일관된 데이터 처리가 가능하도록 개선

- **결과**
  XML 및 JSON 데이터 형식 출돌 문제를 해결하여 모든 API 응답을 일관된 형식으로 처리함.
  데이터 형식 불일치로 인한 오류가 감소하고, 시스템의 안정성이 향상됨


### 3. **채팅 전송 오류**
- **문제**: 컨트롤러 메소드 누락으로 인한 채팅 메시지 전송 오류 발생
- **상황**
  실시간 채팅 기능을 구현한 후, 사용자가 채팅 메시지를 전송할 때 메시지가 정상적으로 전달되지 않고 오류가 발생
  로그에서 채팅 내역을 처리하는 메소드가 호출되지 않음을 확인

- **원인 분석**
  채팅 내역을 가져오는 메소드가 컨트롤러 클래스에 누락되어, 클라이언트에서 전송된 요청을 서버가 처리하지 못함

- **해결 방법**
  - **메소드 추가**:
    채팅 내역을 가져오는 메소드를 컨트롤러 클래스에 추가
  - **엔드포인트 설정**:
    클라이언트에서 올바르게 채팅 내역을 요청할 수 있도록 엔드포인트 설정
  - **테스트**:
    채팅 기능을 테스트하여 채팅 내역이 정상적으로 전송되고 처리되었는지 확인

- **결과**
  메소드 누락 문제를 해결하여 채팅 내역이 정상 전송  처리
  실시간 채팅 기능 안정화 및 사용자 경험 개선

### 4. Docker를 사용한 Spring Boot 애플리케이션에서 JSP 파일 로딩 문제 해결
- **문제**:Docker 컨테이너 내에서 Spring Boot 애플리케이션을 실행할 때, JSP 파일을 로드하지 못해 HTTP 404 오류가 발생. 이는 컨테이너 내부의 경로 설정 문제로 인해 발생한 것.

- **해결 방법**
  - **Dockerfile 수정**: JSP 파일을 올바른 위치로 복사하고, WAR 파일로 애플리케이션을 실행하도록 Dockerfile을 수정
  ```dockerfile
  FROM eclipse-temurin:21-jre
  ARG WAR_FILE=build/libs/*.war
  COPY ${WAR_FILE} app.war
  
  # JSP files need to be available in the exploded WAR structure
  COPY src/main/webapp/WEB-INF/views /WEB-INF/views
  
  EXPOSE 8001
  ENTRYPOINT ["java", "-jar", "/app.war"]
  ```
  - **docker-compose 파일 수정**: JSP 파일과 업로드 디렉토리를 올바르게 매핑하도록 docker-compose.yml 파일을 수정
  
- **결과**
  Docker와 Spring Boot 설정을 수정하여 JSP 파일을 올바르게 로드할 수 있었다. 위의 설정을 통해 Docker 컨테이너 내에서 JSP 파일을 포함한 Spring Boot 애플리케이션을 성공적으로 실행할 수 있다.

### 5. AWS EC2 프리티어 메모리 부족 현상
- **문제**:AWS EC2 프리티어로 제공하는 인스턴스 유형인 `t2.micro`의 RAM이 약1GB에 불과하다. 그렇기에 gradle 빌드 작업 시, 서버가 멈추는 현상이 발생하였다.
- **해결방법**
  - **RAM 증설**
    : 리눅스에서는 SWAP 메모리를 지정할 수 있다.(SWAP메모리란?-RAM이 부족할 경우, HDD의 일정 공간을 마치 RAM처럼 사용하는 것)
    - dd 명령어를 통해 SWAP 메모리 할당
      ```sudo dd if=/dev/zero of=/swapfile bs=128M count=16```
      - 128mb씩 16개의 공간을 만듦(약 2GB)
    - SWAP 파일에 대한 읽기 및 쓰기 권한 업데이트
      ```$ sudo chmod 600 /swapfile```
    - Linux SWAP 영역을 설정
      ```$ sudo mkswap /swapfile```
    - SWAP 공간에 SWAP 파일을 추가하여 SWAP 파일을 즉시 사용할 수 있도록 만듦
      ```$ sudo swapon /swapfile```
    - /etc/fstab 파일을 편집하여 부팅 시 SWAP 파일을 활성화함
      - 편집기에서 파일 열기
        ```$ sudo vi /etc/fstab```
      - 파일 끝에 다음 내용 추가한 뒤 저장 및 종료
        ```/swapfile swap swap defaults 0 0```
- **결과**
  ![image](https://github.com/ehyunseok/BookFriends_Spring/assets/121013391/c653b685-191f-4954-8f77-1b30bc014a7e)
  CPU 사용률이 증설 이전에는 99.9퍼센트였으나, 증설 이후 확연히 준 것을 확인할 수 있다.
- **참고**: [얇고 넓은 개발 블로그-](https://sundries-in-myidea.tistory.com/102)
  


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
- 2024.06.07.
  - csrf 보호 적용
- 2024.06.08.
  - 게시글 삭제/추천, 댓글 등록/수정/삭제/추천
  - 서평 메인(조건부 조회, 검색어 조회, 페이지네이션)
  - 서평 게시글 등록/수정/삭제/추천
- 2024.06.09.
  - 모임모집 메인(조건부조회, 검색어 조회, 페이지네이션)
  - 모집글 등록/수정/삭제
  - 댓글 등록/수정/삭제
  - 채팅 UI 완성
  - 채팅 메인: 새로운 메시지 개수 배지, 채팅리스트(새로운 메시지 개수 배지)
  - 실시간 채팅페이지: 메시지 보내기 기능 시작
- 2024.06.10.
  - 실시간 채팅페이지: 메시지 반복 출력 오류 해결
  - 할것- 채팅 메인 메시지 리스트 새로운 메시지 개수 배지 수정해야함
- 2024.06.11.
  - 국립중앙도서관 OpenAPI로 국중 소장자료 검색, 사서추천도서 기능 구현 완료
  - 사서추천도서-연도별, 분류별(문학,인문,사회,자연,기타) 조회가 가능함
  - 할것-메인 페이지 수정
- 2024.06.12.
  - 자유게시판, 독서모임 모집 게시글 작성 시 이미지 파일 업로드 오류 문제 처리(xml, json 충돌 문제 해결)
  - 메인 페이지 UI 수정
  - Redis 적용 시작
- 2024.06.13.
  - 서평, 자유게시판 Redis 적용 완료
  - 독서모임 Redis 적용 중
- 2024.06.17.
  - 기존 코드 오류를 해결하고 Redis를 적용하기로 결정함
  - 의존 라이브러리에 맞게 코드 수정
  - 댓글 정렬 코드 오류 해결
- 2024.06.18.
  - 채팅 메인화면 마지막 메시지 불러오는 코드 수정
  - 실시간 채팅 페이지
    - 서버 컨트롤러에서 누락된 메소드 추가
    - 클라이언트 화면에 메시지 리스트 중복 출력 문제 해결
    - UI 약간 수정
- 2024.06.19.
  - JUnit 기능 테스트
  - Redis 적용 완료
    - Member - 로그인 세션, 사용자 권한 캐싱
    - Board - 게시글 목록, 특정 게시글 조회, 댓글 목록 캐싱. 게시글 및 댓글 등록/수정/삭제 시 캐시 무효화
    - Recruit - 게시글 목록, 특정 게시글 조회, 댓글 목록 캐싱. 게시글 및 댓글 등록/수정/삭제 시 캐시 무효화
    - Review - 게시글 목록, 특정 게시글 조회, 캐싱. 게시글 등록/수정/삭제 시 캐시 무효화
    - Likey - 추천 상태 변경 시 관련 엔티티의 캐시 무효화 및 캐시 업데이트
    - Chat - 메시지 보내기 및 메시지 읽음 처리 시 캐시 무효화
    - Library - 사서추천도서 조회, 소장자료 검색 캐싱
- 2024.06.25
  - 모임 모집 게시글 댓글 작성 시간 표기
  - 로그인 페이지 알림창 추가
- 2024.06.27
  - 도커 이미지 빌드 프로세스 도입 시작
    - 프로젝트의 도커 이미지 자동 빌드 및 배포 워크플로우 설정 시작
- 2024.06.30
  - 도커를 사용한 Springboot JSP 파일 로딩 문제 해결
- 2024.07.06
  - 배포 시작
