/*
 * 02/07/2015
 * LiveThresholds
 * Author: viniciusfacco
 * - Extends static thresholds, but here thresholds values are modified during the execution
 */
package thresholds;

import static autoelastic.AutoElastic.gera_log;

/**
 * Reviews
 * @author viniciusfacco
 */
public class LiveThresholds extends StaticThresholds{
    
    private int counter;
    private float last_load;
    private float load_variation;
    private float live_value;

    public LiveThresholds(float uppert, float lowert) {
        super(uppert, lowert);
        counter = 0;
        last_load = 0;
        live_value = 0;
        objname = "thresholds.FullThresholds";
    }
    
    /**
     * Adapt the current thresholds considering the system load
     * @param load - current system load
     */
    @Override
    public void calculateThresholds(float load){
        if (counter >= 1){ //the algorithm needs at least two observations to calculate load variations
            load_variation = load - last_load;
            live_value = (float) (live_value * 0.5 + load_variation * 0.5); //exponential moving average
            super.lower_threshold = calculateThreshold(super.lower_threshold);
            super.upper_threshold = calculateThreshold(super.upper_threshold);
        } else {
            counter++;
        }
        //gera_log(objname, "Main|LiveThresholds|calculateThresholds: Threshold inferior/superior = " + super.lower_threshold + "/" + super.upper_threshold);
        last_load = load;
    }
    
    @Override
    public void recalculateLowerThreshold(float load){
        super.lower_threshold = load / 2;
    }
    
    @Override
    public void recalculateUpperThreshold(float load){
        super.upper_threshold = 1 - ((1 - load) / 2);
    }
    
    @Override
    public void reset(float uppert, float lowert) {
        upper_threshold = uppert;
        lower_threshold = lowert;
        counter = 0;
        last_load = 0;
        live_value = 0;
    }
    
    // return a new value for the threshold using the value "live_value" to adapt the threshold
    private float calculateThreshold(float threshold){
        if ((threshold - live_value) < 0){
            return 0;
        } else if ((threshold - live_value) > 1){
            return 1;
        } else{
            return (threshold - live_value);
        }
    }
    
}
