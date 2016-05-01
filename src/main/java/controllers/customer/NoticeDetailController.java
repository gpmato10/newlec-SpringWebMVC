package controllers.customer;

import dao.NoticeDao;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import vo.Notice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dw on 2016. 5. 2..
 */
public class NoticeDetailController implements Controller {

    private NoticeDao noticeDao;

    public void setNoticeDao(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String seq = request.getParameter("seq");

        Notice notice = noticeDao.getNotice(seq);

        ModelAndView mv = new ModelAndView("noticeDetail.jsp");
        mv.addObject("notice", notice);

        return mv;
    }
}
