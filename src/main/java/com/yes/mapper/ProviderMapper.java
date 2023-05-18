package com.yes.mapper;

import com.yes.HJHSF;

/**
 * Created by huangJin on 2023/5/16.
 */

@HJHSF(serviceName = "testService", type = HJHSF.Type.PROVIDER)
public interface ProviderMapper {
    String test();
}
