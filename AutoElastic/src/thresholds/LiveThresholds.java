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
 * 28/09/2015 - viniciusfacco
 *            - changed the calculateThresholds implementation
 *            - removed methods recalculateLowerThreshold and recalculateUpperThreshold
 *            - added method resetThresholds
 */
public class LiveThresholds extends StaticThresholds{
    
    private int counter;
    private float last_load;
    private float load_variation;
    private float live_value;
    private float current_upper_threshold;
    private float current_lower_threshold;

    public LiveThresholds(float uppert, float lowert) {
        super(uppert, lowert);
        current_upper_threshold = uppert;
        current_lower_threshold = lowert;
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
        counter++;
        if (counter > 1){ //the algorithm needs at least two observations to calculate load variations
            load_variation = load - last_load;
            live_value = (float) (live_value * 0.5 + load_variation * 0.5); //exponential moving average
            if (counter > 5){//the adaptation will occur if we have more than 6 observations
                if (live_value > 0){//the load are increasing
                    current_upper_threshold = calculateThreshold(current_upper_threshold);
                } else {//the load are decreasing
                    current_lower_threshold = calculateThreshold(current_lower_threshold);
                }
            }
        }
        //gera_log(objname, "Main|LiveThresholds|calculateThresholds: Threshold inferior/superior = " + super.lower_threshold + "/" + super.upper_threshold);
        last_load = load;
    }
    
    @Override
    public void resetThresholds(){
        current_lower_threshold = super.lower_threshold;
        current_upper_threshold = super.upper_threshold;
    }
    
    /**
     * Return the current upper threshold
     * @return current current upper threshold
     */
    @Override
    public float getUpperThreshold() {
        return current_upper_threshold;
    }

    /**
     * Return the current lower threshold
     * @return current lower threshold
     */
    @Override
    public float getLowerThreshold() {
        return current_lower_threshold;
    }
    
    @Override
    public void reset(float uppert, float lowert) {
        upper_threshold = uppert;
        lower_threshold = lowert;
        current_upper_threshold = uppert;
        current_lower_threshold = lowert;
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
