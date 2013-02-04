package com.ehaqui.ehcore.tps;

import java.util.LinkedList;


public class TPS implements Runnable
{
    public static final LinkedList<Float> loggedTps     = new LinkedList<Float>();
    public static int                     loggedTpsSize = 60;
    
    public static float                   tps           = 20.0F;
    
    public static float                   timeSpend     = 0;
    
    public static long                    lastCall      = getMillis() - 3000L;
    public static int                     interval      = 40;
    
    public void addTps(Float tps)
    {
        if((tps != null) && (tps.floatValue() <= 20.0F))
        {
            loggedTps.add(tps);
        }
        if(loggedTps.size() > loggedTpsSize)
            loggedTps.poll();
    }
    
    public static final float getTPS()
    {
        return tps;
    }
    
    public static final float getAverageTPS()
    {
        float amount = 0.0F;
        for (Float f : loggedTps)
        {
            if(f != null)
            {
                amount += f.floatValue();
            }
        }
        return amount / loggedTps.size();
    }
    
    public static final float getTimeTPS()
    {
        return timeSpend;
    }
    
    private static long getMillis()
    {
        return System.currentTimeMillis();
    }
    
    public final void run()
    {
        long currentTime = getMillis();
        long spentTime = (currentTime - lastCall) / 1000L;
        timeSpend = spentTime;
        
        if(spentTime == 0L)
        {
            spentTime = 1L;
        }
        
        float calculatedTps = (float) (interval / spentTime);
        
        if(calculatedTps > 20.0F)
        {
            calculatedTps = 20.0F;
        }
        
        setTps(calculatedTps);
        addTps(calculatedTps);
        
        lastCall = getMillis();
    }
    
    private void setTps(float newTps)
    {
        tps = newTps;
    }
}