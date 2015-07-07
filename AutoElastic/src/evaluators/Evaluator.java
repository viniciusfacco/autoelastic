/*
 * 02/07/2015
 * Evaluator
 * Author: viniciusfacco
 * - Interface used to define the methods to implement evaluators
 */
package evaluators;

/**
 *
 * @author viniciusfacco
 */
public interface Evaluator {
    
    public boolean evaluate(float load, float upper_threshold, float lower_threshold);
    
    public float getDecisionLoad();
    
    public boolean isHighAction();
    
    public boolean isLowAction();
    
    public byte whichAction();
    
    public void reset();
    
}
