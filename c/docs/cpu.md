#### 인덱스
- [주요 CPU 아키텍처](#주요-cpu-아키텍처)


## 주요 CPU 아키텍처

CPU의 작업 파이프라인

```text
Fetch(인출) -> Decode(해석) -> Execute(실행) -> Writeback(쓰기)
```

CPU가 파이프라인을 거쳐 작업을 하도록 소프트웨어에서 명령을 내려야 하는데, 그 명령어의 집합을 **ISA (Instruction Set Architecture)**라고 한다 (소프트웨어와 하드웨어 간의 규약)

ISA는 CPU가 수행할 수 있는 명령어 목록을 규정할 뿐만 아니라 레지스터 모델(개수, 용도), 주소 지정 방식, 메모리 모델, 특권 수준, ABI 등을 정의한다

이 ISA를 설계하는 방법으로 **CISC (Complex Instruction Set Computer)**와 **RISC (Reduced Instruction Set Computer)**가 있다

```text
ISA 설계 철학: RISC / CISC
        ↓
ISA 명세: x86-64, ARMv8, RISC-V
        ↓
마이크로아키텍처: Zen, Skylake, Firestorm
        ↓
실제 칩: Ryzen, Xeon, M1
```

**x86/x86-x64**
- ISA: CISC
- 구현체: Intel Core/Xeon, AMD Ryzen/EPYC
- 용도: 데스크톱, 서버
- 특징: 방대한 명령어와 강력한 하위 호환성

**ARM (ARMv8/ARMv9)**
- ISA: RISC
- 구현체: 애플 실리콘(M-series), 퀄컴 스냅드래곤, Cortex-A/X, AWS Graviton
- 용도: 모바일, 노트북, 서버
- 특징: 고정 길이 명령, 전력 효율

**RISC-V**
- ISA: RISC
- 구현체: 마이크로컨트롤러, IoT, 임베디드 칩
- 용도: 연구, 임베디드 등
- 특징: 완전 오픈 ISA, 모듈식 확장

**MIPS**
- ISA: RISC
- 구현체: 네트워크 장비, 임베디드 시스템, 구형 닌텐도

**POWER/PowerPC**
- ISA: RISC
- 용도: 고성능 서버, 메인프레임 계열
