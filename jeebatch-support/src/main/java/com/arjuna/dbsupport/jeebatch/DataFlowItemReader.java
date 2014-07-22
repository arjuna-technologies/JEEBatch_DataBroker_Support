/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.dbsupport.jeebatch;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.chunk.ItemReader;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DataFlowItemReader implements ItemReader
{
    private static final Logger logger = Logger.getLogger(DataFlowItemReader.class.getName());

    @Inject
    private JobContext jobContext;

    @Override
    public Serializable checkpointInfo()
        throws Exception
    {
        logger.log(Level.INFO, "DataFlowItemReader.checkpointInfo: " + jobContext.getJobName());

        return null;
    }

    @Override
    public void open(Serializable checkpoint)
        throws Exception
    {
        logger.log(Level.INFO, "DataFlowItemReader.open: " + jobContext.getJobName());
    }

    @Override
    public Object readItem()
        throws Exception
    {
        logger.log(Level.INFO, "DataFlowItemReader.readItem: " + jobContext.getJobName());
        logger.log(Level.INFO, "    readItem  -  jobContext: " + jobContext);

        try { Thread.sleep(1000); } catch (Throwable throwable) {};

        return "Message";
    }

    @Override
    public void close()
        throws Exception
    {
        logger.log(Level.INFO, "DataFlowItemReader.close: " + jobContext.getJobName());
    }
}
