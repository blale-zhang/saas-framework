package org.blade.utils;

import org.blade.utils.impl.LongIdGenerator;

/**
 *
 */
public class IdGenUtils {


   private static final IdGenerator<Long> idGenerator = new LongIdGenerator();

    public static Long getNextLongId(){
        return idGenerator.getNextId();
    }

}
