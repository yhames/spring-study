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

### 회원(Member)

_Entity_

#### 속성

- `email`: 이메일 주소, 고유 식별자
- `nickname`: 닉네임
- `passwordHash`: 해싱된 비밀번호
- `status`: 회원 상태 (활성, 비활성 등)

#### 행위

- `create()`: 회원 생성: email, nickname, password, passwordEncoder
- `activate()`: 가입 완료를 시킨다.
- `deactivate()`: 탈퇴한다.
- `verifyPassword()`: 비밀번호를 검증한다.
- `changeNickname()`: 닉네임을 변경한다.
- `changePassword()`: 비밀번호를 변경한다.

#### 규칙

- 회원 생성 후 상태는 가입 대기
- 일정 조건을 만족하면 가입 완료가 된다.
- 가입 대기 상태에서만 가입 완료가 될 수 있다.
- 가입 완료 상태에서는 탈퇴할 수 있다.
- 회원의 비밀번호는 해시를 만들어서 저장한다.

### 회원 상태

_Enum_

#### 상수

- `PENDING`: 가입 대기
- `ACTIVE`: 가입 완료
- `DEACTIVATED`: 탈퇴

### 패스워드 인코더(PasswordEncoder)

_Domain Service_

[//]: # (도메인에서 기술 의존적인 행위는 도메인 서비스로 분리한다.)

#### 행위

- `encode()`: 비밀번호를 해싱한다.
- `matches()`: 입력된 비밀번호와 해싱된 비밀번호를 비교한다.

### 강사

### 강의

### 수업

### 섹션

### 수강

### 진도