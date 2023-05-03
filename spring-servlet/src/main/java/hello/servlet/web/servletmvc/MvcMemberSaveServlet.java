package hello.servlet.web.servletmvc;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MvcMemberSaveServlet", urlPatterns = "/servlet-mvc/members/save")
public class MvcMemberSaveServlet extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // 요청 데이터 파싱
        String username = req.getParameter("username");
        int age = Integer.parseInt(req.getParameter("age"));

        // 해당 데이터 저장 -> service 계층에서 해야하는데, 프로젝트가 작아서 일단 합쳐둠
        Member member = new Member(username, age);
        memberRepository.save(member);

        // Model에 데이터 보관
        req.setAttribute("member", member);

        // 컨트롤러에서 뷰 호출
        String viewPath = "/WEB-INF/views/save-result.jsp";
        req.getRequestDispatcher(viewPath).forward(req, res);
    }
}
