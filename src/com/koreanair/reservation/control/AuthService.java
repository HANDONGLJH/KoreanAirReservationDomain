package com.koreanair.reservation.control;

import java.util.HashMap;
import java.util.Map;

import com.koreanair.reservation.domain.user.Member;

/**
 * 인증 서비스 — Control 계층. Iteration 1 Walking Skeleton 전용.
 *
 * <p>Iteration 1: hard-coded 샘플 회원 1명만 로그인 가능. 실제 DB / 해시 검증은 없음.
 * <p>TODO(iter2): Guest verifyIdentity(pnr, name, email) 구현, 세션 timeout.
 * <p>TODO(iter2): 기존 BookingController.authenticateMember(skypassNumber, password) 로 라우팅 통합.
 */
public class AuthService {

    /** 샘플 회원 저장소 — 데모용. 실제로는 DB 조회. */
    private final Map<String, Member> memberBySkypass = new HashMap<>();
    private final Map<String, String> passwordBySkypass = new HashMap<>();

    /** 현재 로그인 상태 — 단일 사용자 데모 한정. */
    private Member current;

    public AuthService() {
    }

    public void registerSample(Member member, String skypassNumber) {
        registerMember(member, skypassNumber, "pw-stub");
    }

    public Member registerMember(Member member, String skypassNumber, String password) {
        if (member == null || skypassNumber == null || skypassNumber.isBlank()
                || password == null || password.isBlank()) {
            throw new IllegalArgumentException("회원 등록 정보가 올바르지 않습니다.");
        }
        memberBySkypass.put(skypassNumber, member);
        passwordBySkypass.put(skypassNumber, password);
        return member;
    }

    /**
     * @return 성공 시 로그인된 회원, 실패 시 null (Iteration 2 에서 Exception 화).
     */
    public Member login(String skypassNumber, String passwordStub) {
        Member m = memberBySkypass.get(skypassNumber);
        if (m == null) {
            return null;
        }
        String expectedPassword = passwordBySkypass.get(skypassNumber);
        if (!passwordStub.equals(expectedPassword)) {
            return null;
        }
        this.current = m;
        return m;
    }

    public void logout() {
        this.current = null;
    }

    public Member currentMember() {
        return current;
    }
}
