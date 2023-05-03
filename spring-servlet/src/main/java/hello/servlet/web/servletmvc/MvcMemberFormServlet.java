package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // WEB-INF 내 파일은 반드시 컨트롤러를 거쳐서 불러와야함.
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);  // *dispatches* a request from controller to view.
                                                                            // 컨트롤러에서 해당 경로의 파일을 가져옴
        dispatcher.forward(req, res);   // 서블릿에서 다른 서블릿이나 jsp(new-form.jsp)를 호출
                                        // *redirect* 방식과는 다르게 클라이언트에서 요청이 다시 오는 것이 아니라
                                        // 서버 내부에서 다시 호출이 발생함.
    }
}
