package com.sds.controller.board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BoardController {
	
	@RequestMapping(value="/board", method=RequestMethod.GET)
	public String selectAll(){
		System.out.println("selectAll 호출함!!");
		return "board/list";
	}
	
	@RequestMapping(value="/board/{board_id}",method=RequestMethod.GET)
	public String select(@PathVariable int board_id, Model model){
		//디비연동!!!
		System.out.println("상세보기요청의 Board_id="+board_id);
		model.addAttribute("msg","상세내용저장");
		
		return "board/detail";
	}
	
	//글쓰기 (post)
	@RequestMapping(value="/board",method=RequestMethod.POST)
	public String insert(Board board){
		System.out.println("insert호출");
		System.out.println("writer is "+board.getWriter());
		System.out.println("title is "+board.getTitle());
		System.out.println("content is "+board.getContent());
		return "redirect:/board";
	}
}
