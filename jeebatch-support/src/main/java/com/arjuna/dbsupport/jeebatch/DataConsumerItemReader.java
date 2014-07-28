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
        logger.log(Level.FINE, "DataConsumerItemReader.open: jobName = " + _jobContext.getJobName());

        String id = _jobContext.getProperties().getProperty(BatchDataConsumerMap.ID_PROPERTYNAME);

        logger.log(Level.FINE, "DataConsumerItemReader.open: id = " + id);

        _batchDataConsumer = _batchDataConsumerMap.get(id);
    }

    @Override
    public Serializable checkpointInfo()
        throws Exception
    {
        logger.log(Level.FINE, "DataConsumerItemReader.checkpointInfo: " + _jobContext.getJobName());

        return null;
    }

    @Override
    public Object readItem()
        throws Exception
    {
        logger.log(Level.FINE, "DataConsumerItemReader.readItem: " + _jobContext.getJobName() + " -> " + _batchDataConsumer);

        return _batchDataConsumer.readItem();
    }

    @Override
    public void close()
        throws Exception
    {
        logger.log(Level.FINE, "DataConsumerItemReader.close: " + _jobContext.getJobName() + " -> " + _batchDataConsumer);

        _batchDataConsumerMap.remove(_batchDataConsumer);
    }

    @Inject
    private JobContext _jobContext;

    @Inject
    @BatchProperty
    private BatchDataConsumerMap _batchDataConsumerMap;

    private BatchDataConsumer _batchDataConsumer;
}
