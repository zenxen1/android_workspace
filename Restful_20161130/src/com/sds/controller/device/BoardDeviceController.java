package com.sds.controller.device;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sds.domain.Board;
import com.sds.model.board.BoardService;

@Controller
public class BoardDeviceController {
	
	@Autowired
	private BoardService boardService;

	/*//UI 즉 인터페이스가 있는 결과물을 보고자 하는 브라우저로 요청이 들어올때...
	@RequestMapping(value="/browser/board",method=RequestMethod.GET)
	public String selectAll(){
		System.out.println("브라우저의 selectAll 호출");
		return "board/list";
	}*/
	
	//UI 필요없는 결과물을 가져가고자 하는 각종 디바이스의 경우..
	@RequestMapping(value="/board",method=RequestMethod.GET)
	@ResponseBody
	public List selectAll(){
		System.out.println("디바이스의 selectAll 호출");
		List list = new ArrayList<Board>();
		list = boardService.selectAll();
		return list;
	}
	
	//글 한건 등록요청
	@RequestMapping(value="/board",method=RequestMethod.POST)
	@ResponseBody
	public String regist(@RequestBody Board board){
		System.out.println("작성자는"+board.getWriter());
		return "1";
	}
	/*수정요청 : PUT
	 * POST, GET 이외의 HTTP 전송 메서드는 HTML5에서부터만 지원한다*/
	@RequestMapping(value="/board",method=RequestMethod.PUT)
	@ResponseBody
	public String update(Board board){
		System.out.println("수정할 작성자는"+board.getWriter());
		return "1";
	}
	
	@RequestMapping(value="/board/{board_id}",method=RequestMethod.DELETE)
	@ResponseBody
	public String update(@PathVariable int board_id){
		System.out.println("삭제할 작성자는"+board_id);
		return "1";
	}
	
}
