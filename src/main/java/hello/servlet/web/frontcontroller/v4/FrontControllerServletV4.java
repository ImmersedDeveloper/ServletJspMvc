package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServeltV4", urlPatterns = "/front-controller/v4/*") // *에 어떤 URL이 들어와도 해당 서블릿 무조건 호출되어서 매핑되는 원리
public class FrontControllerServletV4 extends HttpServlet {

    // 매핑 정보
    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    // 서블릿 생성시 매핑 정보 객체 미리 저장, 해당 데이터 조회해서 사용 가능
    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members/members",
                new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControlControllerV4.service"); // test

        String requestURI = request.getRequestURI();
        ControllerV4 controller = controllerMap.get(requestURI);

        if (controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap
        /**
         * Map<String, String> paramMap = new HashMap<>();
         *         request.getParameterNames().asIterator()
         *                 .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
         */

        Map<String, String> paramMap = createParamMap(request);
        
        ModelView mv = controller.process(paramMap);
        String viewName = mv.getViewName();// 논리 이름 new-form
        /**
         // /WEB-INF/views/new-form.jsp
         MyView view = new MyView("/WEB-INF/views/" + viewName + ".jsp");
         view.render(request, response);
         */
        // /WEB-INF/views/new-form.jsp
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), request, response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
