/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.dbsupport.jeebatch;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.BatchProperty;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class BatchDataConsumerMap
{
    private static final Logger logger = Logger.getLogger(BatchDataConsumerMap.class.getName());

    public static final String ID_PROPERTYNAME = "DataConsumerId";

    public BatchDataConsumerMap()
    {
        _batchDataConsumerMap = new HashMap<String, BatchDataConsumer>();
    }
    
    public void add(BatchDataConsumer batchDataConsumer)
    {
        _batchDataConsumerMap.put(batchDataConsumer.getId(), batchDataConsumer);
    }

    public BatchDataConsumer get(String id)
    {
        return _batchDataConsumerMap.get(id);
    }

    public boolean remove(BatchDataConsumer batchDataConsumer)
    {
        return (_batchDataConsumerMap.remove(batchDataConsumer.getId()) != null);
    }

    @Produces
    @BatchProperty
    public BatchDataConsumerMap getBatchDataConsumerMap(final InjectionPoint injectionPoint)
    {
        logger.log(Level.INFO, "BatchDataConsumerMap.getBatchDataConsumerMap");

        if (_instance == null)
            _instance = new BatchDataConsumerMap();

        return _instance;
    }

    private static BatchDataConsumerMap _instance;

    private Map<String, BatchDataConsumer> _batchDataConsumerMap;
}
