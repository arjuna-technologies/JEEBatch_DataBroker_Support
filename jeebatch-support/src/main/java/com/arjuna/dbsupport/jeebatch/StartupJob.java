/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.dbsupport.jeebatch;

import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.StepExecution;
import javax.ejb.Startup;
import javax.ejb.Singleton;

@Startup
@Singleton
public class StartupJob implements Serializable
{
    private static final long serialVersionUID = 8048435909708986821L;

    private static final Logger logger = Logger.getLogger(StartupJob.class.getName());

    @PostConstruct
    public void startupJob()
    {
        logger.log(Level.INFO, "StartupJob.startupJob");

        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Properties  properties  = new Properties();

        long execId = jobOperator.start("testJob", properties);

        for (StepExecution stepExecution: jobOperator.getStepExecutions(execId))
        {
            logger.log(Level.INFO, "********: " + stepExecution.getStepName() + ", " + stepExecution.getBatchStatus() + " ********");
        }

        logger.log(Level.INFO, "StartupJob.startupJob: " + execId);
    }
}
