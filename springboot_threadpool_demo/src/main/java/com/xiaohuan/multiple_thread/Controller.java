package com.xiaohuan.multiple_thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-01 10:49
 */

@RestController
@RequestMapping("/multiple_thread/*")
public class Controller {
  @Autowired
  private AsyncService asyncService;

  @GetMapping("/async")
  public void async(){
    asyncService.executeAsync();
  }
}
