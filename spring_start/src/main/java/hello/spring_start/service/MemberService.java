package hello.spring_start.service;

import hello.spring_start.domain.Member;
import hello.spring_start.repository.MemberRepository;
import hello.spring_start.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
// 모든 작업을 하나의 트랜잭션으로 묶어서 처리하겠다
// 잘되면 반영, 안되면 롤백
// @Service
// 이 클래스를 객체로 만들어서 spring 컨테이너에 넣어둔다
public class MemberService {

    // 여기서 사용하는 레포와 테스트에서 사용하는 레포가 다른 인스턴스가 아니라 하나의 저장소를 공유
    private final MemberRepository memberRepository;

    // @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 시간 조회 일일히 하기 귀찮으니까 aop 사용

    // 회원 가입
    public Long join(Member member) {
        // 회원 가입 시간 측정
//        long start = System.currentTimeMillis();
//        try {
//            validateDuplicateMember(member);
//            memberRepository.save(member);
//            return member.getId();
//        } finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("join " + timeMs + "ms");
//        }
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
        // ifPresent: 값이 있을 때만 동작을 실행하고 없으면 안함
        // IllegalStateException: 부적절한 상태일 때 사용
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        // 회원 조회 시간 측정
//        long start = System.currentTimeMillis();
//        try {
//            return memberRepository.findAll();
//        } finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("findMembers " + timeMs + "ms");
//        }
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}