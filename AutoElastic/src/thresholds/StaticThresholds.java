/*
 * 02/07/2015
 * StaticThresholds
 * Author: viniciusfacco
 * - Implements static thresholds that not change
 */
package thresholds;

/**
 * Reviews
 * @author viniciusfacco
 * 28/09/2015 - viniciusfacco
 *            - removed methods recalculateLowerThreshold and recalculateUpperThreshold
 *            - added method resetThresholds
 */
public class StaticThresholds implements Thresholds{
    
    protected float upper_threshold;
    protected float lower_threshold;
    protected String objname;
    
    public StaticThresholds(float uppert, float lowert){
        upper_threshold = uppert;
        lower_threshold = lowert;
    }

    /**
     * With static threshold this method do nothing
     * @param load
     */
    @Override
    public void calculateThresholds(float load){}
    
    /**
     * StaticThresholds: do nothing.
     * LiveThresholds: set the lower threshold to (load/2).
     */
    @Override
    public void resetThresholds(){}
    
    /**
     * Return the current upper threshold
     * @return current current upper threshold
     */
    @Override
    public float getUpperThreshold() {
        return upper_threshold;
    }

    /**
     * Return the current lower threshold
     * @return current lower threshold
     */
    @Override
    public float getLowerThreshold() {
        return lower_threshold;
    }

    @Override
    public void reset(float uppert, float lowert) {
        upper_threshold = uppert;
        lower_threshold = lowert;
    }
    
}
