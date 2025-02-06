# CSV

13조 영화관 예매 프로젝트

## 프로젝트 목표

- Redis Lock을 이용한 동시성 이슈 제어
- Cache를 이용한 성능 개선

## 팀원 소개

|                   팀장                    |                팀원                 |                   팀원                   |                 팀원                  |
|:---------------------------------------:|:---------------------------------:|:--------------------------------------:|:-----------------------------------:|
| [홍승근](https://github.com/hongseungkeun) | [김호진](https://github.com/Hojin02) | [박재성](https://github.com/Lustella-123) | [임수민](https://github.com/sumin9926) | 

## 설계 및 문서

### 주요 기능

- 데이터 자동 삽입
- 예매 (동시성 제어)
    - 분산 락과 비관적 락을 활용한 예매 처리
- 검색 (Cache 사용)
    - 인기 검색어: RedisTemplate을 이용한 빠른 검색
    - 인기 영화 순위
        - v1: 데이터베이스 직접 조회
        - v2: RedisTemplate을 이용한 조회
        - v3: RedisTemplate과 @Cacheable을 이용한 캐싱 처리

### 와이어 프레임

- ![13.png](src%2Fmain%2Fresources%2Fimage%2F13.png)

### API 명세서

- [노션](https://www.notion.so/teamsparta/ThirTeam-13-50005c7a1bed4a40b940e79c05861c38)

### ERD

``` mermaid
erDiagram
    USER ||--o{ BOOKING : makes
    USER {
        Long id PK "고유식별자"
        string email "이메일"
        string password "비밀번호"
        string nickname "닉네임"
        string userRole "회원 권한(USE/ADMIN)"
    }

    MOVIE ||--o{ SCREENING : has
    MOVIE {
        Long id PK "고유식별자"
        string title "영화 제목"
        int runtime "영화 런타임"
        date release_date "개봉일"
        string description "영화 설명"
    }

    THEATER ||--o{ SCREENING : hosts
    THEATER ||--o{ SEAT : has
    THEATER {
        Long id PK "고유식별자"
        string name "상영관 이름"
    }

    SCREENING ||--o{ BOOKING : includes
    SCREENING {
        Long id PK "고유식별자"
        Long movie_id FK "영화 외래키"
        Long theater_id FK "상영관 외래키"
        datetime screening_datetime "상영일시"
        int remainingSeats "남은 좌석 수"
    }

    BOOKING ||--o{ BOOKED_SEAT : books
    BOOKING {
        Long id PK "고유식별자"
        Long user_id FK "사용자 외래키"
        Long screening_id FK "상영 정보 외래키"
        datetime reserved_at "예매 시간"
        string status "예매 상태(완료/취소)"
    }

   SEAT||--o{ BOOKED_SEAT : reserved
    SEAT {
        Long id PK "고유식별자"
        Long theater_id FK "상영관 외래키"
        string seat_number "좌석 번호"
        int seat_price "좌석 가격"
    }

    BOOKED_SEAT {
        Long booking_id FK "예매 외래키"
        Long seat_id FK "좌석 외래키"
    }
```