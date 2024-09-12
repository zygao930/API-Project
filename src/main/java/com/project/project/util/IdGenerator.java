package com.project.project.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    public static String generateId(String entity){
        return entity + UUID.randomUUID().toString();
    }
}
