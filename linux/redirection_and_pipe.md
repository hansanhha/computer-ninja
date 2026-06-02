## 리다이렉션과 파이프
- [리다이렉션](#리다이렉션)
- [pipe](#pipe)


## 리다이렉션

리다이렉션과 파이프에 대한 가장 기본적인 원리는 ['모든 것은 파일이다'](./Unix.md#파일-디스크립터-모든-것은-파일이다)에서 확인할 수 있다.

간단히 요약하자면 모든 프로세스는 기본적으로 stdin(FD 0), stdout(FD 1), stderr(FD 2) 파일 디스크립터를 가지게 된다.

```text
FD 0 → stdin
FD 1 → stdout
FD 2 → stderr
```

파일 디스크립터는 커널의 내부 객체(디스크 파일, 소켓 등 모든 컴퓨팅 객체)를 가리키는 참조이다.

stdin은 키보드로부터 입력을 받고 stdout은 터미널 출력, stderr은 터미널 에러 출력을 하는 파일 디스크립터이다.

유닉스/리눅스는 대부분의 컴퓨팅 자원에 대해 일관적으로 API를 관리하기 위해 파일 API를 사용한다. (일부 예외도 있음)

```shell
# 명령어
echo hello

# echo 프로세스의 hello 출력
echo
  stdout(FD 1)
       ↓
    terminal
```

리다이렉션은 입출력 대상을 변경하는 기술로 쉘이 리다이렉션 기호를 인식하여 특정 FD가 가리키는 대상을 바꾸는 것이다.

이 때 출력을 하는 대상이나 입력을 받는 대상은 구체적인 곳을 알지 못하고 FD를 통해서만 데이터 입출력 작업을 진행하는 것이다.

```shell
# stdout 리다이렉션
# hello를 output.txt 파일에 저장 
echo hello > output.txt

# 1. output.txt open
# 2. echo의 stdout(FD 1)을 output.txt 파일로 변경
# 3. exec(echo)

# 결과
echo
  stdout
     ↓
output.txt
```

```shell
# stdin 리다이렉션
# input.txt 파일의 내용을 sort 프로세스에 전달하여 정렬
sort < input.txt

# 1. input.txt open
# 2. sort의 stdin(FD 0)을 input.txt 파일로 변경
# 3. exec(sort)

# 결과
input.txt
     ↓
 stdin
 sort
```

```shell
# stderr 리다이렉션 (stdout은 제외됨)
command 2> error.log
command 2> /dev/null # /dev/null은 데이터를 버리는 장치임

# 결과
stderr(FD 2)
      ↓
 error.log
```

```shell
# stdout, stderr 리다이렉션
command > out.log 2>&1

# 결과
stdout
stderr
    ↓
 out.log
```

```shell
# 덮어쓰기
echo hello >> output.txt
```


## pipe

파이프는 프로세스의 stdout을 다른 프로세스의 stdin에 연결하는 것이다.

리다이렉션: 프로세스의 입출력 대상 변경

파이프: 프로세스의 stdout을 다른 프로세스의 stdin에 연결

```shell
# cat 출력을 grep에 전달한다
cat file.txt | grep hello

# 결과
cat stdout
      ↓
    pipe
      ↓
grep stdin
```

