/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.dbsupport.jeebatch;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataProvider;

public class BatchDataProvider implements DataProvider<Object>
{
    private static final Logger logger = Logger.getLogger(BatchDataProvider.class.getName());

    public BatchDataProvider(DataFlowNode dataFlowNode)
    {
        logger.log(Level.INFO, "BatchDataProvider.BatchDataProvider");

        _id            = UUID.randomUUID().toString();
        _dataFlowNode  = dataFlowNode;
        _dataConsumers = new LinkedList<DataConsumer<Object>>();
    }

    public String getId()
    {
        return _id;
    }
    
    @Override
    public DataFlowNode getDataFlowNode()
    {
        logger.log(Level.INFO, "BatchDataProvider.getDataFlowNode");

        return _dataFlowNode;
    }

    @Override
    public Collection<DataConsumer<Object>> getDataConsumers()
    {
        logger.log(Level.INFO, "BatchDataProvider.getDataConsumers");

        return Collections.unmodifiableList(_dataConsumers);
    }

    @Override
    public void addDataConsumer(DataConsumer<Object> dataConsumer)
    {
        logger.log(Level.INFO, "BatchDataProvider.addDataConsumer");

        _dataConsumers.add(dataConsumer);
    }

    @Override
    public void removeDataConsumer(DataConsumer<Object> dataConsumer)
    {
        logger.log(Level.INFO, "BatchDataProvider.removeDataConsumer");

        _dataConsumers.remove(dataConsumer);
    }

    @Override
    public void produce(Object data)
    {
        logger.log(Level.INFO, "BatchDataProvider.produce");

        for (DataConsumer<Object> dataConsumer: _dataConsumers)
            dataConsumer.consume(this, data);
    }

    public void writeItems(List<Object> items)
    {
        logger.log(Level.INFO, "BatchDataProvider.writeItems");

        for (Object item: items)
            produce(item);
    }

    private String                     _id;
    private DataFlowNode               _dataFlowNode;
    private List<DataConsumer<Object>> _dataConsumers;
}
