# MUSINSA - JAVA BACK-END CODI PROJECT

## 1. 구현 범위에 대한 설명
API 목록

1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
4. 브랜드 및 상품을 추가 / 업데이트 / 삭제하는 API

### * 고려할점
● API 실패 시, 실패값과 실패 사유를 전달해야 합니다.

### * 가산점
● Unit test 및 Integration test 작성
● Frontend 페이지 구현

## 2. 개발 환경
- IDE: IntelliJ IDEA
- Language: Java 17
- Build Tool: Gradle
- Framework: Spring Boot 3.3.1
- Database: H2
- Test: Junit5

## 3. 구현 범위
- Back-end : API
- Front-end : thymeleaf
- test : Junit5
- API document : Swagger

### * 주의 사항
카테고리 항목에 대해 영어로 아래와 같이 매치 하였습니다.
- 브랜드 -> brand
- 카테고리 -> category
- 상의 -> top
- 아우터 -> outer
- 바지 -> pants
- 스니커즈 -> shoes
- 가방 -> bag
- 모자 -> hat
- 양말 -> socks
- 액세서리 -> accessory

## 기타 접속정보
1. H2 콘솔 접속
    - http://localhost:8080/h2-console
    - 애플리케이션이 시작될 때 H2 인메모리 데이터베이스가 data.sql 파일을 실행하여 자동으로 초기화합니다.
2. API 문서 codi
    - http://localhost:8080/musinsa/codi/swagger-ui/index.html

## 추가 구현 내용
1. Unit test 및 Integration test 작성
    - 비즈니스 영역 테스트 커버리지 100% 수행