package com.ramz.locationtracker.utils;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by munnazz on 29/11/15.
 */
public class MainEventBus {

    private static Bus instance = null;
    private MainEventBus()
    {
        instance = new Bus(ThreadEnforcer.ANY);
    }
    public static Bus getInstance()
    {
        if(instance == null)
        {
            instance = new Bus(ThreadEnforcer.ANY);
        }
        return instance;
    }
}
