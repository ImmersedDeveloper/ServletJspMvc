package hello.servlet.web.frontcontroller.v2.controller;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFormControllerV2 implements ControllerV2 {
    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
        String viewPATH = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPATH);
        dispatcher.forward(request, response);
        */

        /**
        MyView myView = new MyView("/WEB-INF/views/new-form.jsp");
        return myView;
         */

        // Ctrl Alt n (inline method)
        return new MyView("/WEB-INF/views/new-form.jsp");
    }
}
