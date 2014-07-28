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

    public static final String ID_PROPERTYNAME = "dataconsumer_id";

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
    public static BatchDataConsumerMap getBatchDataConsumerMap(final InjectionPoint injectionPoint)
    {
        logger.log(Level.FINE, "BatchDataConsumerMap.getBatchDataConsumerMap");

        synchronized (_syncObject)
        {
            if (_instance == null)
                _instance = new BatchDataConsumerMap();
        }

        logger.log(Level.FINE, "BatchDataConsumerMap.getBatchDataConsumerMap: returns = " + _instance);

        return _instance;
    }

    private static Object               _syncObject = new Object();
    private static BatchDataConsumerMap _instance;

    private Map<String, BatchDataConsumer> _batchDataConsumerMap;
}
