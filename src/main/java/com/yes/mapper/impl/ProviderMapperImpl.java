package com.yes.mapper.impl;

import com.yes.mapper.ProviderMapper;
import org.springframework.stereotype.Component;

/**
 * Created by huangJin on 2023/5/18.
 */

@Component
public class ProviderMapperImpl implements ProviderMapper {
    @Override
    public String test() {
        return "Hello HJHSF";
    }
}
