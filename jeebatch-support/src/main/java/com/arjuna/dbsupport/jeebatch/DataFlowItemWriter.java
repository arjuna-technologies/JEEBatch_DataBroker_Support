/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.dbsupport.jeebatch;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.chunk.ItemWriter;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DataFlowItemWriter implements ItemWriter
{
    private static final Logger logger = Logger.getLogger(DataFlowItemWriter.class.getName());

    @Inject
    private JobContext jobContext;
    
    @Override
    public Serializable checkpointInfo()
        throws Exception
    {
        logger.log(Level.INFO, "DataFlowItemWriter.checkpointInfo: " + jobContext.getJobName());

        return null;
    }

    @Override
    public void open(Serializable checkpoint)
        throws Exception
    {
        logger.log(Level.INFO, "DataFlowItemWriter.open: " + jobContext.getJobName());
    }

    @Override
    public void writeItems(List<Object> items)
        throws Exception
    {
        logger.log(Level.INFO, "DataFlowItemWriter.writeItems: " + jobContext.getJobName());
        logger.log(Level.INFO, "    writeItems  -  jobContext: " + jobContext);
    }

    @Override
    public void close()
        throws Exception
    {
        logger.log(Level.INFO, "DataFlowItemWriter.close: " + jobContext.getJobName());
    }
}
