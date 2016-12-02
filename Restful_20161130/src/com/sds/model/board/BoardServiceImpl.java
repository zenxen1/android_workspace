package com.sds.model.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sds.domain.Board;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	@Qualifier("boardDAOmybatis")
	private BoardDAO boardDAO;

	@Override
	public List selectAll() {
		System.out.println("서비스 selectAll 호출");
		
		return boardDAO.selectAll();
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
