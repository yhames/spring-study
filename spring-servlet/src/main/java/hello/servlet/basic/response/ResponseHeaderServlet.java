package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ResponseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Status-Line
        res.setStatus(HttpServletResponse.SC_OK);

        // Response-Headers
        res.setHeader("Content-Type", "text/plain;charset=utf-8");
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");  // 캐시 완전히 무효화
        res.setHeader("Pragma", "no-cache");  // 과거 브라우저(HTTP 1.0)에서 캐시 무효화
        res.setHeader("my-header", "hello");

        // Header 편의 메서드
        content(res);
        cookie(res);
        redirect(res);

        // message body - 단순 텍스트
        res.getWriter().println("OK");
    }

    private void content(HttpServletResponse res) {
        /**
         * Content-Type: text/plain;charset=utf-8
         * Content-Length: 2
         */
//        res.setHeader("Content-Type", "text/plain;charset=utf-8");
        res.setContentType("text/plain");
        res.setCharacterEncoding("utf-8");
//        res.setContentLength(2);    // 생략시 자동생성
    }

    private void cookie(HttpServletResponse res) {
        /**
         * Set-Cookie: myCookie=good;Max-Age:600
         */
//        res.setHeader("Set-Cookie", "myCookie=good;Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600);
        res.addCookie(cookie);
    }

    private void redirect(HttpServletResponse res) throws IOException {
        /**
         * Status Code 302
         * Location: /basic/hello-form.html
         */
//        res.setStatus(HttpServletResponse.SC_FOUND);
//        res.setHeader("Location", "/basic/hello-form.html");
        res.sendRedirect("/basic/hello-form.html");
    }
}
