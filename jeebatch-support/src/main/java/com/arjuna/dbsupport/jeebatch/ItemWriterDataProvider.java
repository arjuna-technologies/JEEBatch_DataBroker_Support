/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.dbsupport.jeebatch;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.chunk.ItemWriter;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataProvider;

public class ItemWriterDataProvider<T> implements DataProvider<T>, ItemWriter
{
    private static final Logger logger = Logger.getLogger(ItemWriterDataProvider.class.getName());

    @Inject
    private JobContext jobContext;
    
    @Override
    public Serializable checkpointInfo()
        throws Exception
    {
        return null;
    }

    @Override
    public void open(Serializable checkpoint)
        throws Exception
    {
    }

    @Override
    public void writeItems(List<Object> items)
        throws Exception
    {
    }

    @Override
    public void close()
        throws Exception
    {
    }

    @Override
    public DataFlowNode getDataFlowNode()
    {
        return _dataFlowNode;
    }

    @Override
    public Collection<DataConsumer<T>> getDataConsumers()
    {
        return Collections.unmodifiableList(_dataConsumers);
    }

    @Override
    public void addDataConsumer(DataConsumer<T> dataConsumer)
    {
        _dataConsumers.add(dataConsumer);
    }

    @Override
    public void removeDataConsumer(DataConsumer<T> dataConsumer)
    {
        _dataConsumers.remove(dataConsumer);
    }

    @Override
    public void produce(T data)
    {
        for (DataConsumer<T> dataConsumer: _dataConsumers)
            dataConsumer.consume(this, data);
    }

    private DataFlowNode          _dataFlowNode;
    private List<DataConsumer<T>> _dataConsumers;
}
