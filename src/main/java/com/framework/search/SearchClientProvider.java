package com.framework.search;


import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class SearchClientProvider {
    private static final Logger LOG = LoggerFactory.getLogger(SearchClientProvider.class);

    @Value("${search.service.host}")
    private String host;

    private Client client;

    public Client singletonClient() {
        if (this.client != null) {
            return this.client;
        }

        try {
            String[] strings = host.split(":");
            String hostname = strings[0];
            int port = Integer.valueOf(strings[1]);
            InetSocketTransportAddress address = new InetSocketTransportAddress(InetAddress.getByName(hostname), port);
            Client client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(address);
            this.client = client;
            return  client;
        } catch (UnknownHostException e) {
            LOG.error("fail to create search client with: [{}]", host);
            throw new RuntimeException("fail to create search client", e);
        }
    }

    public void cleanup() {
        if (this.client != null) {
            LOG.debug("cleaning up search client.");
            this.client.close();
        }
    }
}
