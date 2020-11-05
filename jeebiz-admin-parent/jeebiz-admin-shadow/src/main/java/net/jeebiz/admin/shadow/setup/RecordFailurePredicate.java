/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.setup;

import java.util.function.Predicate;

import net.jeebiz.boot.api.exception.BizCheckedException;

public class RecordFailurePredicate implements Predicate<Throwable> {

    @Override
    public boolean test(Throwable throwable) {
        if (throwable.getCause() instanceof BizCheckedException) return false;
        else return true;
    }
    
}
