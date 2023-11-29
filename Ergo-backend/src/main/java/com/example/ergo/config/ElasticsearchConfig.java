package com.example.ergo.config;

//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.json.jackson.JacksonJsonpMapper;
//import co.elastic.clients.transport.ElasticsearchTransport;
//import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 董冠男
 * @date 2023/11/23
 * 描述：
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchConfig {

//    private String hostname ="localhost";
//    private int port =9200;

//    @Bean
//    public ElasticsearchClient elasticsearchClient(){
//        RestClient client = RestClient.builder(new HttpHost(hostname,port,"http")).build();
//        ElasticsearchTransport transport = new RestClientTransport(client,new JacksonJsonpMapper());
//        return new ElasticsearchClient(transport);
//    }
    // 是否开启ES
    private Boolean open;

    // es host ip 地址（集群）
    private String hosts;

    // es用户名
    private String userName;

    // es密码
    private String password;

    // es 请求方式
    private String scheme;

    // es集群名称
    private String clusterName;

    // es 连接超时时间
    private int connectTimeOut;

    // es socket 连接超时时间
    private int socketTimeOut;

    // es 请求超时时间
    private int connectionRequestTimeOut;

    // es 最大连接数
    private int maxConnectNum;

    // es 每个路由的最大连接数
    private int maxConnectNumPerRoute;

    /**
     * 如果@Bean没有指定bean的名称，那么这个bean的名称就是方法名
     */
    @Bean(name = "restHighLevelClient")
    public RestHighLevelClient restHighLevelClient() {

        // 此处为单节点es
        String host = hosts.split(":")[0];
        String port = hosts.split(":")[1];
        HttpHost httpHost = new HttpHost(host, Integer.parseInt(port));

        // 构建连接对象
        RestClientBuilder builder = RestClient.builder(httpHost);

        // 设置用户名、密码
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

        // 连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(connectTimeOut);
            requestConfigBuilder.setSocketTimeout(socketTimeOut);
            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
            return requestConfigBuilder;
        });
        // 连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(maxConnectNum);
            httpClientBuilder.setMaxConnPerRoute(maxConnectNumPerRoute);
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            return httpClientBuilder;
        });

        return new RestHighLevelClient(builder);
    }

}
