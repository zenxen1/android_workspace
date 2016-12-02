package com.sds.controller.browser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sds.domain.Board;

@Controller
public class BoardBrowserController {

	//UI 즉 인터페이스가 있는 결과물을 보고자 하는 브라우저로 요청이 들어올때...
	@RequestMapping(value="/board",method=RequestMethod.GET)
	public String selectAll(){
		System.out.println("브라우저의 selectAll 호출");
		return "board/list";
	}
	
	/*//UI 필요없는 결과물을 가져가고자 하는 각종 디바이스의 경우..
	@RequestMapping(value="/device/board",method=RequestMethod.GET)
	public @ResponseBody List getList(){
		System.out.println("디바이스의 selectAll 호출");
		List list = new ArrayList<Board>();
		for(int i=0;i<10;i++){
		Board board = new Board();
		board.setBoard_id(1);
		board.setTitle("연습이네요");
		board.setContent("내용이네요");
		board.setWriter("ㅋㅋㅋ");
		list.add(board);
		}
		return list;
	}*/
	
}
