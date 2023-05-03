package com.example.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출 : " + url);
//        connect();    // 생성자 호출 시점에는 초기화가 완료되지 않아서 URL -> Null
//        call("초기화 연결 메시지");   // afterPropertiesSet 에서 해줘야함.
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void connect() {
        System.out.println("NetworkClient.connect :" + url);
    }

    public void call(String msg) {
        System.out.println("NetworkClient.call :" + url + " msg :" + msg);
    }

    public void disconnect() {
        System.out.println("NetworkClient.disconnect :" + url);
    }

    @PostConstruct  // Java 공식 지원하는 JSR-250 표준 어노테이션, 다른 컨테이너에서도 사용 가능
    public void init(){ // 초기화 콜백, 의존관계주입 완료 후 호출
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close(){    // 소멸 콜백
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
