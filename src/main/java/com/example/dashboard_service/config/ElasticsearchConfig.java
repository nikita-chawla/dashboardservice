package com.example.dashboard_service.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;

@Configuration
public class ElasticsearchConfig {

    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUri;

    @Value("${spring.elasticsearch.username}")
    private String username;

    @Value("${spring.elasticsearch.password}")
    private String password;

    @Bean
    public RestHighLevelClient restHighLevelClient() throws Exception {
        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));

        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(new TrustStrategy() {
                    @Override
                    public boolean isTrusted(X509Certificate[] chain, String authType) {
                        return true;
                    }
                }).build();

        RestClientBuilder builder = RestClient.builder(
                        HttpHost.create(elasticsearchUri))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder
                                .setDefaultCredentialsProvider(credentialsProvider)
                                .setSSLContext(sslContext)
                                .setDefaultIOReactorConfig(IOReactorConfig.custom()
                                        .setConnectTimeout(5000)
                                        .setSoTimeout(60000)
                                        .build());
                    }
                });

        return new RestHighLevelClient(builder);
    }
}
