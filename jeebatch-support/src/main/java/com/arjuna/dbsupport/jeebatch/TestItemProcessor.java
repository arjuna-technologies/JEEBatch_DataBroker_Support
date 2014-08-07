/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.dbsupport.jeebatch;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.chunk.ItemProcessor;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class TestItemProcessor implements ItemProcessor
{
    private static final Logger logger = Logger.getLogger(TestItemProcessor.class.getName());

    @Inject
    private JobContext jobContext;

    @Override
    public Object processItem(Object item)
        throws Exception
    {
        logger.log(Level.INFO, "TestItemProcessor.processItem: " + jobContext.getJobName());
        logger.log(Level.INFO, "                   item:       " + item);

        return item;
    }
}