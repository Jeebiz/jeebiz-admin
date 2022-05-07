/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.shadow.setup;

import java.util.function.Predicate;

public class RetryOnResultPredicate implements Predicate<Object> {

    @Override
    public boolean test(Object o) {
        return o == null ? true : false;
    }
}