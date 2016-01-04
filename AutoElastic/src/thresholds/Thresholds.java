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
    
    public void recalculateUpperThreshold(float x, float y, float z);
    
    public void recalculateLowerThreshold(float x, float y, float z);
    
    public float getUpperThreshold();
    
    public float getLowerThreshold();
    
    public void setUpperThreshold(float threshold);
    
    public void setLowerThreshold(float threshold);

    public void reset(float uppert, float lowert);
    
}
