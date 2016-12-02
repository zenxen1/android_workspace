package com.sds.model.board;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sds.domain.Board;

@Repository
public class BoardDAOmybatis implements BoardDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate; 
	
	public List selectAll() {
		System.out.println("dao¿« selectAll»£√‚");
		
		return sqlSessionTemplate.selectList("Board.selectAll");
	}

	
	public Board select(int board_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Board board) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Board board) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int board_id) {
		// TODO Auto-generated method stub
		
	}
	

}
