package com.oopinspring.mvc.service;

import com.oopinspring.mvc.dao.BoardDao;
import com.oopinspring.mvc.domain.BoardVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardDao boardDao;

    @Override
    public List<BoardVO> list() {
        return boardDao.list();
    }

    @Override
    public int delete(BoardVO boardVO) {
        return boardDao.delete(boardVO);
    }

    @Override
    public int edit(BoardVO boardVO) {
        return boardDao.update(boardVO);
    }

    @Override
    public void write(BoardVO boardVO) {
        boardDao.insert(boardVO);
    }

    @Override
    public BoardVO read(int seq) {
        boardDao.updateReadCount(seq);
        return boardDao.select(seq);
    }
}
