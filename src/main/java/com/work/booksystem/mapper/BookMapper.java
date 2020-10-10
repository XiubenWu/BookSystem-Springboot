package com.work.booksystem.mapper;

import com.work.booksystem.model.BookInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BookMapper {
    @Select("select * from book_info")
    public List<BookInfo> selectAllBook();

    @Select("select * from book_info where num=#{num}")
    public BookInfo selectByNum(int num);

    @Select("select * from book_info where name like #{name}")
    public List<BookInfo> selectByName(String name);

    @Insert("insert into book_info(num, name, type,author,address,subscribeTime,isSubscirbe,isScrap) values(#{num}, #{name}, #{type},#{author},#{address},#{subscribeTime},0,0)")
    public void insertBook(int num, String name, String type, String author, String address, String subscribeTime);

    @Update("update book_info set name=#{name},type=#{type},author=#{author},address=#{address} where num=#{num}")
    public void updateBook(int num,String name,String type,String author,String address);

    @Update("update book_info set isSubscirbe=#{isSubscirbe} where num=#{num}")
    public void updateIsSubscirbeByNum(int num, int isSubscirbe);

    @Update("update book_info set isScrap=#{isScrap} where num=#{num}")
    public void updateIsScrapByNum(int num, int isScrap);

    @Update("update book_info set subscribeTime=#{subscribeTime} where num=#{num}")
    public void updateSubscribeTimeByNum(int num, String subscribeTime);

    @Update("update book_info set isSubscirbe=#{isSubscirbe},isScrap=#{isScrap},subscribeTime=#{subscribeTime}")
    public void resetBookState(int isSubscirbe, int isScrap, String subscribeTime);

    @Delete("delete from book_info where num=#{num}")
    public void deleteBook(int num);
}
