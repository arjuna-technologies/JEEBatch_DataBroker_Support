/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.dbsupport.jeebatch;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataProvider;

@Named
public class BatchDataProvider<T> implements DataProvider<T>
{
    private static final Logger logger = Logger.getLogger(BatchDataProvider.class.getName());

    public BatchDataProvider(DataFlowNode dataFlowNode)
    {
        _dataFlowNode  = dataFlowNode;
        _dataConsumers = new LinkedList<DataConsumer<T>>();
    }

    @Override
    public DataFlowNode getDataFlowNode()
    {
        logger.log(Level.INFO, "BatchDataProvider.getDataFlowNode");

        return _dataFlowNode;
    }

    @Override
    public Collection<DataConsumer<T>> getDataConsumers()
    {
        logger.log(Level.INFO, "BatchDataProvider.getDataConsumers");

        return Collections.unmodifiableList(_dataConsumers);
    }

    @Override
    public void addDataConsumer(DataConsumer<T> dataConsumer)
    {
        logger.log(Level.INFO, "BatchDataProvider.addDataConsumer");

        _dataConsumers.add(dataConsumer);
    }

    @Override
    public void removeDataConsumer(DataConsumer<T> dataConsumer)
    {
        logger.log(Level.INFO, "BatchDataProvider.removeDataConsumer");

        _dataConsumers.remove(dataConsumer);
    }

    @Override
    public void produce(T data)
    {
        logger.log(Level.INFO, "BatchDataProvider.produce");

        for (DataConsumer<T> dataConsumer: _dataConsumers)
            dataConsumer.consume(this, data);
    }

    private DataFlowNode          _dataFlowNode;
    private List<DataConsumer<T>> _dataConsumers;
}
