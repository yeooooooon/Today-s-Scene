# Today's Scene - 백엔드 REST API 설계 및 프로젝트 구조

본 문서는 Today's Scene 프로젝트의 백엔드 시스템에 대한 전체적인 아키텍처, 프로젝트 구조, 그리고 REST API 명세서를 정의합니다.

---

## 1. 프로젝트 아키텍처 개요 (REST API 기반)

본 프로젝트는 프론트엔드(UI/UX)와 백엔드(데이터 처리)를 분리한 **RESTful API 아키텍처**를 채택하였습니다. 

*   **Frontend (클라이언트):** HTML, Vanilla CSS, JS를 사용하여 시각적인 화면을 구성하고, 백엔드 및 외부 API(TMDB)로 데이터를 비동기적(Fetch)으로 요청합니다.
*   **Backend (서버):** Spring Boot 기반으로 구동되며, 클라이언트의 요청(Request)에 대해 페이지(HTML)를 새로고침하여 반환하지 않고 **JSON 형태의 데이터(Data)**만 가볍게 응답(Response)합니다.

---

## 2. 백엔드 프로젝트 구조 (Directory Structure)

Spring Boot의 계층형 아키텍처(Layered Architecture) 패턴을 적용하여, 역할과 책임을 명확히 분리하였습니다.

```text
src/main/java/com/todaysscene/backend/
 ├── 📁 config           # 설정 클래스 (CORS 설정, DB 초기 데이터 세팅 등)
 │    └── DataInitializer.java (앱 실행 시 영화 데이터를 DB에 자동 삽입)
 │
 ├── 📁 controller       # API 요청(HTTP)을 받고 응답(JSON)을 반환하는 계층
 │    ├── MoodController.java
 │    ├── MovieController.java
 │    └── ReviewController.java
 │
 ├── 📁 domain           # 데이터베이스 테이블과 매핑되는 핵심 엔티티(Entity) 클래스
 │    ├── Mood.java
 │    ├── Movie.java
 │    ├── MoodReason.java
 │    └── Review.java
 │
 └── 📁 repository       # DB에 직접 접근하여 CRUD(생성,조회,수정,삭제)를 수행하는 계층
      ├── MoodRepository.java
      ├── MovieRepository.java
      └── ReviewRepository.java
```

*   **Controller:** 프론트엔드의 요청 창구. 어떤 API 주소로 요청이 왔는지 확인하고 적절한 데이터를 꺼내어 JSON으로 변환해 돌려줍니다.
*   **Domain (Entity):** 실제 데이터베이스의 표(Table) 모양을 자바 객체로 정의한 설계도입니다.
*   **Repository:** 데이터베이스 창고 관리자입니다. DB에 데이터를 넣거나 빼오는 쿼리 작업을 담당합니다.

---

## 3. 데이터베이스 설계 구조 (Database ERD & Entities)

JPA(Hibernate)를 통해 관리되는 핵심 엔티티(테이블) 간의 관계(Relation)입니다.

### 🗄️ 핵심 테이블 (Entities)

1. **`moods` 테이블 (`Mood.java`)**
   * **역할:** 감정 카테고리를 저장 (예: melancholy, excited 등)
   * **필드:** `mood_id` (PK, String)
   * **관계:** 
     - `movies` (다대다, N:M 연관관계 -> `mood_movies` 중간 테이블 자동 생성)
     - `reasons` (일대다, 1:N 연관관계)

2. **`movies` 테이블 (`Movie.java`)**
   * **역할:** 추천 영화의 기본 정보 저장 (TMDB 기반)
   * **필드:** `movie_id` (PK, Integer), `title` (String), `poster_path` (String), `overview` (Text)
   * **관계:** 
     - `moods` (다대다, N:M 연관관계)
     - `reviews` (일대다, 1:N 연관관계)

3. **`mood_reasons` 테이블 (`MoodReason.java`)**
   * **역할:** 특정 무드에 대한 감성적인 추천 이유 텍스트 풀 저장
   * **필드:** `reason_id` (PK, Auto Increment), `reason_text` (Text), `mood_id` (FK)
   * **관계:** `moods` 테이블과 다대일(N:1) 연관관계

4. **`reviews` 테이블 (`Review.java`)**
   * **역할:** 사용자가 영화에 남긴 리뷰 및 평점 데이터 저장
   * **필드:** `review_id` (PK, Auto Increment), `author_name` (String), `content` (Text), `rating` (Integer), `created_at` (Datetime), `movie_id` (FK)
   * **관계:** `movies` 테이블과 다대일(N:1) 연관관계

---

## 4. REST API 명세서 (API Endpoints Design)

프론트엔드와 백엔드가 데이터를 주고받기 위한 약속(URL 및 응답 형식)입니다.

| 요청 URL | HTTP Method | 담당 Controller | 파라미터 | 반환 데이터 형식(JSON) | 기능 설명 |
| :--- | :---: | :--- | :--- | :--- | :--- |
| `/api/moods` | GET | `MoodController` | 없음 | `List<Mood>` | 전체 무드(감정) 카테고리 목록 조회 |
| `/api/moods/{id}` | GET | `MoodController` | `@PathVariable id` | `Mood` | 특정 무드에 연결된 영화 리스트 및 추천 이유 통합 조회 |
| `/api/moods/{id}/movies` | GET | `MoodController` | `@PathVariable id` | `List<Movie>` | 특정 무드에 속한 추천 영화 목록만 별도 조회 |
| `/api/movies` | GET | `MovieController` | 없음 | `List<Movie>` | DB에 저장된 전체 추천 영화 풀 조회 |
| `/api/movies/{id}` | GET | `MovieController` | `@PathVariable id` | `Movie` | 특정 영화의 기본 정보 조회 |
| `/api/movies/{movieId}/reviews`| GET | `ReviewController` | `@PathVariable movieId` | `List<Review>` | 특정 영화에 작성된 사용자 리뷰 전체 목록 조회 |
| `/api/movies/{movieId}/reviews`| POST | `ReviewController` | `@RequestBody Review` | `Review` | 특정 영화에 대한 새로운 리뷰 및 평점 등록 |

---

## 5. 데이터 플로우 (Data Flow) - 어떻게 작동하는가?

사용자가 화면에서 버튼을 클릭했을 때, 데이터가 어떻게 흘러가는지 보여주는 흐름도입니다.

1. **[Frontend] 이벤트 발생:** 사용자가 메인 화면에서 '우울한(Melancholy)' 무드 버튼 클릭
2. **[Frontend] API 요청:** JS의 `fetch()` 함수가 백엔드 API 호출 `GET http://localhost:8080/api/moods/melancholy`
3. **[Backend] Controller 수신:** `MoodController`가 요청을 받아 `MoodRepository`에게 데이터 조회 지시
4. **[Backend] DB 조회:** H2 DB에서 `moods` 테이블과 매핑된 `movies`, `mood_reasons` 데이터를 JOIN하여 추출
5. **[Backend] JSON 응답:** 추출된 데이터를 JSON 포맷으로 직렬화하여 프론트엔드로 전송
6. **[Frontend] 랜덤 픽 & 화면 렌더링:** 프론트엔드는 받은 15개의 영화 중 3개를 무작위(`pickRandom`)로 뽑은 뒤, **TMDB API**를 추가 호출하여 포스터 이미지를 가져와 화면에 그림
