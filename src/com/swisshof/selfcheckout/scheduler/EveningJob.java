package com.swisshof.selfcheckout.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import com.swisshof.selfcheckout.SelfCheckoutContext;
import com.swisshof.selfcheckout.statemachine.MainStm;


public class EveningJob implements Job
{
    SelfCheckoutContext selfCheckoutContext = null;
    MainStm mainStm = null;
    
	public void execute(JobExecutionContext context) throws JobExecutionException {

        System.out.println("Evening Trigger");   
        try {
			selfCheckoutContext = (SelfCheckoutContext)context.getScheduler().getContext().get("context");
			mainStm= (MainStm)context.getScheduler().getContext().get("mainStm");
	        mainStm.processEvent(MainStm.Events.GOTO_INACTIVE);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}
