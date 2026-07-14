# Today's Scene REST API 문서

Base URL: `http://localhost:8080`

CORS: 전체 origin 허용 (`@CrossOrigin(origins = "*")`)

문서 형식은 쿠팡 Open API 문서(출고지 생성)의 구성 방식(Path / Request Parameters / Request Example / Response Message / Response Example / Error Spec)을 참고하여 작성했습니다.

> 이 버전은 `POST /api/movies` (영화 등록) 엔드포인트가 코드에서 삭제된 최신 프로젝트 기준으로 작성되었습니다. 총 7개 엔드포인트.

---

## 1. 무드 전체 목록 조회

무드(Mood) 목록 전체를 조회합니다. 각 무드는 연결된 영화 목록과 추천 사유(reason) 목록을 함께 반환합니다.

### Path

`GET`

`/api/moods`

### Example Endpoint

`http://localhost:8080/api/moods`

### Request Parameters

없음

### Response Message

| Name | Type | Description |
|---|---|---|
| moodId | String | 무드 ID (예: melancholy, excited, romantic 등) |
| movies | Array | 해당 무드에 연결된 영화 목록 (MovieDto) |
| reasons | Array | 해당 무드의 추천 사유 목록 (MoodReasonDto) |
| reasons[].reasonId | Integer | 추천 사유 ID |
| reasons[].reasonText | String | 추천 사유 문구 |

### Response Example

```json
[
  {
    "moodId": "melancholy",
    "movies": [
      {
        "movieId": 153,
        "title": "영화 제목",
        "posterPath": "/poster.jpg",
        "overview": "줄거리 요약"
      }
    ],
    "reasons": [
      { "reasonId": 1, "reasonText": "혼자 있는 밤, 이 영화가 조용히 곁에 있어줄 거예요" }
    ]
  }
]
```

### Error Spec

현재 컨트롤러 레벨에서 별도의 에러 응답 스펙은 정의되어 있지 않습니다. (항상 200 OK, 빈 배열 가능)

### URL API Name

`GET_ALL_MOODS`

---

## 2. 무드 단건 조회

무드 ID로 특정 무드 정보를 조회합니다.

### Path

`GET`

`/api/moods/{id}`

### Example Endpoint

`http://localhost:8080/api/moods/melancholy`

### Request Parameters

#### Path Segment Parameter

| Name | Required | Type | Description |
|---|---|---|---|
| id | O | String | 조회할 무드 ID (예: melancholy, excited, romantic, dark, cozy, wonder, funny, thoughtful) |

### Response Message

| Name | Type | Description |
|---|---|---|
| moodId | String | 무드 ID |
| movies | Array | 연결된 영화 목록 |
| reasons | Array | 추천 사유 목록 |

### Response Example

```json
{
  "moodId": "melancholy",
  "movies": [ { "movieId": 153, "title": "영화 제목", "posterPath": "/poster.jpg", "overview": "줄거리" } ],
  "reasons": [ { "reasonId": 1, "reasonText": "혼자 있는 밤, 이 영화가 조용히 곁에 있어줄 거예요" } ]
}
```

### Error Spec

| HTTP 상태 코드 (오류 유형) | 오류 상황 | 해결 방법 |
|---|---|---|
| 404 (리소스 없음) | 존재하지 않는 moodId 요청 시 | 응답 바디 없이 404 반환. 올바른 moodId 값을 입력했는지 확인합니다. |

### URL API Name

`GET_MOOD_BY_ID`

---

## 3. 무드별 영화 목록 조회

특정 무드에 해당하는 영화 목록만 조회합니다.

### Path

`GET`

`/api/moods/{id}/movies`

### Example Endpoint

`http://localhost:8080/api/moods/romantic/movies`

### Request Parameters

#### Path Segment Parameter

| Name | Required | Type | Description |
|---|---|---|---|
| id | O | String | 조회할 무드 ID |

### Response Message

| Name | Type | Description |
|---|---|---|
| (Array) | Array | MovieDto 배열 |
| movieId | Integer | 영화 ID (TMDB 기준) |
| title | String | 영화 제목 |
| posterPath | String | 포스터 이미지 경로 |
| overview | String | 줄거리 |

### Response Example

```json
[
  { "movieId": 122906, "title": "영화 제목", "posterPath": "/poster.jpg", "overview": "줄거리" }
]
```

### Error Spec

| HTTP 상태 코드 (오류 유형) | 오류 상황 | 해결 방법 |
|---|---|---|
| 404 (리소스 없음) | 존재하지 않는 moodId 요청 시 | 올바른 moodId 값을 입력했는지 확인합니다. |

### URL API Name

`GET_MOVIES_BY_MOOD`

---

## 4. 영화 전체 목록 조회

### Path

`GET`

`/api/movies`

### Example Endpoint

`http://localhost:8080/api/movies`

### Request Parameters

없음

### Response Message

| Name | Type | Description |
|---|---|---|
| movieId | Integer | 영화 ID |
| title | String | 영화 제목 |
| posterPath | String | 포스터 이미지 경로 |
| overview | String | 줄거리 |

### Response Example

```json
[
  { "movieId": 153, "title": "영화 제목", "posterPath": "/poster.jpg", "overview": "줄거리" }
]
```

### URL API Name

