package cn.yunlingfly.hikaricp.controller;


import cn.yunlingfly.hikaricp.bean.Person;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class HealthController {
	@RequestMapping(value = "/health",method = RequestMethod.GET)
	public Object getAll(){
		return "ok";
	}
}
