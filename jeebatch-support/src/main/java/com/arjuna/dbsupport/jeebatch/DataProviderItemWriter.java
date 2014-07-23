/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.dbsupport.jeebatch;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemWriter;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DataProviderItemWriter implements ItemWriter
{
    private static final Logger logger = Logger.getLogger(DataProviderItemWriter.class.getName());

    @Override
    public void open(Serializable checkpoint)
        throws Exception
    {
        logger.log(Level.INFO, "DataProviderItemWriter.open: jobName           " + _jobContext.getJobName());

        logger.log(Level.INFO, "DataProviderItemWriter.open: job properties    " + _jobContext.getProperties());

        logger.log(Level.INFO, "DataProviderItemWriter.open: step properties   " + _stepContext.getProperties());

        logger.log(Level.INFO, "DataProviderItemWriter.open: transientUserData " + _jobContext.getTransientUserData());
        
        String id = _jobContext.getProperties().getProperty(BatchDataProviderMap.ID_PROPERTYNAME);

        logger.log(Level.INFO, "DataProviderItemWriter.open: id                " + id);

        _batchDataProvider = _batchDataProviderMap.get(id);

        logger.log(Level.INFO, "DataProviderItemWriter.open: batchDataProvider " + _batchDataProvider);        
    }

    @Override
    public Serializable checkpointInfo()
        throws Exception
    {
        logger.log(Level.INFO, "DataProviderItemWriter.checkpointInfo: " + _jobContext.getJobName());

        return null;
    }

    @Override
    public void writeItems(List<Object> items)
        throws Exception
    {
        logger.log(Level.INFO, "DataProviderItemWriter.writeItems: " + _jobContext.getJobName());

        _batchDataProvider.writeItems(items);
    }

    @Override
    public void close()
        throws Exception
    {
        logger.log(Level.INFO, "DataProviderItemWriter.close: " + _jobContext.getJobName());

        _batchDataProviderMap.remove(_batchDataProvider);
    }

    @Inject
    private JobContext _jobContext;

    @Inject
    private StepContext _stepContext;

    @Inject
    @BatchProperty
    private BatchDataProviderMap _batchDataProviderMap;

    private BatchDataProvider _batchDataProvider;
}
