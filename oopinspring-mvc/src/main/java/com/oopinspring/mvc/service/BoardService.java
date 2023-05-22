package com.oopinspring.mvc.service;

import com.oopinspring.mvc.domain.BoardVO;

import java.util.List;

public interface BoardService {

    public List<BoardVO> list();

    public int delete(BoardVO boardVO);

    public int edit(BoardVO boardVO);

    public void write(BoardVO boardVO);

    public BoardVO read(int seq);
}
