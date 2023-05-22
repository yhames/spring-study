package com.oopinspring.mvc.dao;

import com.oopinspring.mvc.domain.BoardVO;

import java.util.List;

public interface BoardDao {

    public List<BoardVO> list();

    public int delete(BoardVO boardVO);

    public int deleteAll();

    public int update(BoardVO boardVO);

    public void insert(BoardVO boardVO);

    public BoardVO select(int seq);

    public int updateReadCount(int seq);
}
