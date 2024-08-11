package exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 400 Bad Request
    BAD_REQUEST(400, "잘못된 요청입니다."),
    MISSING_REQUEST_PARAMETER(400, "요청 파라미터가 누락되었습니다."),
    INVALID_REQUEST_BODY(400, "유효하지 않은 요청 본문입니다."),
    INVALID_TYPE(400, "유효하지 않은 타입 값입니다."),
    JSON_PARSE_ERROR(400, "JSON 파싱 오류가 발생했습니다."),
    LOCATION_NOT_EXISTS(400, "사용자의 위치를 찾을 수 없습니다."),

    // 401 Unauthorized
    UNAUTHORIZED(401, "인증이 필요합니다."),
    AUTHENTICATION_FAILED(401, "인증에 실패했습니다."),

    // 403 Forbidden
    FORBIDDEN(403, "권한이 없습니다."),
    ACCESS_DENIED(403, "접근이 거부되었습니다."),

    // 404 Not Found
    NOT_FOUND(404, "요청한 리소스를 찾을 수 없습니다."),
    WIFI_NOT_FOUND(404, "와이파이 정보를 찾을 수 없습니다."),
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다."),
    DATABASE_ERROR(500, "데이터베이스 오류가 발생했습니다."),
    UNEXPECTED_ERROR(500, "예기치 않은 오류가 발생했습니다.");

    private final int code;
    private final String message;

    public String getName() {
        return this.name();
    }
}
