package io.pivotal.demo.util;

import io.pivotal.demo.model.Customer;
import org.apache.geode.cache.*;
import org.apache.geode.cache.util.CacheListenerAdapter;
import org.apache.geode.internal.logging.LogService;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class RegionUpdateCacheListener<Integer, Customer> extends CacheListenerAdapter<Integer, Customer> implements Declarable {

    Logger logger = LogService.getLogger();

    public void init(Properties properties){
        //nothing to do
    }

    @Override
    public void afterCreate(EntryEvent<Integer, Customer> event) {
        logger.info("Entered Create Block");
        Cache cache = CacheFactory.getAnyInstance();
        logger.info("Entered Here");

        Region<Integer, Customer> customerUpdate = cache.getRegion("customer");
        logger.info("Region accessed");
        if (customerUpdate.containsKey(event.getKey())){
            customerUpdate.replace(event.getKey(),event.getNewValue());

        } else {

            customerUpdate.put(event.getKey(), event.getNewValue());
        }
        logger.info("This is the end");
    }

    @Override
    public void afterUpdate(EntryEvent<Integer, Customer> event) {
        logger.info("Entered Update Block");

       Cache cache = CacheFactory.getAnyInstance();
        logger.info("Update Entered Here");

        Region<Integer, Customer> customerUpdate = cache.getRegion("customer");
        logger.info("Update Region accessed");
  //      if (customerUpdate.containsKey(event.getKey())){
            customerUpdate.replace(event.getKey(),event.getNewValue());

    //    } else {

      //      customerUpdate.put(event.getKey(), event.getNewValue());
       // }
        logger.info("Update This is the end");
    }


}
