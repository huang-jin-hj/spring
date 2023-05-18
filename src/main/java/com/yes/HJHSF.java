package com.yes;

/**
 * Created by huangJin on 2023/5/17.
 */
public @interface HJHSF {
    String serviceName();
    Type type();


    enum Type{
        CONSUMER,
        PROVIDER
        ;
    }
}
