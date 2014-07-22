/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.dbsupport.jeebatch;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataProvider;
import com.arjuna.databroker.data.DataFlowNode;

@Named
public class BatchDataConsumer<T> implements DataConsumer<T>
{
    private static final Logger logger = Logger.getLogger(BatchDataConsumer.class.getName());

    public BatchDataConsumer(DataFlowNode dataFlowNode)
    {
        logger.log(Level.INFO, "BatchDataConsumer.BatchDataConsumer");

        _dataFlowNode = dataFlowNode;
    }

    @Override
    public DataFlowNode getDataFlowNode()
    {
        logger.log(Level.INFO, "BatchDataConsumer.getDataFlowNode");

        return _dataFlowNode;
    }

    @Override
    public void consume(DataProvider<T> dataProvider, T data)
    {
        logger.log(Level.INFO, "BatchDataConsumer.consume");
    }

    private DataFlowNode _dataFlowNode;
}
