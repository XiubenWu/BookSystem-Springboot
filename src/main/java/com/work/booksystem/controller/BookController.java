package com.work.booksystem.controller;


import com.work.booksystem.mapper.BookMapper;
import com.work.booksystem.model.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
//@ResponseBody
public class BookController {
    @Autowired
    BookMapper bookMapper;

    @RequestMapping("/")
    public String home(Map<String, Object> map) {
        List<BookInfo> bookInfos = bookMapper.selectAllBook();
        map.put("bookInfos", bookInfos);
        map.put("feedBack", null);
        return "home";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/searchByName")
    public String searchByName(Map<String, Object> map, @RequestParam(name = "name") String name) {
        name = '%' + name + '%';
        try {
            List<BookInfo> bookInfos = bookMapper.selectByName(name);
            map.put("bookInfos", bookInfos);
            if (bookInfos.size() != 0) {
                map.put("feedBack", "查询成功,相关内容如下!");
            } else {
                map.put("feedBack", "查询成功,无相关信息!");
            }
        } catch (Exception e) {
            map.put("feedBack", "查询失败!");
        }
        return "home";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/searchByNum")
    public String searchByNum(Map<String, Object> map, @RequestParam(name = "num") String num) {
        int intNum = 0;
        if (errorCatch(num).equals("")) {
            intNum = Integer.parseInt(num);
        } else {
            map.put("feedBack", errorCatch(num));
            return "home";
        }
        BookInfo bookInfo = bookMapper.selectByNum(intNum);
        map.put("feedBack", "查询成功!");
        map.put("bookInfos", bookInfo);
        return "home";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/insertBook")
    public String insertBook(Map<String, Object> map, @RequestParam(name = "num") String num, @RequestParam(name = "name") String name, @RequestParam(name = "type") String type, @RequestParam(name = "author") String author, @RequestParam(name = "file") MultipartFile multipartFile) {
        int intNum = 0;
        try {
            intNum = Integer.parseInt(num);
        } catch (Exception e) {
            map.put("feedBack", "操作失败,图书编号必须为数字");
            return "home";
        }
        BookInfo testBookInfo = bookMapper.selectByNum(intNum);
        try {
            int test = testBookInfo.getNum();
            map.put("feedBack", "操作失败,图书编号重复,请重新输入!");
        } catch (Exception e) {
            if (name.equals("") || type.equals("")) {
                map.put("feedBack", "添加失败,请填写必要信息");
            } else {
                String saveDir = "G:\\WXB\\TEST\\booksystem\\src\\main\\resources\\static\\imags\\";
                if (multipartFile.isEmpty()) {
                    map.put("feedBack", "操作失败,未选择上传文件！");

                } else {
                    Set<String> allowSuffix = new HashSet<>(Arrays.asList("jpg", "png", "jpeg"));
                    String originalFileName = multipartFile.getOriginalFilename();
                    assert originalFileName != null;
                    int index = originalFileName.lastIndexOf(".");
                    String suffix = originalFileName.substring(index + 1);
                    String address = "imags\\" + intNum + "." + suffix;
//                    System.out.println(address);
                    File file = new File(saveDir + intNum + "." + suffix);
                    if (!allowSuffix.contains(suffix.toLowerCase())) {
                        map.put("feedBack", "上传文件格式错误(支持png,jpg,jpeg)");
                    } else {
                        try {
                            multipartFile.transferTo(file);
                            bookMapper.insertBook(intNum, name, type, author, address, "无");
                            map.put("feedBack", "添加图书成功");
                        } catch (Exception error) {
                            map.put("feedBack", "保存出错，稍后再试");
                        }

                    }

                }
            }
        }
        return "home";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/updateBook")
    public String updateBook(Map<String, Object> map, @RequestParam(name = "num") String num, @RequestParam(name = "name") String name, @RequestParam(name = "type") String type, @RequestParam(name = "author") String author, @RequestParam(name = "address") String address) {
        int intNum = 0;
        if (errorCatch(num).equals("")) {
            intNum = Integer.parseInt(num);
        } else {
            map.put("feedBack", errorCatch(num));
            return "home";
        }
        BookInfo bookInfo = bookMapper.selectByNum(intNum);
        String newName = "".equals(name) ? bookInfo.getName() : name;
        String newType = "".equals(type) ? bookInfo.getType() : type;
        String newAuthor = "".equals(author) ? bookInfo.getAuthor() : type;
        String newAddress = "".equals(address) ? bookInfo.getAddress() : address;
        bookMapper.updateBook(intNum, newName, newType, newAuthor, newAddress);
        map.put("feedBack", "更新成功!");
        return "home";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/deleteBook")
    public String deleteBook(Map<String, Object> map, @RequestParam(name = "num") String num) {
        int intNum = 0;
        try {
            intNum = Integer.parseInt(num);
        } catch (Exception e) {
            map.put("feedBack", "操作失败,图书编号必须为数字");
            return "home";
        }
        BookInfo testBookInfo = bookMapper.selectByNum(intNum);
        try {
            int test = testBookInfo.getNum();
            bookMapper.deleteBook(intNum);
            map.put("feedBack", "成功删除图书!");
        } catch (Exception e) {
            map.put("feedBack", "操作失败,图书不存在,请重新输入!");
        }
        return "home";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/subscribeBook")
    public String subscribeBook(Map<String, Object> map, @RequestParam(name = "num") String num) throws ParseException {
        int intNum = 0;
        try {
            intNum = Integer.parseInt(num);
        } catch (Exception e) {
            map.put("feedBack", "操作失败,图书编号必须为数字");
            return "home";
        }
        BookInfo bookInfo = bookMapper.selectByNum(intNum);
        try {
            int test = bookInfo.getNum();
        } catch (Exception e) {
            map.put("feedBack", "操作失败,图书不存在,请重新输入!");
            return "home";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (bookInfo.getIsScrap() != 0) {// not scrap
            System.out.println("图书已废弃");
            map.put("feedBack", "预定失败,图书已废弃!");
        } else if (bookInfo.getIsSubscirbe() == 0) {// not subscribe
            String dataString = sdf.format(new Date());
//            System.out.println(dataString);
            bookMapper.updateSubscribeTimeByNum(intNum, dataString);
            bookMapper.updateIsSubscirbeByNum(intNum, 1);// subscribe
            map.put("feedBack", "预定成功!");

        } else {//subscribed
            Date dateNow = new Date();
            Date subscribeTime = sdf.parse(bookMapper.selectByNum(intNum).getSubscribeTime());
            float error = (float) ((dateNow.getTime() - subscribeTime.getTime()) / (1000.0 * 60));//mins
            if (error < 30) {//取消预订
                bookMapper.updateIsSubscirbeByNum(intNum, 0);
                bookMapper.updateSubscribeTimeByNum(intNum, "无");
                map.put("feedBack", "取消预定成功!");
            } else {
                map.put("feedBack", "取消预定失败,超出可取消时间限制!");
            }
        }
        return "home";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/scrapBook")
    public String scrapBook(Map<String, Object> map, @RequestParam(name = "num") String num) {
        int intNum = 0;
        if (errorCatch(num).equals("")) {
            intNum = Integer.parseInt(num);
        } else {
            map.put("feedBack", errorCatch(num));
            return "home";
        }
        BookInfo bookInfo = bookMapper.selectByNum(intNum);
        if (bookInfo.getIsScrap() == 0) {
            bookMapper.updateIsScrapByNum(intNum, 1);
            bookMapper.updateIsSubscirbeByNum(intNum, 0);
            bookMapper.updateSubscribeTimeByNum(intNum, "无");
            map.put("feedBack", "图书已报废!");
        } else {
            bookMapper.updateIsScrapByNum(intNum, 0);
            map.put("feedBack", "图书已重新启用!");
        }
        return "home";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/resetBookState")
    public String resetBookState(Map<String, Object> map) {
        bookMapper.resetBookState(0, 0, "无");
        map.put("feedBack", "图书状态已复原!");
        return "home";
    }

//    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/postTest")
//    public String postTest(Map<String, Object> map, @RequestParam(name = "file") MultipartFile multipartFile) throws Exception {
//        String saveDir = "G:\\WXB\\TEST\\booksystem\\src\\main\\resources\\static\\imags\\";
//        if (multipartFile.isEmpty()) {
//            map.put("feedBack", "未选择上传文件！");
//
//        } else {
//            Set<String> allowSuffix = new HashSet<>(Arrays.asList("jpg", "png", "jpeg"));
//            String originalFileName = multipartFile.getOriginalFilename();
//            File file = new File(saveDir + "10.png");
//            assert originalFileName != null;
//            int index = originalFileName.lastIndexOf(".");
//            String suffix = originalFileName.substring(index + 1);
//            if (!allowSuffix.contains(suffix.toLowerCase())) {
//                map.put("feedBack", "上传文件格式错误(支持png,jpg,jpeg)");
//            } else {
//                try {
//                    multipartFile.transferTo(file);
//                    map.put("feedBack", "上传成功");
//                } catch (Exception e) {
//                    map.put("feedBack", "保存出错，稍后再试");
//                }
//
//            }
//
////            System.out.println(file);
//        }
//
//        return "home";
//    }


    private String errorCatch(String num) {
        int intNum = 0;
        try {
            intNum = Integer.parseInt(num);
        } catch (Exception e) {
            return "操作失败,图书编号必须为数字!";
        }
        BookInfo bookInfo = bookMapper.selectByNum(intNum);
        try {
            int test = bookInfo.getNum();
        } catch (Exception e) {
            return "操作失败,图书不存在,请重新输入!";
        }
        return "";
    }


}
