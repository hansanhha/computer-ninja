#### 인덱스
- [GitHub Gist](#github-gist)
- [사용법](#사용법)


## GitHub Gist

깃허브 지스트는 스니펫(작은 코드 조각), 설정 파일, 메모 등을 Git 기반으로 저장하고 공유할 수 있는 서비스이다

문서 + 코드 조각 + Git 버전 관리가 결합된 경량 리포지토리로 URL 하나로 공유할 수 있다

짧은 코드/설정 파일/일회성 스크립트 공유, 간단한 문서 호스팅 등의 목적으로 사용한다

리포지토리: 프로젝트 관리

지스트: 코드 조각/메모 공유

**공개/비공개 상관없이 무료**


## 사용법

리포지토리와 동일하게 README.md 이름을 가진 파일이 있으면 마크다운 렌더링을 거쳐 메인으로 표시된다

UI
- [gist.github.com](https://gist.github.com) 접속
- 필요한 문서 작성 후 Gist 생성
- 클론 (HTTPS 또는 SSH)
- 파일 수정 -> 커밋/푸시

GitHub CLI
- gist 생성: `cat <file> | gh gist create --filename='hello gist' --desc='github gist testing'`
- gist 수정: `gh gist edit` -> gist 선택 -> 수정
- gist 삭제: `gh gist delete` -> gist 선택 -> 삭제