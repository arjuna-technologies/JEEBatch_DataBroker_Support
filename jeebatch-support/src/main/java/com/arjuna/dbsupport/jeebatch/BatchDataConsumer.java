/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.dbsupport.jeebatch;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataProvider;
import com.arjuna.databroker.data.DataFlowNode;

public class BatchDataConsumer implements DataConsumer<Object>
{
    private static final Logger logger = Logger.getLogger(BatchDataConsumer.class.getName());

    public BatchDataConsumer(DataFlowNode dataFlowNode)
    {
        logger.log(Level.FINE, "BatchDataConsumer.BatchDataConsumer");

        _id            = UUID.randomUUID().toString();
        _dataFlowNode  = dataFlowNode;
        _blockingQueue = new LinkedBlockingQueue<Object>();
    }

    public String getId()
    {
        return _id;
    }

    @Override
    public DataFlowNode getDataFlowNode()
    {
        logger.log(Level.FINE, "BatchDataConsumer.getDataFlowNode");

        return _dataFlowNode;
    }

    @Override
    public void consume(DataProvider<Object> dataProvider, Object data)
    {
        logger.log(Level.FINE, "BatchDataConsumer.consume");

        try
        {
            _blockingQueue.put(data);
        }
        catch (InterruptedException interruptedException)
        {
            logger.log(Level.WARNING, "Problem while putting item into DataConsumerItemReader", interruptedException);
        }
    }

    public Object readItem()
        throws InterruptedException
    {
        logger.log(Level.FINE, "BatchDataConsumer.consume");

        return _blockingQueue.take();
    }

    private String                _id;
    private DataFlowNode          _dataFlowNode;
    private BlockingQueue<Object> _blockingQueue;
}
