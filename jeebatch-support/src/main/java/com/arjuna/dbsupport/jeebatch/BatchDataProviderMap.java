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

public class BatchDataProviderMap
{
    private static final Logger logger = Logger.getLogger(BatchDataProviderMap.class.getName());

    public static final String ID_PROPERTYNAME = "dataprovider_id";

    public BatchDataProviderMap()
    {
        _batchDataProviderMap = new HashMap<String, BatchDataProvider>();
    }

    public void add(BatchDataProvider batchDataProvider)
    {
        _batchDataProviderMap.put(batchDataProvider.getId(), batchDataProvider);
    }

    public BatchDataProvider get(String id)
    {
        return _batchDataProviderMap.get(id);
    }

    public boolean remove(BatchDataProvider batchDataProvider)
    {
        return (_batchDataProviderMap.remove(batchDataProvider.getId()) != null);
    }

    @Produces
    @BatchProperty
    public BatchDataProviderMap getBatchDataProviderMap(final InjectionPoint injectionPoint)
    {
        logger.log(Level.FINE, "BatchDataProviderMap.getBatchDataProviderMap");

        synchronized (this)
        {
            if (_instance == null)
                _instance = new BatchDataProviderMap();
        }

        logger.log(Level.FINE, "BatchDataProviderMap.getBatchDataProviderMap: returns = " + _instance);

        return _instance;
    }
    
    private static BatchDataProviderMap _instance;

    private Map<String, BatchDataProvider> _batchDataProviderMap;
}
