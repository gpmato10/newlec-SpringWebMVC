package controllers.customer;

import dao.NoticeDao;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import vo.Notice;

import java.util.List;


/**
 * Created by dw on 2016. 5. 1..
 */
public class NoticeController implements Controller {

    @Override
    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {

        NoticeDao dao = new NoticeDao();
        List<Notice> list = dao.getNotices(1, "title", "%%");


        ModelAndView mv = new ModelAndView("notice.jsp");
        mv.addObject("list", list);

        return mv;
    }
}