`GET_ALL_MOVIES`

---

## 5. 영화 단건 조회

### Path

`GET`

`/api/movies/{id}`

### Example Endpoint

`http://localhost:8080/api/movies/153`

### Request Parameters

#### Path Segment Parameter

| Name | Required | Type | Description |
|---|---|---|---|
| id | O | Integer | 조회할 영화 ID |

### Response Message

| Name | Type | Description |
|---|---|---|
| movieId | Integer | 영화 ID |
| title | String | 영화 제목 |
| posterPath | String | 포스터 이미지 경로 |
| overview | String | 줄거리 |

### Response Example

```json
{ "movieId": 153, "title": "영화 제목", "posterPath": "/poster.jpg", "overview": "줄거리" }
```

### Error Spec

| HTTP 상태 코드 (오류 유형) | 오류 상황 | 해결 방법 |
|---|---|---|
| 404 (리소스 없음) | 존재하지 않는 movieId 요청 시 | 올바른 movieId 값을 입력했는지 확인합니다. |

### URL API Name

`GET_MOVIE_BY_ID`

---

## 6. 영화별 리뷰 목록 조회

### Path

`GET`

`/api/movies/{movieId}/reviews`

### Example Endpoint

`http://localhost:8080/api/movies/153/reviews`

### Request Parameters

#### Path Segment Parameter

| Name | Required | Type | Description |
|---|---|---|---|
| movieId | O | Integer | 리뷰를 조회할 영화 ID |

### Response Message

| Name | Type | Description |
|---|---|---|
| reviewId | Long | 리뷰 ID |
| authorName | String | 작성자명, 최대 50자 |
| content | String | 리뷰 내용 |
| rating | Integer | 별점 (1~5) |
| createdAt | String (LocalDateTime) | 작성일시 |
| movieId | Integer | 영화 ID |

정렬 기준: `createdAt` 내림차순 (최신순)

### Response Example

```json
[
  {
    "reviewId": 1,
    "authorName": "홍길동",
    "content": "여운이 남는 영화였어요.",
    "rating": 5,
    "createdAt": "2026-07-10T21:30:00",
    "movieId": 153
  }
]
```

### Error Spec

해당 movieId의 리뷰가 없는 경우에도 빈 배열과 함께 200 OK를 반환합니다. (별도 404 처리 없음)

### URL API Name

`GET_REVIEWS_BY_MOVIE`

---

## 7. 리뷰 등록

특정 영화에 대한 리뷰를 등록합니다. 요청한 movieId의 영화가 아직 DB에 없을 경우, 최소 정보(movieId)만으로 영화 레코드를 새로 생성한 뒤 리뷰를 저장합니다. (사용자가 직접 영화를 등록하는 별도 API는 없으며, 이 자동 생성 로직이 유일한 영화 저장 경로입니다.)

### Path

`POST`

`/api/movies/{movieId}/reviews`

### Example Endpoint

`http://localhost:8080/api/movies/153/reviews`

### Request Parameters

#### Path Segment Parameter

| Name | Required | Type | Description |
|---|---|---|---|
| movieId | O | Integer | 리뷰를 등록할 영화 ID |

#### Body Parameter

| Name | Required | Type | Description |
|---|---|---|---|
| authorName | | String | 작성자명, 최대 50자 |
| content | | String | 리뷰 내용 |
| rating | | Integer | 별점 (1~5 권장, 서버 단 별도 범위 검증 없음) |

### Request Example

```json
{
  "authorName": "홍길동",
  "content": "여운이 남는 영화였어요.",
  "rating": 5
}
```

### Response Message

| Name | Type | Description |
|---|---|---|
| reviewId | Long | 생성된 리뷰 ID |
| authorName | String | 작성자명 |
| content | String | 리뷰 내용 |
| rating | Integer | 별점 |
| createdAt | String (LocalDateTime) | 작성일시 (서버 현재 시각 자동 저장) |
| movieId | Integer | 영화 ID |

### Response Example

```json
{
  "reviewId": 10,
  "authorName": "홍길동",
  "content": "여운이 남는 영화였어요.",
  "rating": 5,
  "createdAt": "2026-07-13T15:20:11",
  "movieId": 153
}
```

### Error Spec

> 참고: 현재 구현에는 rating 범위(1~5) 검증, authorName/content 필수값 검증(@Valid) 등이 적용되어 있지 않아, 잘못된 값이 들어와도 그대로 저장됩니다. 향후 `spring-boot-starter-validation` 의존성을 활용해 `@NotBlank`, `@Min`/`@Max` 등의 검증 로직을 추가하는 것을 권장합니다.

### URL API Name

`ADD_REVIEW`

---

## 부록: 엔드포인트 요약

| Method | URL | 설명 |
|---|---|---|
| GET | `/api/moods` | 무드 전체 목록 조회 |
| GET | `/api/moods/{id}` | 무드 단건 조회 |
| GET | `/api/moods/{id}/movies` | 무드별 영화 목록 조회 |
| GET | `/api/movies` | 영화 전체 목록 조회 |
| GET | `/api/movies/{id}` | 영화 단건 조회 |
| GET | `/api/movies/{movieId}/reviews` | 영화별 리뷰 목록 조회 |
| POST | `/api/movies/{movieId}/reviews` | 리뷰 등록 |