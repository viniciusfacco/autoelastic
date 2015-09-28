/*
 * 02/07/2015
 * Thresholds
 * Author: viniciusfacco
 * - Interface used to define the methods to implement thresholds
 */
package thresholds;

/**
 *
 * @author viniciusfacco
 * 28/09/2015 - viniciusfacco
 *            - removed methods recalculateLowerThreshold and recalculateUpperThreshold
 *            - added method resetThresholds
 */
public interface Thresholds {
    
    public void calculateThresholds(float load);  
    
    public void resetThresholds();
    
    public float getUpperThreshold();
    
    public float getLowerThreshold();

    public void reset(float uppert, float lowert);
    
}
