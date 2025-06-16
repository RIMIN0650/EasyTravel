# 🌟 EasyTravel - AI 기반 개인 맞춤형 여행 추천 시스템

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)

**AI가 대신 짜주는 통합 여행 계획 서비스** ✈️

---

## 🎯 프로젝트 소개

EasyTravel은 **OpenAI GPT**를 활용하여 사용자 맞춤형 여행 계획을 자동으로 생성하는 혁신적인 시스템입니다. 
**할루시네이션 검증 기능**을 통해 실제 존재하는 장소만을 추천하여 **95% 이상의 신뢰성**을 보장합니다.

### 🔥 **핵심 차별화 포인트**
- 🤖 **AI 추천 + 실존성 검증**: GPT 추천 → Geocoding 검증 → 재추천 프로세스
- 🗺️ **실시간 지도 연동**: Google Maps API로 여행지 시각화
- 🏨 **통합 정보 서비스**: 숙박, 맛집, 날씨까지 원스톱 제공
- 💬 **여행 커뮤니티**: 후기 공유 및 지역 게시판

---

## ✨ 주요 기능

### 🎯 **AI 여행 추천**
- 사용자 설문 기반 개인화 추천
- **할루시네이션 방지**: 실제 장소만 추천 (검증률 95%+)
- 일정, 예산, 취향 모두 고려한 맞춤형 계획

### 🗺️ **통합 여행 정보**
- **숙박 검색**: Foursquare API 연동, 리뷰 및 별점 제공
- **맛집 추천**: 카카오맵 API 기반 지역별 맛집 검색
- **실시간 날씨**: 여행 기간 날씨 정보 제공

### 💬 **커뮤니티**
- **여행 후기**: 사진과 함께 생생한 여행 경험 공유
- **지역 게시판**: 현지인 추천 숨은 명소 정보
- **챗봇 지원**: 24시간 여행 상담 서비스

---

## 🏗️ 시스템 아키텍처

```
                    사용자
                      │
                    HTTP
                      ▼
┌─────────────────────────────────────────────────────┐
│                 Spring Boot                         │
│  ┌─────────────────────────────────────────────┐    │
│  │            MVC Architecture                 │    │
│  │                                             │    │
│  │  ┌─────────────────┐ ┌─────────────────┐   │    │
│  │  │   Controller    │ │  Thymeleaf      │   │    │
│  │  │                 │ │  Template       │   │    │
│  │  │ • Web Controller│ │  Engine         │   │    │
│  │  │ • REST API      │ │                 │   │    │
│  │  │ • Exception     │ │ • HTML/CSS/JS   │   │    │
│  │  │   Handler       │ │ • Static Files  │   │    │
│  │  └─────────────────┘ └─────────────────┘   │    │
│  │           │                    ▲           │    │
│  │           ▼                    │           │    │
│  │  ┌─────────────────┐          │           │    │
│  │  │  Service Layer  │          │           │    │
│  │  │                 │          │           │    │
│  │  │ • 비즈니스 로직   │          │           │    │
│  │  │ • AI 추천 서비스 │          │           │    │
│  │  │ • 외부 API 연동  │──────────────────────────    │
│  │  └─────────────────┘          │           │    │
│  │           │                    │           │    │
│  │           ▼                    │           │    │
│  │  ┌─────────────────┐          │           │    │
│  │  │ Repository Layer│          │           │    │
│  │  │                 │          │           │    │
│  │  │ • JPA/MyBatis   │          │           │    │
│  │  │ • Data Access   │          │           │    │
│  │  └─────────────────┘          │           │    │
│  └─────────────────────────────────────────────┘    │
│                      │                              │
│                      ▼                              │
│              ┌─────────────┐                        │
│              │   MySQL     │                        │
│              │ Database    │                        │
│              └─────────────┘                        │
└─────────────────────────────────────────────────────┘
                      ▲
                     AWS
                   
외부 API 연동:
• OpenAI GPT API  • Google Maps API  
• Kakao Map API   • Foursquare API
```

---

## 🔧 기술 스택

### **Backend**
- **Framework**: Spring Boot 3.x (MVC Architecture)
- **Language**: Java 17
- **Database**: MySQL 8.0 + JPA/Hibernate
- **Template Engine**: Thymeleaf (서버 사이드 렌더링)

### **Frontend**
- **Technology**: HTML5, CSS3, JavaScript (ES6+)
- **Framework**: Bootstrap 5 (반응형 디자인)
- **Static Files**: Spring Boot 내장 정적 파일 서버

### **External APIs**
- **AI**: OpenAI GPT-4 (여행 추천)
- **Maps**: Google Maps API, Kakao Map API
- **Places**: Foursquare API (숙박, 맛집 정보)
- **Geocoding**: Google Geocoding API (위치 검증)

### **Infrastructure**
- **Cloud**: AWS EC2 + RDS
- **CI/CD**: GitHub Actions
- **Monitoring**: AWS CloudWatch

---

## 🚀 빠른 시작

### 📋 **시스템 요구사항**
- **Java**: 17 이상
- **MySQL**: 8.0 이상
- **Memory**: 최소 4GB RAM
- **Storage**: 최소 10GB 여유 공간

### ⚡ **설치 및 실행**

#### 1️⃣ **프로젝트 클론**
```bash
git clone https://github.com/your-team/EasyTravel.git
cd EasyTravel
```

#### 2️⃣ **데이터베이스 설정**
```sql
-- MySQL 접속 후 실행
CREATE DATABASE easytravel CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'easytravel'@'localhost' IDENTIFIED BY 'password123';
GRANT ALL PRIVILEGES ON easytravel.* TO 'easytravel'@'localhost';
FLUSH PRIVILEGES;
```

