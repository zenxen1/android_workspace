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
	
	//UI 즉 인터페이스가 있는 결과물을 보고자 하는 브라우저로 요청이 들어올때..
	@RequestMapping(value="/board", method=RequestMethod.GET)
	public String selectAll(){
		System.out.println("브라우저의 selectAll 호출");
		return "board/list";
	}
	
}










