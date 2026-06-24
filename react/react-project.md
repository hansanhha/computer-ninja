
`npm create vite@latest`: 리액트 프로젝트 생성

```text
root
├── public/
├── src/
├── package.json
├── package.json.lock
├── tsconfig.json, jsconfig.json
├── vite.config.js, webpack.config.js
├── .prettierrc
├── eslint.config.js
├── .env, .env.local
├── .gitignore
└── README.md
```

### `public/`

정적 자산이 위치하는 디렉토리 

빌드 시 그대로 복사된다.

`index.html`, `favicon.ico`, 정적 이미지, 메타 태그 템플릿 등

```text
root/
└── public/
    ├── favicon.ico
    └── index.html
```

### `src/`


리액트 앱 코드가 위치하는 디렉토리

주요 파일
- `src/main.jsx`, `src/main.tsx`: 리액트 앱 진입점 파일
- `src/App.jsx`, `src/App.tsx`: 루트 컴포넌트
- `src/index.css`, `src/global.css`: 글로벌 스타일

디렉토리
- `src/components/`: 재사용 가능한 UI 컴포넌트 (`Button`, `Card`)
- `src/pages/`: 라우트별 페이지 컴포넌트 (`HomePage`, `LoginPage`)
- `src/routes/`: 라우팅 설정 (`App`에서 `react-router`를 연결하는 코드)
- `src/hooks/`: 커스텀 훅 (`useAuth`, `useFetch`)
- `src/layouts/`: 페이지 공통 레이아웃 (`MainLayout`, `AuthLayout`)
- `src/services/` 또는 `src/api/`: 서버 호출, API 클라이언트 axios 설정
- `src/store/`: 글로벌 상태 관리 (Redux slice, Zustand store, Recoil atom)
- `src/context/`: React Context 관련 코드 (`AuthContext`, `ThemeContext`)
- `src/utils/` 또는 `src/lib/`: 공통 유틸 함수 (`formatDate.ts`, `validateEmail.ts`)
- `src/types/`, `src/models/`: 타입스크립트 타입 및 인터페이스
- `src/assets/`: 이미지, 아이콘, 폰트, 정적 데이터
- `src/styles/`: CSS, SCSS, Tailwind 설정, 스타일 변수

```text
root/
└── src/
    ├── api/
    │   └── apiClient.ts
    ├── assets/
    │   └── logo.svg
    ├── components/
    │   ├── Button/
    │   │   ├── Button.tsx
    │   │   └── Button.css
    │   └── Header/
    ├── context/
    │   └── AuthContext.tsx
    ├── hooks/
    │   └── useDebounce.ts
    ├── layouts/
    │   └── MainLayout.tsx
    ├── pages/
    │   ├── HomePage.tsx
    │   └── ProfilePage.tsx
    ├── store/
    │   └── authSlice.ts
    ├── styles/
    │   └── globals.css
    ├── types/
    │   └── user.ts
    ├── utils/
    │   └── formatDate.ts
    ├── App.tsx
    └── main.tsx
```

테스트 파일
- `src/__tests__/`, `src/components/__test__/`, `src/pages/__tests__/`로 디렉토리에 테스트 파일을 두거나
- `*.test.tsx` 또는 `*.spec.tsx` 형태로 컴포넌트 파일과 같이 둔다.

큰 프로젝트의 경우 기능 별로 더 세부적으로 디렉토리를 구성할 수도 있다. `src/account/`, `src/posts/`

### 기타 파일

`package.json`: 의존성, 스크립트, 빌드 명령 등

`package.json.lock`: 실제로 설치된 패키지와 종속성 트리 전체의 정확한 버전을 명시한 파일 (실행 환경을 일관되게 유지하려는 목적)

`tsconfig.json`, `jsconfig.json`: 타입스크립트 또는 JS 경로 설정

`vite.config.js`, `webpack.config.js`: 번들러 설정

`.prettierrc`: 포매터 설정

`eslint.config.js`: 린터 설정

`.env`, `.env.local`: 환경 변수 설정