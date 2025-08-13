# Splearn 도메인 모델

## 도메인 모델 만들기

1. 도메인 학습
2. 개념 식별
3. 관계 정의
4. 속성 및 행위 명시
5. 시각화
6. 검증 및 반복

## Splearn 도메인

[//]: # (도메인에 대한 설명)

## 도메인 모델

---

### **회원 에그리거트**

### 회원(Member)

_Aggregate Root_

#### 속성

- `id`: 'Long'
- `email`: 이메일 주소, 고유 식별자 - Natural ID
- `nickname`: 닉네임
- `passwordHash`: 해싱된 비밀번호
- `status`: 회원 상태 (활성, 비활성 등)
- `detail`: `MemberDetail` 1:1

#### 행위

- `static register()`: 회원 등록: email, nickname, password, passwordEncoder
- `activate()`: 등록 완료를 시킨다.
- `deactivate()`: 탈퇴한다.
- `verifyPassword()`: 비밀번호를 검증한다.
- `changePassword()`: 비밀번호를 변경한다.
- `updateInfo()`: 회원의 닉네임, 프로필 주소, 자기 소개를 수정한다.

#### 규칙

- 회원 생성 후 상태는 등록 대기
- 일정 조건을 만족하면 등록 완료가 된다.
- 등록 대기 상태에서만 등록 완료가 될 수 있다.
- 등록 완료 상태에서는 탈퇴할 수 있다.
- 회원의 비밀번호는 해시를 만들어서 저장한다.
- 등록 완료 상태에서만 회원 정보를 수정할 수 있다.
- 프로필 주소는 중복을 허용하지 않고, 기존의 프로필 주소를 제거할 수 있다.

### 회원 상세 (MemberDetail)

_Entity_

- `id`: 'Long'
- `profile`: 프로필 주소. 모든 회원이 고유한 프로필 주소를 가져야 한다.
- `introduction`: 자기소개
- `registeredAt`: 등록 일시
- `activatedAt`: 등록 완료 일시
- `deactivatedAt`: 탈퇴 일시

#### 행위

- `create()`: 회원 상세 생성. 현재 시간을 등록 일시로 저장한다.
- `activate()`: 회원 등록 완료. 등룍 완료 일시를 저장한다.
- `deactivate()`: 회원 탈퇴. 탈퇴 일시를 저장한다.
- `updateInfo()`: 상세 정보 수정

#### 규칙



### 회원 상태

_Enum_

#### 상수

- `PENDING`: 등록 대기
- `ACTIVE`: 등록 완료
- `DEACTIVATED`: 탈퇴

### DuplicateEmailException

_Exception_

### 패스워드 인코더(PasswordEncoder)

_Domain Service_

[//]: # (도메인에서 기술 의존적인 행위는 도메인 서비스로 분리한다.)

#### 행위

- `encode()`: 비밀번호를 해싱한다.
- `matches()`: 입력된 비밀번호와 해싱된 비밀번호를 비교한다.

### 프로필 주소

_Value Object_

#### 속성

- "address": 프로필 주소 

---

### Email

_Value Object_

#### 속성

- `address`: 이메일 주소

---

### 강사

### 강의

### 수업

### 섹션

### 수강

### 진도