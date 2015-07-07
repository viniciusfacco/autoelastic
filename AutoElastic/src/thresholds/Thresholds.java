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
 */
public interface Thresholds {
    
    public void calculateThresholds(float load);  
    
    public void recalculateLowerThreshold(float load);
    
    public void recalculateUpperThreshold(float load);
    
    public float getUpperThreshold();
    
    public float getLowerThreshold();
    
}
