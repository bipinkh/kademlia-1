package com.soriole.dht.kademlia.simulations;

import com.soriole.dht.kademlia.GetParameter;
import com.soriole.dht.kademlia.JKademliaNode;
import com.soriole.dht.kademlia.KademliaStorageEntry;
import com.soriole.dht.kademlia.exceptions.ContentNotFoundException;
import com.soriole.dht.kademlia.node.KademliaId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Testing sending and receiving content between 2 Nodes on a network
 *
 * @author Joshua Kissoon
 * @since 20140224
 */
public class ContentUpdatingTest {
    private static final Logger logger = LoggerFactory.getLogger(ContentUpdatingTest.class);

    public static void main(String[] args) {
        try {
            /* Setting up 2 Kad networks */
            JKademliaNode kad1 = new JKademliaNode("JoshuaK", new KademliaId("ASF45678947584567467"), 7574);
            logger.info("Created Node Kad 1: " + kad1.getNode().getNodeId());
            JKademliaNode kad2 = new JKademliaNode("Crystal", new KademliaId("ASERTKJDHGVHERJHGFLK"), 7572);
            logger.info("Created Node Kad 2: " + kad2.getNode().getNodeId());
            kad2.bootstrap(kad1.getNode());

            /* Lets create the content and share it */
            DHTContentImpl c = new DHTContentImpl(kad2.getOwnerId(), "Some Data");
            kad2.put(c);

            /* Lets retrieve the content */
            logger.info("Retrieving Content");
            GetParameter gp = new GetParameter(c.getKey(), DHTContentImpl.TYPE, c.getOwnerId());

            logger.info("Get Parameter: " + gp);
            KademliaStorageEntry conte = kad2.get(gp);
            logger.info("Content Found: " + new DHTContentImpl().fromSerializedForm(conte.getContent()));
            logger.info("Content Metadata: " + conte.getContentMetadata());

            /* Lets update the content and put it again */
            c.setData("Some New Data");
            kad2.put(c);

            /* Lets retrieve the content */
            logger.info("Retrieving Content Again");
            conte = kad2.get(gp);
            logger.info("Content Found: " + new DHTContentImpl().fromSerializedForm(conte.getContent()));
            logger.info("Content Metadata: " + conte.getContentMetadata());

        } catch (IOException | ContentNotFoundException e) {
            e.printStackTrace();
        }
    }
}
