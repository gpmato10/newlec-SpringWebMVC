package controllers;

import dao.NoticeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import vo.Notice;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by dw on 2016. 5. 2..
 */

@Controller
public class CustomerController {
    private NoticeDao noticeDao;

    @Autowired
    public void setNoticeDao(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }

    @RequestMapping("/customer/notice.htm")
    public String noticees(String pg, String f, String q, Model model) throws SQLException, ClassNotFoundException {

        //        넘겨받은 값 있을때 쓰는 값들.


//        기본값
        int page = 1;
        String field = "TITLE";
        String query = "%%";

        if (pg != null && !pg.equals("")) {
            page = Integer.parseInt(pg);
        }
        if (f != null && !f.equals("")) {
            field = f;
        }
        if (q != null && !q.equals("")) {
            query = q;
        }

//        NoticeDao dao = new NoticeDao();
        List<Notice> list = noticeDao.getNotices(page, field, query);


//        ModelAndView mv = new ModelAndView("notice.jsp");
        model.addAttribute("list", list);

        return "notice.jsp";
    }

    @RequestMapping("/customer/noticeDetail.htm")
    public String noticeDetail() {
        return "noticeDetail.jsp";
    }
}