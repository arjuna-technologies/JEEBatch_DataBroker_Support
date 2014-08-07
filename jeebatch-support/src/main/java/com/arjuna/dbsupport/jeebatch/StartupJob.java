/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.dbsupport.jeebatch;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.ejb.Singleton;
import com.arjuna.databroker.data.DataConsumer;

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
        String              name       = "Test Job";
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(BatchDataProcessor.JOBID_PROPERTYNAME, "testJob");

        BatchDataProcessor batchDataProcessor = new BatchDataProcessor(name, properties);
        
        DataConsumer<Object> dataConsumer = batchDataProcessor.getDataConsumer(Object.class);
        dataConsumer.consume(null, "Test Data");
    }
}