# SpringBoot-Project-Board Service

![메인화면](https://github.com/yashin20/BoardServiceV2/assets/92693776/f5f42a21-c5fa-4e1c-97b9-e2d779193e1a)

## 목차
 - [1. 프로젝트 소개](#1-프로젝트-소개)
   - [1-1. 프로젝트 소개](#1-1-프로젝트-소개)
   - [1-2. 프로젝트 기능](#1-2-프로젝트-기능)
   - [1-3. 개발 환경](#1-3-개발-환경)
   - [1-4. 실행 화면](#1-4-실행-환경)
  
 - [2. 프로젝트 구조](#2-프로젝트-구조)
   - [2-1. 패키지 구조](#2-1-패키지-구조)
   - [2-2. DB 설계](#2-2-DB-설계)
   - [2-3. API 설계](#2-3-API-설계)
  
 - [개발 내용](#개발-내용)

 - [마치며](#마치며)
   - [1. 프로젝트 보완사항](#1-프로젝트-보완사항)
   - [2. 프로젝트 과정에서 발생한 문제](#2-프로젝트-과정에서-발생한-문제)
   - [3. 후기](#3-후기)
  
     


## 1. 프로젝트 소개

### 1-1. 프로젝트 소개

아이템 선정 이유는 웹 개발에 대한 이해를 높이고, 실제 서비스를 구현하면서 프로그램 능력 향상시키기 위해 이 프로젝트를 시작했습니다.  
Spring Boot를 이용한 게시판 서비스는 기본적인 CRUD 기능을 포함하고 있어 웹 서비스의 기본 원리를 학습하는 데 매우 적합하다고 생각했습니다.  
이 프로젝트를 통해 서버-클라이언트 구조, 데이터베이스 연동, RESTful API 설계 등 웹 애플리케이션의 핵심 개념을 실습할 수 있었습니다.  


### 1-2. 프로젝트 기능

게시판 서비스의 주요 기능은 다음과 같습니다.

게시판  
- 게시글 CRUD 기능
- 게시글 정렬 기능 (조회수, 작성일자)
- 게시글 페이징
- 게시글 검색 기능

사용자
 - Security 회원가입 및 로그인 기능
 - 회원 정보 수정
 - 회원 탈퇴
 - 유효성 및 중복 검사

댓글
 - CRUD 기능
   

### 1-3. 개발 환경

#### Back-end
 - Java 21
 - SpringBoot 3.2.4
 - JPA(Spring Data JPA)
 - Spring Security

##### Build Tool
 - Gradle 8.7

##### DataBase
 - MySQL 8.0.36

#### Front-end
 - html/css
 - JavaScript
 - Thymeleaf
 - Bootstrap 5.3.2


### 1-4. 실행 화면
  
  <details>
    <summary>게시글</summary>
    
   **1. 게시글 전체 목록**

   로그인 X 화면  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/5547af49-7724-4aeb-979f-7a6ad2590bdd)  

   로그인 O 화면  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/0871872a-720b-445f-bfe3-2055b252bd2e)  



   **1-1. 게시글 전체 목록 정렬**

   '조회수' 기준으로 내림차순 정렬  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/18250746-ccbb-4911-bcf1-f39d151f0f83)  

   ※ 로그인을 하지 않아도 게시글 정렬이 가능하다.  



   **2. 게시글 등록 화면**

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/67099714-c576-4029-8b68-552aa2a8ef5e)  

   로그인한 사용자만 게시글 작성이 가능하며, 작성 후 '게시' 버튼을 누르면 메인 페이지로 리다이렉트 된다.  

   

   **3. 게시글 상세 정보**

   로그인 X  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/92b77621-66c2-4a46-83e7-e13552424ae3)  

   ※ 로그인 하지 않은 경우, 게시글 상세 정보에 접근 가능하지만, '게시글 설정' 옵션에 접근할 수 없다.  


   작성자 계정이 아닌 다른 계정으로 로그인 O  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/bef81a23-7918-4817-a5b6-b0dc5515f885)  

   ※ 작성자 계정이 아닌 다른 계정으로 로그인한 경우, '게시글 설정' 옵션에 접근 가능하지만, '게시글 작성' 기능만 접근 가능하다.  


   작성자 계정으로 로그인 O  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/15f21e93-5271-447d-b708-f7aa6e0feff0)  

   ※ 작성자 계정으로 로그인 한 경우, '게시글 수정' 과 '게시글 삭제' 를 할 수 있다.  



   **4. 게시글 수정 화면**

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/2ecc1243-4c45-4f2f-9b42-4af5f6e7e914)  

   게시글 수정 후, '게시' 버튼을 눌러 수정을 마무리한다.    
   '게시' 버튼을 누르면 게시글 목록으로 이동한다.  

   [수정된 게시글 화면]  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/c828dff6-270e-4b36-a597-85969a196c0c)  
   
   

   **5. 게시글 삭제 화면**

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/816ef173-4759-4456-b8b5-1ad39da2f7bb)  

   '게시글 삭제' 버튼을 눌러 삭제를 진행한다.  

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/08081d6f-d02c-4c06-9a42-d1c9d963d53d)  

   '게시글 번호'를 포함한 삭제 완료 안내 메시지가 등장한다.

   [게시글 목록]  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/440f7a24-f7f1-4178-b6da-94319eda0f34)  

   게시글이 삭제 된 것을 볼 수 있다.


   **6. 게시글 검색 화면**

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/6b0fdefb-da6a-4dc7-9220-14c441fbb801)  



  **6-1. 게시글 검색 후 페이징 화면**

  ['by' 키워드로 검색한 화면]  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/bbd81c39-1be6-45da-a29c-a9431b2a07ca)  

  ['by' 키워드로 검색 내용 中 사용자 기준 4페이지]  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/c56913a8-1e71-4927-a828-27cf637fc195)  



  **6-2. 게시글 검색 후 페이징 + 정렬**

  ['by' 키워드로 검색 내용 && 조회수 기준 내림차순 정렬]  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/79fef79f-8ff0-4c39-99ac-2677d557079b)  


  ['by' 키워드로 검색 내용 && 조회수 기준 내림차순 정렬 中 사용자 기준 1페이지]  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/d346d15f-fe23-401e-a321-e79a19fa6537)   
    
  </details>


  <details>
    <summary>회원</summary>

   **1. 회원가입 화면**

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/fe32e1bc-cfeb-4c2b-9bc2-7ac7a2af3fea)  


   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/f7f03b03-c0d4-4526-a495-2cdcb6e0ff46)  

   ※ 회원가입 양식에 대한 경고 메시지 표시  



   **2. 로그인 화면**

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/70aeecdb-783d-4469-b23a-b1b814896db5)  

   ※ 로그인 실패에 대한 경고 메시지 표시  



   **3. 회원정보 수정 화면**

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/49fd5a13-5ff2-4ee5-96b1-8fef64824af3)  
   로그인 된 username 을 입력하여, 회원정보 화면으로 이동  

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/1ffe8588-1a0d-4b49-8bb8-15a53608835e)  

  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/db057e23-35ee-4f4b-afcb-333b7dcc2956)  
   변경하려는 닉네임에 대한 중복 체크    
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/8e879869-e2e9-4503-8ff2-80213486f736)  
   변경하려는 비밀번호에 대한 유효성 체크    



   **4. 회원 탈퇴**
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/86e4f872-27d0-4d94-a470-5e5ad86565dd)  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/109b6351-9b3c-4f57-a1eb-26bc5373da76)  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/e0a6d5a1-82f8-41b2-bbde-ae1eeaeb9f09)  
   회원 탈퇴 처리 후, 메인 페이지로 리다이렉션, 로그아웃 처리가 된다.    
   또한, 탈퇴한 회원이 작성한 게시글 / 댓글의 작성자는 'unknown' 으로 표기된다.  

    
  </details>


  <details>
    <summary>댓글</summary>

  **1. 댓글 작성 화면**
  
  로그인 X - 댓글 작성 화면  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/9cf7227a-1bc9-47d5-b173-bdebc02a7d64)  

  로그인 O - 댓글 작성 화면  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/97112208-bde3-474e-8283-f1195d8ceea1)  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/d771292d-46c4-461b-8c18-a5f929166c88)  


  **2. 댓글 수정**

  로그인 X  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/3f198fc2-6e9e-46f5-a6e1-898147562efe)  


  작성자 != 로그인 회원 - 로그인 O  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/a0ee73f7-912f-4561-acfe-ec125d0eb977)  

  작성자 == 로그인 회원 - 로그인 O  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/7e5fc904-1300-46c1-b3c0-ab732fb893b3)  
  작성자 본인이 로그인 한 상태에서만 댓글 수정 / 삭제 에 접근 가능하다.  

  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/4f4107bf-e01b-480f-832e-7149459ad179)  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/c2cf7c26-9a62-4183-9928-785486321a8a)  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/b08c429d-fa29-40c6-8a82-8647bd790f21)  

  댓글이 수정된 모습과 '작성일자' 뒤에 '(수정됨)' 표식이 생긴 것을 볼 수 있다.  



  **3. 댓글 삭제**

  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/c2c1e45a-1990-4a82-98d7-e4414d202543)  
  '댓글 2번!' 을 삭제 한다.    
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/2ddb34c3-fd21-467b-b7a1-a452fcbb9b61)  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/2dbb7acc-c455-48f4-bbd5-30a108e4941f)  
  '댓글 2번!' 이 삭제 된 모습을 볼 수 있다.

    
  </details>




