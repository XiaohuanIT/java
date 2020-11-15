package com.xiaohuan;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * <p>Class:EmailListener</p>
 * <p>Description:</p>
 * @author Liam
 * @Date [2012-9-7 上午8:44:53]
 */
public class EmailListener implements ApplicationListener {

	public void onApplicationEvent(ApplicationEvent  event) {
		if(event instanceof EmailEvent){
			EmailEvent emailEvent = (EmailEvent)event;
			emailEvent.print();
			System.out.println("the source is:"+emailEvent.getSource());
			System.out.println("the address is:"+emailEvent.address);
			System.out.println("the email's context is:"+emailEvent.text);
		}

	}

}
