package com.primus;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SchedulerThread extends Thread {



    @Autowired
    ApplicationManager applicationManager ;

    @Override
    public void run() {

        try {
            for (; ; ) {
               // appointmentStatusUpdater.runScheduledJob();
                Thread.sleep(Long.parseLong(applicationManager.getThreadInterval()));
            }
        }catch(Exception ex) {
            //Logger.logException("Error" , this.getClass() ,ex);
        }
    }


}
