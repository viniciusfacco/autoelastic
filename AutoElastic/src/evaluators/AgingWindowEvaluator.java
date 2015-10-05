/*
 * 26/05/2014
 * AgingWindowEvaluator
 * - Classe destinada a fazer a avaliação de carga de CPU;
 * - Avaliação consiste em examinar um histórico de X leituras de CPU aplicando o método de envelhecimento para obter o valor de comparação com os thresholds
 * - São considerados as últimas VIEW_SIZE observações para o cálculo do AgingWindowEvaluator.
 */

package evaluators;

import java.util.ArrayList;

/**
 * Reviews
 * @author viniciusfacco
 * 02/07/2015 - viniciusfacco 
 *            - changed the constructor and parameters. Now the thresholds are not keep here
 *            - changed method "evaluate" to receive the thresholds as parameters
 * 06/07/2015 - viniciusfacco
 *            - class renamed from "AgingEvaluator" to "AgingWindowEvaluator"
 */


public class AgingWindowEvaluator extends GenericEvaluator{
    
    private ArrayList<Float> observ; //list with the VIEW_SIZE observations
    
    
    //26/05/2014: constructor
    //public AgingWindowEvaluator(int viewsize, float th_max, float th_min){
    //    super(viewsize, th_max, th_min); //call father's constructor
    public AgingWindowEvaluator(int viewsize){
        super(viewsize); //call father's constructor
        objname = "evaluators.AgingWindowEvaluator";    //rewrite the name
        observ = new ArrayList<>();
    }
    
    //26/05/2014: viniciusfacco
    /**
     * Return if the aging system identified if the factor is out of range between the thresholds.
     * @param load - the current system load.
     * @param upper_threshold - the current upper threshold.
     * @param lower_threshold - the current lower threshold.
     * @return 
     */
    @Override
    public boolean evaluate(float upper_threshold, float lower_threshold){        
        //test if the aging is out of the range between the thresholds
        if (decision_load > upper_threshold) { //test if we have a violation on the higher threshold after aply the aging
            high_alert = true; 
            low_alert = false; 
            return true;
        } else if (decision_load < lower_threshold){ //test if we have a violation on the lower threshold after aply the aging
            high_alert = false;
            low_alert = true;
            return true; 
        } else {
            high_alert = false;
            low_alert = false;
        }
        return false;
    }
    
    @Override
    public float computeLoad(float load){
        decision_load = 0;
        observ.add(0, load); //inserte the new value in the first place in the list
        //aply the aging concept if counter > VIEW_SIZE and the average if VIEW_SIZE < counter < 2
        if (observ.size() >= VIEW_SIZE){ // if we do have the minimum of VIEW_SIZE values
            decision_load = calc_aging(0); //calculate the aging initializing in the first position (the newer value)
        } else if (observ.size() > 2){ // if the counter is betwen 3 and VIEW_SIZE we will calculate the average to define the factor
            decision_load = average();
        }
        return decision_load;
    }
    
    //return the aging value for the VIEW_SIZE window
    private float calc_aging(int index){
        if (index == (VIEW_SIZE -1)){ //if this is the last value to use in the calculation (-1 is because we are working with ArrayList and the first position is 0)
            return (float) (observ.get(index) * 0.5); //return the actual value in the list * 1/2
        } else { //if we do not have in the last value
            return (float) ((observ.get(index) * 0.5) + (calc_aging(index + 1) * 0.5)); //we calculate the actual value and sum with the calculation of the next values
        }
    }
    
    //return the average of all values in the list
    private float average(){
        float value = 0;
        for (Float observ_value : observ) { //for each value in the observ
            value = value + observ_value;
        }
        return value / observ.size();
    }
    
    @Override
    public void reset(){
        super.reset();
        observ = new ArrayList<>();
    }
    
}
