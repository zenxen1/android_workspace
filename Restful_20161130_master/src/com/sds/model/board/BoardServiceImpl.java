package com.sds.model.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sds.domain.Board;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	@Qualifier("boardDAOMybatis")
	private BoardDAO boardDAO; //injection 
	
	public List selectAll() {
		System.out.println("서비스의 selectAll 호출");
		List list=boardDAO.selectAll();
		return list;
	}

	@Override
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
