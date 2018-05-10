package org.ipfsbox.battery.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TEST {

    public static Log log = LogFactory.getLog(TEST.class);

    public static void main(String args[]){

        IPFSCluster ipfsCluster = new IPFSCluster("127.0.0.1", 9094);
        try {

            log.info("ipfsCluster id :" + ipfsCluster.id());
            log.info("ipfsCluster version:" + ipfsCluster.version());
            log.info("ipfsCluster Pins :" +ipfsCluster.pins.add("QmdJbpQWDKsgwmiSw7tTvL42Wy6M3YYz1DnnYK2bGAUzLR"));
            log.info("ipfsCluster Pins :" +ipfsCluster.pins.ls("QmdJbpQWDKsgwmiSw7tTvL42Wy6M3YYz1DnnYK2bGAUzLR"));
            log.info("ipfsCluster Pins :" +ipfsCluster.pins.ls());
            log.info("ipfsCluster Pins :" +ipfsCluster.pins.rm("QmdJbpQWDKsgwmiSw7tTvL42Wy6M3YYz1DnnYK2bGAUzLR"));
            log.info("ipfsCluster Pins :" +ipfsCluster.pins.ls());
            log.info("ipfsCluster Peers :" + ipfsCluster.peers.ls());
            log.info("ipfsCluster Sync :" + ipfsCluster.sync.sync("QmdJbpQWDKsgwmiSw7tTvL42Wy6M3YYz1DnnYK2bGAUzLR"));
            log.info("ipfsCluster Sync :" + ipfsCluster.sync.sync());
//            log.info("ipfsCluster Recover :" + ipfsCluster.recover.recover("QmdJbpQWDKsgwmiSw7tTvL42Wy6M3YYz1DnnYK2bGAUzLR"));
//            log.info("ipfsCluster Recover :" + ipfsCluster.recover.recover());
//            log.info("ipfsCluster Allocation :" + ipfsCluster.allocation.ls());
//            log.info("ipfsCluster Allocation :" + ipfsCluster.allocation.ls("QmdJbpQWDKsgwmiSw7tTvL42Wy6M3YYz1DnnYK2bGAUzLR"));
            log.info("ipfsCluster Health :" + ipfsCluster.health.graph());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
