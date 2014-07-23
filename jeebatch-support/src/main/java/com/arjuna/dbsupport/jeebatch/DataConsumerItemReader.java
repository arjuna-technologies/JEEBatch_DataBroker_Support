/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.dbsupport.jeebatch;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemReader;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DataConsumerItemReader implements ItemReader
{
    private static final Logger logger = Logger.getLogger(DataConsumerItemReader.class.getName());

    @Override
    public void open(Serializable checkpoint)
        throws Exception
    {
        logger.log(Level.INFO, "DataConsumerItemReader.open: jobName           " + _jobContext.getJobName());

        logger.log(Level.INFO, "DataConsumerItemReader.open: properties        " + _jobContext.getProperties());

        logger.log(Level.INFO, "DataConsumerItemReader.open: transientUserData " + _jobContext.getTransientUserData());

        String id = _jobContext.getProperties().getProperty(BatchDataConsumerMap.ID_PROPERTYNAME);

        logger.log(Level.INFO, "DataConsumerItemReader.open: id                " + id);

        _batchDataConsumer = _batchDataConsumerMap.get(id);

        logger.log(Level.INFO, "DataConsumerItemReader.open: batchDataConsumer " + _batchDataConsumer);        
    }

    @Override
    public Serializable checkpointInfo()
        throws Exception
    {
        logger.log(Level.INFO, "DataConsumerItemReader.checkpointInfo: " + _jobContext.getJobName());

        return null;
    }

    @Override
    public Object readItem()
        throws Exception
    {
        logger.log(Level.INFO, "DataConsumerItemReader.readItem: " + _jobContext.getJobName() + " -> " + _batchDataConsumer);

        return _batchDataConsumer.readItem();
    }

    @Override
    public void close()
        throws Exception
    {
        logger.log(Level.INFO, "DataConsumerItemReader.close: " + _jobContext.getJobName() + " -> " + _batchDataConsumer);

        _batchDataConsumerMap.remove(_batchDataConsumer);
    }

    @Inject
    private JobContext _jobContext;

    @Inject
    @BatchProperty
    private BatchDataConsumerMap _batchDataConsumerMap;

    private BatchDataConsumer _batchDataConsumer;
}
