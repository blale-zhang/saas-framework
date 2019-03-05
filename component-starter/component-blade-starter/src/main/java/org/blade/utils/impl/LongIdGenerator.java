package org.blade.utils.impl;

import org.blade.utils.IdGenerator;
import org.blade.utils.SnowFlake;

public class LongIdGenerator implements IdGenerator<Long> {

   private SnowFlake snowFlake = new SnowFlake(2, 3);

    @Override
    public Long getNextId() {
        return snowFlake.nextId();
    }
}
