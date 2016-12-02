package com.sds.model.board;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sds.domain.Board;

@Repository
public class BoardDAOMybatis implements BoardDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List selectAll() {
		System.out.println("dao¿« selectAll »£√‚");
		List list=sqlSessionTemplate.selectList("Board.selectAll");
		return list;
	}

	public Board select(int board_id) {
		return null;
	}
	public void insert(Board board) {
	}

	public void update(Board board) {
	}

	public void delete(int board_id) {
		
	}
	
}
