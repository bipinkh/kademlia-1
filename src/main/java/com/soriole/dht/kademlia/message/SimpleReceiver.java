package com.soriole.dht.kademlia.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Default receiver if none other is called
 *
 * @author Joshua Kissoon
 * @created 20140202
 */
public class SimpleReceiver implements Receiver
{
    private static final Logger logger = LoggerFactory.getLogger(SimpleReceiver.class);

    @Override
    public void receive(Message incoming, int conversationId)
    {
        logger.info("\nConversation Id : {} \nReceived Message : {} ", conversationId,incoming);
    }

    @Override
    public void timeout(int conversationId) throws IOException
    {
        logger.info("\nSimpleReceiver message timeout for conversation id : " , conversationId);
    }
}
