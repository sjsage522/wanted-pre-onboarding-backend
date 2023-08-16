### 애플리케이션 실행

    git clone "https://github.com/sjsage522/wanted-pre-onboarding-backend.git"
    
    docker-compose -f docker-compose.production.yml up -d --build



### 테이블 구조 (스키마)

    CREATE TABLE IF NOT EXISTS users (
        id          bigint not null auto_increment,
        email       varchar(255) not null,
        password    varchar(255) not null,
        salt        varchar(255) not null,
        created_at  datetime(6),
        modified_at datetime(6),
    
        PRIMARY KEY (id),
        INDEX (email)
    ) Engine=InnoDB;
    
    CREATE TABLE IF NOT EXISTS posts (
        id          bigint not null auto_increment,
        user_id     bigint not null,
        title       varchar(255) not null,
        content     varchar(255) not null,
        created_at  datetime(6),
        modified_at datetime(6),
    
        PRIMARY KEY (id),
        CONSTRAINT fk_posts_users FOREIGN KEY (user_id) REFERENCES users (id)
    ) Engine=InnoDB;



### 데모



### 구현
게시글 수정/삭제 시 인증로직 통일 (aop, @PostAuthCheck)

에러 핸들링 통합(aop), 비즈니스용 에러코드 정의

AuthFilter 클래스에서 리소스 접근에 대한 인증로직 진행

레이어드 아키텍처 패턴


### API 명세

- 사용자 회원가입
  - url
    - POST https://api.wanted-pre-onboarding-backend.xyz/api/v1/users
  - request example
    -     curl --location 'https://api.wanted-pre-onboarding-backend.xyz/api/v1/users' \
          --data-raw '{
              "email": "test1@gmail.com",
              "password": "12345678"
          }'
  - response example
    - success
      -     {	
                "data": 1,
                "error": null
            }
    - fail
      -     {
                "data": null,
                "error": {
                    "code": 30002,
                    "message": "Fail to sign up. Already exists email."
                }
            }
- 사용자 로그인
  - url
    - POST https://api.wanted-pre-onboarding-backend.xyz/api/v1/users/auth
  - request example
    -     curl --location 'https://api.wanted-pre-onboarding-backend.xyz/api/v1/users/auth' \
          --data-raw '{
              "email": "test1@gmail.com",
              "password": "12345678"
          }'
  - response example
    - success
      -     {
                "data": {
                    "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZW1haWwiOiJ0ZXN0MUBnbWFpbC5jb20iLCJpYXQiOjE2OTE3MTIyMTMsImV4cCI6MTY5MTcxNDAxM30.zMjwMzphvIDL64hfcPJbYQlcgdiQ90Qc4FQuaeKPAWs"
                },
                "error": null
            }
    - fail
      -     {
                "data": null,
                "error": {
                    "code": 40000,
                    "message": "Mismatch user password."
                }
            }

- 게시글 생성
  - url
    - POST https://api.wanted-pre-onboarding-backend.xyz/api/v1/posts
  - request example
    -     curl --location 'https://api.wanted-pre-onboarding-backend.xyz/api/v1/posts' \
          --data '{
              "title": "title 1",
              "content": "content 1"
          }'
  - response example
    - success
      -     {
                "data": 1,
                "error": null
            }

- 게시글 단건 조회
  - url
    - GET https://api.wanted-pre-onboarding-backend.xyz/api/v1/posts/1
  - request example
    -     curl --location 'https://api.wanted-pre-onboarding-backend.xyz/api/v1/posts/1'
  - response example
    - success
      -     {
                "data": {
                    "title": "title 1",
                    "content": "content 1",
                    "post_id": 1,
                    "writer": "test1@gmail.com",
                    "created_at": "2023-08-11T09:05:41.458832",
                    "modified_at": "2023-08-11T09:05:41.458832"
                },
                "error": null
            }
    - fail
      -     {
                "data": null,
                "error": {
                    "code": 30001,
                    "message": "Not found post."
                }
            }

- 게시글 리스트 조회
  - url
    - GET https://api.wanted-pre-onboarding-backend.xyz/api/v1/posts
  - request example
    -     curl --location 'https://api.wanted-pre-onboarding-backend.xyz/api/v1/posts'
  - response example
    - success
      -     {
                "data": {
                    "value": [
                        {
                            "title": "title 1",
                            "content": "content 1",
                            "post_id": 1,
                            "writer": "test1@gmail.com",
                            "created_at": "2023-08-11T09:05:41.458832",
                            "modified_at": "2023-08-11T09:05:41.458832"
                        }
                    ],
                    "count": 1,
                    "page": 0,
                    "size": 10,
                    "total": 1
                },
                "error": null
            }

- 게시글 수정
  - url
    - PATCH https://api.wanted-pre-onboarding-backend.xyz/api/v1/posts/1
  - request example
    -     curl --location --request PATCH 'https://api.wanted-pre-onboarding-backend.xyz/api/v1/posts/1' \
          --data '{
              "title": "title update",
              "content": "content update"
          }'
  - response example
    - success
      -     {
                "data": true,
                "error": null
            }
    - fail
      -     {
                "data": null,
                "error": {
                    "code": 30003,
                    "message": "Denied access this post. You do not have permission."
                }
            }

- 게시글 삭제
  - url
    - DELETE https://api.wanted-pre-onboarding-backend.xyz/api/v1/posts/1
  - request example
    -     curl --location --request DELETE 'https://api.wanted-pre-onboarding-backend.xyz/api/v1/posts/1'
  - response example
    - success
      -     {
                "data": true,
                "error": null
            }
    - fail
      -     {
                "data": null,
                "error": {
                    "code": 30003,
                    "message": "Denied access this post. You do not have permission."
                }
            }