## 2. 프로젝트 구조

### 2-1. 패키지 구조

<details>

<summary>패키지 구조 보기</summary>

```
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂project
 ┃ ┃ ┃ ┗ 📂boardserviceV2
 ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomAuthenticationFailureHandler.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomUserDetailsService.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜WebSecurityConfig.java
 ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentApiController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜HomeController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜InitMember.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberApiController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜PostApiController.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜PostController.java
 ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentRequestDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentResponseDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CreateMemberDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CreatePostDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜LoginDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberResponseDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜PostInfoDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜UpdateMemberDto.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜UpdatePostDto.java
 ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BaseEntity.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Comment.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Member.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Post.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜UserRole.java
 ┃ ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┃ ┃ ┣ 📜DataAlreadyExistsException.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜DataNotFoundException.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜GlobalExceptionHandler.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜UnauthorizedAccessException.java
 ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentRepository.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberRepository.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜PostRepository.java
 ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentService.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberService.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜PostService.java
 ┃ ┃ ┃ ┃ ┗ 📜BoardserviceV2Application.java
 ┃ ┗ 📂resources
 ┃ ┃ ┣ 📂static
 ┃ ┃ ┃ ┣ 📜main.js
 ┃ ┃ ┃ ┗ 📜styles.css
 ┃ ┃ ┣ 📂templates
 ┃ ┃ ┃ ┣ 📂fragments
 ┃ ┃ ┃ ┃ ┣ 📜bodyFooter.html
 ┃ ┃ ┃ ┃ ┣ 📜bodyHeader.html
 ┃ ┃ ┃ ┃ ┣ 📜footer.html
 ┃ ┃ ┃ ┃ ┗ 📜header.html
 ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┣ 📜createMember.html
 ┃ ┃ ┃ ┃ ┣ 📜login.html
 ┃ ┃ ┃ ┃ ┣ 📜memberInfo.html
 ┃ ┃ ┃ ┃ ┗ 📜updateMemberInfo.html
 ┃ ┃ ┃ ┣ 📂post
 ┃ ┃ ┃ ┃ ┣ 📜createPost.html
 ┃ ┃ ┃ ┃ ┣ 📜postInfo.html
 ┃ ┃ ┃ ┃ ┗ 📜updatePost.html
 ┃ ┃ ┃ ┗ 📜index.html
 ┃ ┃ ┗ 📜application.yml
 ┗ 📂test
 ┃ ┗ 📂java
 ┃ ┃ ┗ 📂project
 ┃ ┃ ┃ ┗ 📂boardserviceV2
 ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┗ 📜MemberApiControllerTest.java
 ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberRepositoryTest.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜PostRepositoryTest.java
 ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┗ 📜MemberServiceTest.java
 ┃ ┃ ┃ ┃ ┗ 📜BoardserviceV2ApplicationTests.java
```


