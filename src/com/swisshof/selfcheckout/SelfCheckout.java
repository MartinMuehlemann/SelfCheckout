package com.swisshof.selfcheckout;


import com.swisshof.selfcheckout.gui.MainFrame;
import com.swisshof.selfcheckout.log.IReceiptsArchiver;
import com.swisshof.selfcheckout.log.ReceiptsArchiver;
import com.swisshof.selfcheckout.log.TransactionLogger;
import com.swisshof.selfcheckout.printer.IPrinter;
import com.swisshof.selfcheckout.printer.Printer;
import com.swisshof.selfcheckout.scheduler.EveningJob;
import com.swisshof.selfcheckout.scheduler.MorningJob;
import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.terminal.TerminalController;

import org.apache.logging.log4j.*;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class SelfCheckout {

	public final static Logger logger = LogManager.getLogger(SelfCheckout.class.getName());

	protected SelfCheckoutContext context;
	protected MainStm mainStm;
	protected MainFrame guiMainFrame;
	
	protected TerminalController terminalController;
	
	protected ResourceProvider resourceProvider;
	
	protected ReceiptsArchiver receipsArchiver;
	
	protected TransactionLogger transactionLogger;
	
	protected Printer printer;
	
	
	
	public static void main(String[] args) {
		new SelfCheckout();
		
	}

	public SelfCheckout() {
		super();
		logger.info("Startup");
		
		resourceProvider = new ResourceProvider();
		context = new SelfCheckoutContext();
		mainStm = new MainStm(context);
		receipsArchiver = new ReceiptsArchiver(context);
		transactionLogger = new TransactionLogger();
		printer = new Printer();
		
		context.setMainStm(mainStm);
		context.setReceiptsArchiver(receipsArchiver);
		context.setTransactionLogger(transactionLogger);
		context.setPrinter(printer);
		
		terminalController = new TerminalController(context);
		context.setTerminal(terminalController);
		context.setResourceProvider(resourceProvider);		
		guiMainFrame = new MainFrame(context);
		
		context.setGui(guiMainFrame);

		
		// show GUI
		guiMainFrame.startGui();
		
		// enter init state
		mainStm.init();
		
		
		JobDetail eveningJob = JobBuilder.newJob(EveningJob.class)
			    .withIdentity("evening-job", "group1")
			    .build();
		
		JobDetail morningJob = JobBuilder.newJob(MorningJob.class)
			    .withIdentity("morning-job", "group1")
			    .build();
		
		CronTrigger eveningTrigger = TriggerBuilder.newTrigger()
			    .withIdentity("trigger1", "group1")
			    .withSchedule(CronScheduleBuilder.cronSchedule("50 * * * * ?"))
			    .build();
		
		CronTrigger morningTrigger = TriggerBuilder.newTrigger()
			    .withIdentity("trigger2", "group1")
			    .withSchedule(CronScheduleBuilder.cronSchedule("10 * * * * ?"))
			    .build();


        Scheduler scheduler;
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.getContext().put("context", context);
			scheduler.getContext().put("mainStm", mainStm);
	        scheduler.start();
	        scheduler.scheduleJob(eveningJob, eveningTrigger);
	        scheduler.scheduleJob(morningJob, morningTrigger);
	    } catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
