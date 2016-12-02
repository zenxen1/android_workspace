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

	/*//UI �� �������̽��� �ִ� ������� ������ �ϴ� �������� ��û�� ���ö�...
	@RequestMapping(value="/browser/board",method=RequestMethod.GET)
	public String selectAll(){
		System.out.println("�������� selectAll ȣ��");
		return "board/list";
	}*/
	
	//UI �ʿ���� ������� ���������� �ϴ� ���� ����̽��� ���..
	@RequestMapping(value="/board",method=RequestMethod.GET)
	@ResponseBody
	public List selectAll(){
		System.out.println("����̽��� selectAll ȣ��");
		List list = new ArrayList<Board>();
		list = boardService.selectAll();
		return list;
	}
	
	//�� �Ѱ� ��Ͽ�û
	@RequestMapping(value="/board",method=RequestMethod.POST)
	@ResponseBody
	public String regist(@RequestBody Board board){
		System.out.println("�ۼ��ڴ�"+board.getWriter());
		return "1";
	}
	/*������û : PUT
	 * POST, GET �̿��� HTTP ���� �޼���� HTML5�������͸� �����Ѵ�*/
	@RequestMapping(value="/board",method=RequestMethod.PUT)
	@ResponseBody
	public String update(Board board){
		System.out.println("������ �ۼ��ڴ�"+board.getWriter());
		return "1";
	}
	
	@RequestMapping(value="/board/{board_id}",method=RequestMethod.DELETE)
	@ResponseBody
	public String update(@PathVariable int board_id){
		System.out.println("������ �ۼ��ڴ�"+board_id);
		return "1";
	}
	
}
