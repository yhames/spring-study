package hello.advanced.trace.callback;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TraceTemplate {

    private final LogTrace trace;

    public TraceTemplate(LogTrace trace) {
        this.trace = trace;
    }

    /**
     * 지네릭 클래스 : 클래스명 뒤에 지네릭타입 지정
     * 지네릭 메서드 : 반환타입 앞에 지네릭타입 지정
     */
    public <T> T execute(String message, TraceCallBack<T> callBack) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);
            // 로직 호출
            T result = callBack.call();
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
