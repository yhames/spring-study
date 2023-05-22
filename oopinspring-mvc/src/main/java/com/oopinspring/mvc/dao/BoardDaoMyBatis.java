package com.oopinspring.mvc.dao;

import com.oopinspring.mvc.domain.BoardVO;
import lombok.AllArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class BoardDaoMyBatis implements BoardDao {

    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<BoardVO> list() {
        return sqlSessionTemplate.selectList("list");
    }

    @Override
    public int delete(BoardVO boardVO) {
        return sqlSessionTemplate.delete("delete", boardVO);
    }

    @Override
    public int deleteAll() {
        return sqlSessionTemplate.delete("deleteAll");
    }

    @Override
    public int update(BoardVO boardVO) {
        return sqlSessionTemplate.update("update", boardVO);
    }

    @Override
    public void insert(BoardVO boardVO) {
        sqlSessionTemplate.insert("insert", boardVO);
    }

    @Override
    public BoardVO select(int seq) {
        BoardVO vo = (BoardVO) sqlSessionTemplate.selectOne("select", seq);
        return vo;
    }

    @Override
    public int updateReadCount(int seq) {
        return sqlSessionTemplate.update("updateCount", seq);
    }
}
