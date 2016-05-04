package controllers;

import dao.NoticeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import vo.Notice;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
    public String noticeReg(Notice n, HttpServletRequest request /*String title, String content*/) throws SQLException, ClassNotFoundException, IOException {

        List<CommonsMultipartFile> files = n.getFile();
        for (int i=0; i<files.size(); i++) {
            if(!files.get(i).isEmpty()) {
                String fname = files.get(i).getOriginalFilename();
                String path = request.getServletContext().getRealPath("/customer/upload");  // '/customer/upload' 가 포함된 실제경로
                String fpath = path + "/" + fname;

                // if문 까지 코드는 '/customer/upload' 폴더가 없으면 생성하는 코드.
                File file = new File(path);
                if(file.exists() == false){
                    file.mkdirs();
                }
                FileOutputStream fs = new FileOutputStream(fpath);
                fs.write(files.get(i).getBytes());
                fs.close();


                System.out.println(fname);
                /*NoticeFile nf = new NoticeFile();
                nf.setNoticeSeq(n.getSeq());
                nf.setFileSrc(fname);
                NoticeFileDao.insert(nf);
                n.setFileSrc(fname); */
            }
        }

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

    @RequestMapping (value = "download.htm")
    public void download(String p, String f, HttpServletRequest request, HttpServletResponse response)
            throws IOException {

//        /*
        System.out.println(p);
        System.out.println(f);
//        String fname = new String(f.getBytes("iso-8859-1"), "utf-8");
        String fname = new String(f.getBytes("utf-8"), "utf-8");
        System.out.println(fname);
//        response.setHeader("Content-Disposition",
//                "attachment;filename=" + new String(fname.getBytes(), "iso-8859-1"));
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(fname.getBytes(), "utf-8"));
        String rootPath = request.getServletContext().getRealPath("/");

        String fullPath = rootPath + p + "/" + fname;
//        ttt = ttt + "/" + fname;

//        System.out.println(ttt);
        System.out.println(fullPath);

        FileInputStream fin = new FileInputStream(fullPath);
//        FileInputStream fin = new FileInputStream(ttt);
        ServletOutputStream sout = response.getOutputStream();

        byte[] buf = new byte[1024];
        int size = 0;

        while ((size = fin.read(buf, 0, 1024)) != -1) {
            sout.write(buf, 0, size);
        }

        fin.close();
        sout.close();
//        */
    }
}
