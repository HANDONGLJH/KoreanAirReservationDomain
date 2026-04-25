package com.koreanair.reservation.domain.reservation.state;

/**
 * 현재 상태에서 허용되지 않는 전이를 시도했을 때 던져진다.
 * State 패턴에서 "잘못된 행동"을 컴파일 타임에는 못 잡더라도 런타임에 명시적으로 거부하는 역할.
 */
public class InvalidStateTransitionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidStateTransitionException(String currentState, String action) {
        super(String.format("현재 상태 '%s' 에서 행동 '%s' 은 허용되지 않는다.", currentState, action));
    }
}
