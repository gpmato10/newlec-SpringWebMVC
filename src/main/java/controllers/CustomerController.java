package controllers;

import dao.NoticeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import vo.Notice;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by dw on 2016. 5. 2..
 */

@Controller
@RequestMapping("/customer/*")
public class CustomerController {
    private NoticeDao noticeDao;

    @Autowired
    public void setNoticeDao(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }

    @RequestMapping("notice.htm")
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

    @RequestMapping("noticeDetail.htm")
    public String noticeDetail(String seq, Model model) throws SQLException, ClassNotFoundException {

        Notice notice = noticeDao.getNotice(seq);

//        ModelAndView mv = new ModelAndView("noticeDetail.jsp");
        model.addAttribute("notice", notice);


        return "noticeDetail.jsp";
    }

    @RequestMapping( value = "noticeReg.htm", method = RequestMethod.GET)
    public String noticeReg() {
        return "noticeReg.jsp";
    }

    @RequestMapping(value = "noticeReg.htm", method = RequestMethod.POST)
    public String noticeReg(Notice n /*String title, String content*/) throws SQLException, ClassNotFoundException {
//        Notice n = new Notice();
//        n.setTitle(title);
//        n.setContent(content);

        noticeDao.insert(n);

        return "redirect:notice.htm";
    }

    @RequestMapping (value = "noticeEdit.htm", method = RequestMethod.GET)
    public String noticeEdit(String seq, Model model) throws SQLException, ClassNotFoundException {

        Notice notice = noticeDao.getNotice(seq);
        model.addAttribute("notice", notice);

        return "noticeEdit.jsp";
    }

    @RequestMapping (value = "noticeEdit.htm", method = RequestMethod.POST)
    public String noticeEdit(Notice n) throws SQLException, ClassNotFoundException {

//        Notice notice = noticeDao.getNotice(seq);
//        model.addAttribute("notice", notice);
        noticeDao.update(n);

        return "redirect:noticeDetail.htm?seq="+n.getSeq();
    }

    @RequestMapping (value = "noticeDel.htm", method = RequestMethod.GET)
    public String noticeDel(String seq) throws SQLException, ClassNotFoundException {
        noticeDao.delete(seq);

        return "redirect:notice.htm";
    }
}
