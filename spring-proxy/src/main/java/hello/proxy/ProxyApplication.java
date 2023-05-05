package hello.proxy;

import hello.proxy.config.v1_proxy.ConcreteProxyConfig;
import hello.proxy.config.v2_dynamicproxy.DynamicProxyBasicConfig;
import hello.proxy.config.v2_dynamicproxy.DynamicProxyFilterConfig;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 로그출력 프록시 기능 추가 3가지 상황을 가정
 * AppV1Config : 인터페이스가 있는 구현 클래스에 적용
 * AppV2Config : 인터페이스가 없는 구현 클래스에 적용
 * AppV3Config : 컨포넌트스캔 대상에 적용
 */
//@Import({AppV1Config.class, AppV2Config.class})
//@Import(InterfaceProxyConfig.class)
//@Import(ConcreteProxyConfig.class)
//@Import(DynamicProxyBasicConfig.class)
@Import(DynamicProxyFilterConfig.class)
// @Configuration 은 @Component 가 포함되어있어서 컴포넌트스캔의 대상이 된다.
// 테스트를 위해 각 버전의 config 파일을따로 설정하기 위해 컴포넌트스캔에서 제외시켜야한다.
// 따라서 BasePackages 를 설정해서 config 패키지는 컴포넌트스캔에서 제외하도록 설정하였다.
@SpringBootApplication(scanBasePackages = "hello.proxy.app") // 주의
public class ProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