</details>



### 2-2. DB 설계

![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/34bac54a-31d9-458a-83e3-33ca74f29413)

**MEMBER TABLE**   
![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/688dd71e-b194-41d7-8366-5634e666f748)  

**POST TABLE**  
![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/e56c4f17-526d-4c94-92b3-14cb63b0aee3)  

**COMMENT TABLE**  
![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/422d6e53-7c6c-4d22-bc08-fd9febfa44a6)  



### 2-3. API 설계

**Post 관련 API**  
![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/04184267-86fe-41fd-af99-a6772c85633a)  
  
**Member 관련 API**  
![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/22b3ae7b-34c0-4746-86cc-bdf8feadf447)  
  
**Comment 관련 API**  
![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/bc8aa6de-89c6-4e65-97a5-374b5d8df839)  
  



## 개발 내용

- <a href="https://notorious.tistory.com/340" target="_blank">게시글 페이징 처리 구현</a>
- <a href="https://notorious.tistory.com/341" target="_blank">게시글 키워드 검색 + 정렬 + 페이징 기능 구현</a>
- <a href="https://notorious.tistory.com/342" target="_blank">회원 탈퇴시, 게시글 / 댓글 처리</a>



## 마무리

### 1. 프로젝트 보완사항

아이템 선정시 계획한 프로젝트의 모습은 게시글/댓글 CRUD 기능을 포함한 기본적인 게시판 서비스 였습니다.
프로젝트 개발을 진행됨에 따라 기능을 추가하게 되었는데, 이 과정에서 원활하게 진행되지 못한 것에 아쉬움이 있습니다.

