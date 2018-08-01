package io.pivotal.demo.app;

import io.pivotal.gemfire.gpdb.operations.OperationException;
import io.pivotal.gemfire.gpdb.service.GpdbService;
import io.pivotal.gemfire.gpdb.service.ImportConfiguration;
import io.pivotal.gemfire.gpdb.service.ImportResult;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionExistsException;
import org.apache.geode.cache.RegionService;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;

public class ImportData {
    public static void main(String[] args) throws OperationException {

        ClientCache cache = new ClientCacheFactory()
                .addPoolLocator("172.16.152.100", 10334).set("log-level", "WARN").create();


        Region region = null;

        ClientRegionFactory<Object, Object> regionFactory = cache
                .createClientRegionFactory(ClientRegionShortcut.PROXY);
        try {
            region = regionFactory.create("customerUpdate");
        } catch (RegionExistsException e) {
            region = cache.getRegion("customerUpdate");
        }


        ImportConfiguration importConfiguration = ImportConfiguration.builder(region)
                .build();
        ImportResult result = GpdbService.importRegion(importConfiguration);
        System.out.println(result.getImportedCount());
    }
}
