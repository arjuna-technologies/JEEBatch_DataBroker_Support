/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.dbsupport.jeebatch;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;

import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataProvider;
import com.arjuna.databroker.data.DataProcessor;
import com.arjuna.databroker.data.jee.annotation.PostConfig;
import com.arjuna.databroker.data.jee.annotation.PostCreated;
import com.arjuna.databroker.data.jee.annotation.PostRecovery;

public class BatchDataService implements DataProcessor
{
    private static final Logger logger = Logger.getLogger(BatchDataService.class.getName());

    public static final String JOBID_PROPERTYNAME = "Job ID";

    public BatchDataService()
    {
        logger.log(Level.FINE, "BatchDataService");
    }

    public BatchDataService(String name, Map<String, String> properties)
    {
        logger.log(Level.FINE, "BatchDataService: " + name + ", " + properties);

        _name              = name;
        _properties        = properties;
    }

    @PostCreated
    @PostRecovery
    @PostConfig
    public void setup()
    {
        _batchDataConsumer = new BatchDataConsumer(this);
        _batchDataProvider = new BatchDataProvider(this);

        BatchDataConsumerMap.getBatchDataConsumerMap().add(_batchDataConsumer);
        BatchDataProviderMap.getBatchDataProviderMap().add(_batchDataProvider);

        JobOperator jobOperator = BatchRuntime.getJobOperator();

        Properties jobParameters = new Properties();
        jobParameters.setProperty(BatchDataConsumerMap.ID_PROPERTYNAME, _batchDataConsumer.getId());
        jobParameters.setProperty(BatchDataProviderMap.ID_PROPERTYNAME, _batchDataProvider.getId());

        long execId = jobOperator.start(_properties.get(JOBID_PROPERTYNAME), jobParameters);

        logger.log(Level.FINE, "BatchDataService: " + execId);
    }

    @Override
    public DataFlow getDataFlow()
    {
        return _dataFlow;
    }

    @Override
    public void setDataFlow(DataFlow dataFlow)
    {
        _dataFlow = dataFlow;
    }

    @Override
    public String getName()
    {
        return _name;
    }

    @Override
    public void setName(String name)
    {
        _name = name;
    }

    @Override
    public Map<String, String> getProperties()
    {
        return Collections.unmodifiableMap(_properties);
    }

    @Override
    public void setProperties(Map<String, String> properties)
    {
        _properties = properties;
    }

    @Override
    public Collection<Class<?>> getDataConsumerDataClasses()
    {
        Set<Class<?>> dataConsumerDataClasses = new HashSet<Class<?>>();

        dataConsumerDataClasses.add(Object.class);

        return dataConsumerDataClasses;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> DataConsumer<T> getDataConsumer(Class<T> dataClass)
    {
        if (dataClass == Object.class)
            return (DataConsumer<T>) _batchDataConsumer;
        else
            return null;
    }

    @Override
    public Collection<Class<?>> getDataProviderDataClasses()
    {
        Set<Class<?>> dataProviderDataClasses = new HashSet<Class<?>>();

        dataProviderDataClasses.add(Object.class);

        return dataProviderDataClasses;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> DataProvider<T> getDataProvider(Class<T> dataClass)
    {
        if (dataClass == Object.class)
            return (DataProvider<T>) _batchDataProvider;
        else
            return null;
    }

    private DataFlow            _dataFlow;
    private String              _name;
    private Map<String, String> _properties;
    private BatchDataConsumer   _batchDataConsumer;
    private BatchDataProvider   _batchDataProvider;
}