백엔드 부분에서 조금더 세밀하게 기능을 구현하지 못해, VIEW 단에서 본질인 데이터 렌더링에만 집중하지 못한 것이 아쉽습니다.
템플릿 엔진으로 Thyemleaf 를 채택하였습니다.
주요한 선택 요인은 Thyemleaf 가 단순 데이터 랜더링 기능 뿐만 아니라 풍부한 기능을 지니고 있기 때문입니다.
첫 프로젝트이기 때문에, 백엔드 부분에서 완전한 설계에 무리가 있을것 같다고 판단하여, Thymeleaf 로써 이를 보조하기 위함 이었습니다.
현재 로그인 여부 등을 판단하는 등 다양한 기능을 효과적으로 활용해 프로젝트를 끝매듭지을 수 있었던거 같습니다.
그러나, 템플릿 엔진의 본질인 '데이터 렌더링' 에 집중하지 못한 것이 굉장히 아쉬우며, SRP(단일 책임 원칙) 을 충족하지 못한 것 같아 큰 아쉬움이 듭니다.
  
다음 프로젝트 부터는 Logic-less 하여, 데이터 렌더링에만 집중할 수 있는 템플릿을 선택하거나, Thymeleaf를 사용하더라도 역할 구분을 확실히 하여야 하겠다고 생각합니다.

RequestDTO / ResponseDTO 를 이용해 명료하고, 단순하게 요청/응답에 대응해야 한다 라는 것을 느꼈습니다.
프로젝트 기능을 구현하는 동안, 다양한 요청/응답이 존재하는데, 모든 케이스에 대해 별도의 DTO를 제작하는 것에 큰 무리가 있다고 판단하게 되었습니다.
Entity를 그대로 응답으로 반환하는 것 만큼 큰 문제가 없기 때문에, DTO 를 이용해야 하지만, DTO 를 조금더 효율적으로 이용해야 하는 필요성을 느끼게 되었습니다.

다음 프로젝트는 RequestDTO / ResponseDTO 를 각 요청/응답 형식에 맞는 생성자를 이용하여 구현합니다.
Login / Sign-In 등 유효성 체크가 필요한 것에 한하여 별도의 DTO 를 구현합니다.



### 2. 프로젝트 과정에서 발생한 문제
- <a href="https://notorious.tistory.com/339" target="_blank">Spring Security 가 비로그인 상태에서 static rescoure 접근을 제한</a>


### 3. 후기

Spring 에 대한 독학 후 시작부터 끝까지 온전히 책임지며 진행한 첫 프로젝트로서 그 의의가 있습니다.
프로젝트를 진행하면서 배운점이 확실히 많았으며, 배운점 기록에 대한 중요성도 깨닫았습니다.

이전 까지 제대로된 프로젝트 개발 경험이 없었기에, DTO 설계, 서비스 기능 구상 등에서 많은 어려움이 있었습니다.
개발을 진행해 가며 앞으로 프로젝트 개발에 대한 노하우를 축적할 수 있었기에 다음 프로젝트 진행이 원활할 것으로 예상됩니다.

프로젝트의 모든 부분에서 이 코드를 왜 사용했는가에 대한 이유와 당위성을 나 스스로에게 납득 시켰습니다.
즉, 의미없는 코드를 누더기 처럼 사용하지 않기 위해 노력했습니다.

다른 사람들의 프로젝트를 참고하여, 이 사람은 왜 이 부분에서 이런 방식을 사용했는가에 대해 계속하여 고민하고 효율적인 방식이라고 생각이 되면, 적용하기도 했습니다.

프로젝트를 하나 완성하게 되면서, 어느 부분에서 기술적으로 부족한지 보안점을 찾게 되었습니다.

다음 프로젝트를 노하우를 통해 조금더 원활하게 개발 할 수 있다 라는 자신감이 들었습니다.

하지만 혼자 개발을 진행하다 보니, 내가 적용하는 방식 보다 더 나은 방식이 존재하는지, 나와는 다른 방식으로 구현한 다른 사람의 코드를 보면서 이 방식이 나 보다 더 나은 방식인지에 대한 판단에 어려움이 있었습니다.
앞으로 더 많은 프로젝트를 진행하며, 나의 방식/코드에 대한 확신을 얻는 과정이 필요하다고 생각이 들었습니다.
