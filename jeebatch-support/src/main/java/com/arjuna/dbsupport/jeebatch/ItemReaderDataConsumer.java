/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.dbsupport.jeebatch;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.chunk.ItemReader;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataProvider;

public class ItemReaderDataConsumer<T> implements DataConsumer<T>, ItemReader
{
    private static final Logger logger = Logger.getLogger(ItemReaderDataConsumer.class.getName());

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
    public Object readItem()
        throws Exception
    {
        return null;
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
    public void consume(DataProvider<T> dataProvider, T data)
    {
    }

    private DataFlowNode _dataFlowNode;
}