#### 3️⃣ **환경 설정**
```bash
# backend/src/main/resources/application-local.yml 생성
cp backend/src/main/resources/application-example.yml backend/src/main/resources/application-local.yml
```

**application-local.yml 편집:**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/easytravel?useSSL=false&serverTimezone=Asia/Seoul
    username: easytravel
    password: password123
    
# API 키 설정 (필수)
openai:
  api:
    key: your-openai-api-key-here
    
google:
  maps:
    api-key: your-google-maps-api-key-here
    
kakao:
  api:
    key: your-kakao-api-key-here
```

#### 4️⃣ **애플리케이션 실행**
```bash
cd backend
chmod +x gradlew
./gradlew bootRun
```

#### 5️⃣ **접속 확인** ✅
- **웹 서비스**: http://localhost:8080
- **API 문서**: http://localhost:8080/swagger-ui.html
- **관리자**: http://localhost:8080/admin

---

## 📖 API 사용 예제

### 🔐 **사용자 인증**
```bash
# 회원가입
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "loginId": "testuser",
    "password": "password123",
    "userName": "홍길동",
    "email": "test@example.com",
    "age": 25
  }'

# 로그인
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "loginId": "testuser",
    "password": "password123"
  }'
```

### 🎯 **여행 추천**
```bash
# 여행지 추천 요청
curl -X POST http://localhost:8080/api/v1/recommendations/generate \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "region": "제주도",
    "duration": 3,
    "budget": 500000,
    "travelStyle": "휴양",
    "preferences": {
      "first": "바다",
      "second": "맛집",
      "third": "카페"
    }
  }'
```

### 📍 **응답 예시**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "region": "제주도",
    "itinerary": [
      {
        "day": 1,
        "theme": "해변과 맛집 탐방",
        "places": [
          {
            "name": "협재해수욕장",
            "coordinates": { "lat": 33.3939, "lng": 126.2395 },
            "description": "에메랄드빛 바다와 하얀 모래사장이 아름다운 해수욕장",
            "duration": 120,
            "category": "관광지"
          }
        ]
      }
    ],
    "verified": true,
    "confidence": 0.95
  }
}
```

---

## 🗃️ 데이터베이스 스키마

### 📊 **주요 테이블**
- **user**: 사용자 기본 정보
- **preference**: 사용자 여행 선호도
- **post**: 여행 후기 및 게시글
- **region**: 지역 정보
- **recommendation**: 추천 결과 및 좋아요

### 🔗 **주요 관계**
- `user` 1:1 `preference` (사용자별 선호도)
- `user` 1:N `post` (사용자별 여러 게시글)
- `post` 1:N `postimage` (게시글별 여러 이미지)
- `region` 1:N `post` (지역별 게시글)

---

## 🌟 핵심 기능 상세

### 🧠 **할루시네이션 검증 프로세스**

EasyTravel의 가장 혁신적인 기능입니다:

1. **1차 추천**: OpenAI GPT가 사용자 설문을 바탕으로 여행지 추천
2. **실존성 검증**: Geocoding API로 추천된 장소의 실제 존재 여부 확인
   ```
   예시: "북춘 한옥마을" → 좌표 (37.5814696, 126.9849519) ✅ 검증 성공
   ```
3. **재추천**: 검증된 실제 장소만을 활용하여 GPT가 구체적인 여행 계획 재생성
4. **지도 시각화**: Google Maps API로 검증된 여행지 시각화

**검증 성공률: 95% 이상** 🎯

### 📱 **반응형 웹 디자인**
- 모바일 우선 설계 (온라인 여행시장의 40%가 모바일)
- Bootstrap 5 기반 반응형 레이아웃
- PWA 지원으로 앱과 같은 사용자 경험

---

## 🤝 기여하기

### 📝 **개발 환경 설정**
1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### 🐛 **버그 리포트**
[Issues](https://github.com/your-team/EasyTravel/issues)에서 버그를 신고해주세요.

### 💡 **기능 제안**
새로운 기능 아이디어는 [Discussions](https://github.com/your-team/EasyTravel/discussions)에서 제안해주세요.

---

## 📚 문서

- 📖 **[전체 문서](https://github.com/your-team/EasyTravel/wiki)**
- 🚀 **[설치 가이드](https://github.com/your-team/EasyTravel/wiki/Installation-Guide)**
- 📊 **[API 문서](https://github.com/your-team/EasyTravel/wiki/API-Documentation)**
- 💻 **[개발 가이드](https://github.com/your-team/EasyTravel/wiki/Development-Guide)**
- ❓ **[FAQ](https://github.com/your-team/EasyTravel/wiki/FAQ)**

---

## 👥 개발팀

| 이름 | 역할 | GitHub |
|------|------|--------|
| **권민석** | Backend 개발, DB 관리, 게시판 | [@kwonminseok](https://github.com/kwonminseok) |
| **김범진** | AI 챗봇, 추천 기능 관리 | [@kimbeomjin](https://github.com/kimbeomjin) |
| **이동건** | 맛집, 숙박정보 탭 관리 | [@leedonggun](https://github.com/leedonggun) |
| **장지훈** | AI 여행지 추천 구현 | [@jangjihoon](https://github.com/jangjihoon) |



## ⭐ 별표 부탁드려요!

이 프로젝트가 도움이 되셨다면 ⭐ 별표를 눌러주세요! 여러분의 관심이 개발에 큰 힘이 됩니다.

**Happy Traveling! ✈️🌍**
