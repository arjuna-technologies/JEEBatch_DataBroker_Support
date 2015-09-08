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
import com.arjuna.databroker.data.connector.ObservableDataProvider;
import com.arjuna.databroker.data.connector.ObserverDataConsumer;

public class BatchDataProvider implements ObservableDataProvider<Object>
{
    private static final Logger logger = Logger.getLogger(BatchDataProvider.class.getName());

    public BatchDataProvider(DataFlowNode dataFlowNode)
    {
        logger.log(Level.FINE, "BatchDataProvider.BatchDataProvider");

        _id            = UUID.randomUUID().toString();
        _dataFlowNode  = dataFlowNode;
        _dataConsumers = new LinkedList<ObserverDataConsumer<Object>>();
    }

    public String getId()
    {
        return _id;
    }

    @Override
    public DataFlowNode getDataFlowNode()
    {
        logger.log(Level.FINE, "BatchDataProvider.getDataFlowNode");

        return _dataFlowNode;
    }

    @Override
    public Collection<ObserverDataConsumer<Object>> getDataConsumers()
    {
        logger.log(Level.FINE, "BatchDataProvider.getDataConsumers");

        return Collections.unmodifiableList(_dataConsumers);
    }

    @Override
    public void addDataConsumer(ObserverDataConsumer<Object> dataConsumer)
    {
        logger.log(Level.FINE, "BatchDataProvider.addDataConsumer");

        _dataConsumers.add(dataConsumer);
    }

    @Override
    public void removeDataConsumer(ObserverDataConsumer<Object> dataConsumer)
    {
        logger.log(Level.FINE, "BatchDataProvider.removeDataConsumer");

        _dataConsumers.remove(dataConsumer);
    }

    @Override
    public void produce(Object data)
    {
        logger.log(Level.FINE, "BatchDataProvider.produce");

        for (ObserverDataConsumer<Object> dataConsumer: _dataConsumers)
            dataConsumer.consume(this, data);
    }

    public void writeItems(List<Object> items)
    {
        logger.log(Level.FINE, "BatchDataProvider.writeItems");

        for (Object item: items)
            produce(item);
    }

    private String                             _id;
    private DataFlowNode                       _dataFlowNode;
    private List<ObserverDataConsumer<Object>> _dataConsumers;
}
