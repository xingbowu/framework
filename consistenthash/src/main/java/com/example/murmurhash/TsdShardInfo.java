package com.example.murmurhash;

import com.example.md5hashshard.Const;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by xingbowu on 17/4/12.
 */
public class TsdShardInfo extends ShardInfo<TsdInstance> {

    private static final String REDISS = "rediss";
    private int connectionTimeout;
    private int soTimeout;
    private String host;
    private int port;
    private String password = null;
    private String name = null;
    // Default Redis DB
    private int db = 0;
    private boolean ssl;
    private SSLSocketFactory sslSocketFactory;
    private SSLParameters sslParameters;
    private HostnameVerifier hostnameVerifier;

    public TsdShardInfo(String host) {
        super(Sharded.DEFAULT_WEIGHT);
        this.host = host;
        this.port = Const.DEFAULT_PORT;
    }

    public TsdShardInfo(String host, String name) {
        this(host, Const.DEFAULT_PORT, name);
    }

    public TsdShardInfo(String host, int port) {
        this(host, port, 2000);
    }

    public TsdShardInfo(String host, int port, boolean ssl) {
        this(host, port, 2000, 2000, Sharded.DEFAULT_WEIGHT, ssl);
    }

    public TsdShardInfo(String host, int port, boolean ssl, SSLSocketFactory sslSocketFactory,
                          SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this(host, port, 2000, 2000, Sharded.DEFAULT_WEIGHT, ssl, sslSocketFactory, sslParameters,
                hostnameVerifier);
    }

    public TsdShardInfo(String host, int port, String name) {
        this(host, port, 2000, name);
    }

    public TsdShardInfo(String host, int port, String name, boolean ssl) {
        this(host, port, 2000, name, ssl);
    }

    public TsdShardInfo(String host, int port, String name, boolean ssl, SSLSocketFactory sslSocketFactory,
                          SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this(host, port, 2000, name, ssl, sslSocketFactory, sslParameters,
                hostnameVerifier);
    }

    public TsdShardInfo(String host, int port, int timeout) {
        this(host, port, timeout, timeout, Sharded.DEFAULT_WEIGHT);
    }

    public TsdShardInfo(String host, int port, int timeout, boolean ssl) {
        this(host, port, timeout, timeout, Sharded.DEFAULT_WEIGHT, ssl);
    }

    public TsdShardInfo(String host, int port, int timeout, boolean ssl,
                          SSLSocketFactory sslSocketFactory, SSLParameters sslParameters,
                          HostnameVerifier hostnameVerifier) {
        this(host, port, timeout, timeout, Sharded.DEFAULT_WEIGHT, ssl, sslSocketFactory,
                sslParameters, hostnameVerifier);
    }

    public TsdShardInfo(String host, int port, int timeout, String name) {
        this(host, port, timeout, timeout, Sharded.DEFAULT_WEIGHT);
        this.name = name;
    }

    public TsdShardInfo(String host, int port, int timeout, String name, boolean ssl) {
        this(host, port, timeout, timeout, Sharded.DEFAULT_WEIGHT);
        this.name = name;
        this.ssl = ssl;
    }

    public TsdShardInfo(String host, int port, int timeout, String name, boolean ssl,
                          SSLSocketFactory sslSocketFactory, SSLParameters sslParameters,
                          HostnameVerifier hostnameVerifier) {
        this(host, port, timeout, timeout, Sharded.DEFAULT_WEIGHT);
        this.name = name;
        this.ssl = ssl;
        this.sslSocketFactory = sslSocketFactory;
        this.sslParameters = sslParameters;
        this.hostnameVerifier = hostnameVerifier;
    }

    public TsdShardInfo(String host, int port, int connectionTimeout, int soTimeout, int weight) {
        super(weight);
        this.host = host;
        this.port = port;
        this.connectionTimeout = connectionTimeout;
        this.soTimeout = soTimeout;
    }

    public TsdShardInfo(String host, int port, int connectionTimeout, int soTimeout, int weight,
                          boolean ssl) {
        super(weight);
        this.host = host;
        this.port = port;
        this.connectionTimeout = connectionTimeout;
        this.soTimeout = soTimeout;
        this.ssl = ssl;
    }

    public TsdShardInfo(String host, int port, int connectionTimeout, int soTimeout, int weight,
                          boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters,
                          HostnameVerifier hostnameVerifier) {
        super(weight);
        this.host = host;
        this.port = port;
        this.connectionTimeout = connectionTimeout;
        this.soTimeout = soTimeout;
        this.ssl = ssl;
        this.sslSocketFactory = sslSocketFactory;
        this.sslParameters = sslParameters;
        this.hostnameVerifier = hostnameVerifier;
    }

    public TsdShardInfo(String host, String name, int port, int timeout, int weight) {
        super(weight);
        this.host = host;
        this.name = name;
        this.port = port;
        this.connectionTimeout = timeout;
        this.soTimeout = timeout;
    }

    public TsdShardInfo(String host, String name, int port, int timeout, int weight,
                          boolean ssl) {
        super(weight);
        this.host = host;
        this.name = name;
        this.port = port;
        this.connectionTimeout = timeout;
        this.soTimeout = timeout;
        this.ssl = ssl;
    }

    public TsdShardInfo(String host, String name, int port, int timeout, int weight,
                          boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters,
                          HostnameVerifier hostnameVerifier) {
        super(weight);
        this.host = host;
        this.name = name;
        this.port = port;
        this.connectionTimeout = timeout;
        this.soTimeout = timeout;
        this.ssl = ssl;
        this.sslSocketFactory = sslSocketFactory;
        this.sslParameters = sslParameters;
        this.hostnameVerifier = hostnameVerifier;
    }

    public String toString() {
        return host + ":" + port + "*" + getWeight();
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String auth) {
        this.password = auth;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getDb() {
        return db;
    }

    public boolean getSsl() {
        return ssl;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public SSLParameters getSslParameters() {
        return sslParameters;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    @Override
    public TsdInstance createResource() {
        return new TsdInstance(this);
    }

}
