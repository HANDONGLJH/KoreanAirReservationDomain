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

    private final Map<String, Member> memberBySkypass = new HashMap<>();
    private final Map<String, String> passwordBySkypass = new HashMap<>();
    private final Map<String, Member> memberByName = new HashMap<>();
    private int memberSequence = 4;

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
        memberByName.put(member.getName(), member);
        return member;
    }

    public String generateSkypassNumber() {
        return String.format("SKY-%03d-%03d", memberSequence / 1000, memberSequence % 1000);
    }

    public Member loginByName(String name, String password) {
        Member m = memberByName.get(name);
        if (m == null) {
            return null;
        }
        String skypass = m.getMemberNumber();
        String expectedPassword = passwordBySkypass.get(skypass);
        if (!password.equals(expectedPassword)) {
            return null;
        }
        this.current = m;
        return m;
    }

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
