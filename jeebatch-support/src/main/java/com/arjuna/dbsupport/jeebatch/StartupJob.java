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
import javax.ejb.Startup;
import javax.ejb.Singleton;
import javax.inject.Inject;

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

        BatchDataConsumer batchDataConsumer = new BatchDataConsumer(null);
        BatchDataProvider batchDataProvider = new BatchDataProvider(null);

        _batchDataConsumerMap.add(batchDataConsumer);
        _batchDataProviderMap.add(batchDataProvider);

        JobOperator jobOperator = BatchRuntime.getJobOperator();

        Properties jobParameters = new Properties();
        jobParameters.setProperty(BatchDataConsumerMap.ID_PROPERTYNAME, batchDataConsumer.getId());
        jobParameters.setProperty(BatchDataProviderMap.ID_PROPERTYNAME, batchDataProvider.getId());

        long execId = jobOperator.start("testJob", jobParameters);

        logger.log(Level.INFO, "StartupJob.startupJob: " + execId);
    }

    @Inject
    private BatchDataConsumerMap _batchDataConsumerMap;

    @Inject
    private BatchDataProviderMap _batchDataProviderMap;
}