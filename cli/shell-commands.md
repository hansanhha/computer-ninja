#### 인덱스
- [파일, 디렉토리 작업](#파일-디렉토리-작업)
- [프로세스 작업 제어](#프로세스-작업-제어)
- [네트워크 관련 명령어](#네트워크-관련-명령어)
- [시스템 정보](#시스템-정보)
- [권한, 소유권 변경](#권한-소유권-변경)
- [압축, 아카이브](#압축-아카이브)
- [텍스트 처리](#텍스트-처리)


## 파일, 디렉토리 작업

현재 디렉토리 파일 목록 조회: `ls` (list)

디렉토리 이동: `cd <path>` (change directory)

직전 디렉토리 이동: `-`

홈 디렉토리 이동: `~`

현재 작업 경로 확인: `pwd` (print working directory)

디렉토리 생성: `mkdir <directory>` (make directory)

빈 파일 생성 또는 파일 타임스탬프 업데이트: `touch <file>`

파일 삭제: `rm <file>` (remove)

디렉토리 삭제: `rmdir -rf <dir>` (remove directory, recursive, force)

복사: `cp <src> <dest>` (copy)

이동/이름 변경: `mv <src> <dest>` (move)

파일 내용 출력: `cat <file>` (concatenate)

파일 앞의 일부 출력: `head <file>`

파일 뒤의 일부 출력: `tail <file>`

페이지 단위 보기: `less`

파일 메타데이터 조회: `stat [file]` (statistics)

파일 검색: `find <path>`


## 프로세스 작업 제어

프로세스 목록 확인: `ps` (process)

리소스 사용량 모니터링: `top`

프로세스 종료: `kill [signal] <PID>`

백그라운드 작업 확인: `jobs`

백그라운드 -> 포그라운드로 전환: `fg` (foreground)

백그라운드로 실행: `bg` (background)

명령어 백그라운드 실행: `&` (`md test & cd test`), `&&` (이전 명령이 성공했을 경우에만 다음 명령 실행)

명령어 순차 실행: `;` (`md test; cd test`)


## 네트워크 관련 명령어

HTTP 요청: `curl [option...] <url>` (client url)

파일 다운로드: `wget <download url>` (web get)

연결 체크: `ping <domain, ip>` (packet internet groper)

포트/네트워크 상태 확인: `netstat` (network statistics)

dns 체크: `nslookup <domain>` (name server lookup), `dig` (domain information groper)


## 시스템 정보

시스템 정보: `uname` (unix name)

디스크 사용량: `df` (disk free)

디렉토리/파일 용량: `du [file/directory]` (disk usage)

현재 사용자: `whoami`

권한/그룹 정보: `id`


## 권한, 소유권 변경

권한 변경: `chmod <000> file` (change mode)

소유자 변경: `chown [owner][:owner group] <file/directory>` (change owner)

그룹 변경: `chgrp <group> <file/directory>` (change group)


## 압축, 아카이브

여러 파일과 디렉토리를 하나의 파일로 묶는 아카이브 생성/추출: `tar` (tape archive)

주요 옵션
- `-c` (create): 새 tar 아카이브 생성
- `-x`: tar 아카이브 파일 압축 해제
- `-v` (verbose): 처리되는 파일 목록 상세 출력
- `-f` (file): 아카이브 파일 이름 지정 (반드시 마지막에 사용해야 함)
- `-z`: gzip을 사용하여 압축 또는 해제 (`.tar.gz`)
- `-j`: bzip2를 사용하여 압축하거나 해제 (`.tar.bz2`)
- `-t` : tar 파일에 포함된 파일 목록 출력

예시
- `tar -cvf <archive name>.tar <files/directories>`: 파일 묶기
- `tar -tvf <archive name>.tar`: tar 파일 내용 확인하기
- `tar -xvf <archive name>.tar`: tar 파일 압축 풀기
- `tar -czvf <archive name>.tar.gz <files/directories>`: tar로 묶고 gzip으로 파일 압축하기
- `tar -xzvf <archive name>.tar.gz`: gzip으로 압축된 tar 파일 압축 풀기
- `tar -cjzf <archive name>.tar.bz2 <files/directories>`: tar로 묶고 bzip2로 파일 압축하기
- `tar -xjzf <archive name>.tar.bz2 <files/directoreis>`: bzip2로 압축된 tar 파일 압축 풀기

압축/해제: `gzip <filename>.gz <files/directories>` (gnu zip), `gunzip <filename>.gz` (gnu unzip)

압축/해제 2: `zip <filename>.zip <files/directories>` `unzip <filename>.zip`


## 텍스트 처리

문자열 검색: `grep [options] <pattern> [file]` (global regular expression)

텍스트 파일 패턴 매칭 후 데이터 가공, 결과 추출: `awk [option] <'pattern { action }'> <file>`

텍스트 파일 스트림 편집(치환/삭제/라인 편집): `sed [option] <command> <file>` (stream editor)

텍스트 파일의 특정 필드 추출: `cut [option] <file>`

정렬: `sort [option] <file>`

중복 제거 또는 중복 횟수 카운트: `uniq` (unique)

문자 변환/삭제: `tr` (translate)

줄/단어/바이트 수 측정: `wc <file>` (word count)
