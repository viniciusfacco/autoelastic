/*
 * 26/05/2014
 * Aging
 * - Classe destinada a fazer a avaliação genérica de carga de CPU;
 * - Avaliação consiste em examinar um histórico de X leituras de CPU aplicando o método de envelhecimento para obter o valor de comparação com os thresholds
 * - São considerados as últimas 6 observações para o cálculo do Aging.
 */

package evaluators;

import java.util.ArrayList;

/**
 *
 * @author Vinicius
 * 26/05/2014 - criação da classe
 */


public class Aging extends Generic{
    
    private ArrayList<Float> observ; //list with the VIEW_SIZE observations
    
    
    //26/05/2014: constructor
    public Aging(int viewsize, float th_max, float th_min){
        super(viewsize, th_max, th_min); //call father's constructor
        objname = "evaluators.Aging";    //rewrite the name
        observ = new ArrayList<>();
    }
    
    //26/05/2014: return if the aging system identified if the factor is out of range between the thresholds
    @Override
    public boolean evaluate(float load){
        float factor = 0;
        observ.add(0, load); //inserte the new value in the first place in the list
        //aply the aging concept if counter > VIEW_SIZE and the average if VIEW_SIZE < counter < 2
        if (observ.size() >= VIEW_SIZE){ // if we do have the minimum of VIEW_SIZE values
            factor = calc_aging(0); //calculate the aging initializing in the first position (the newer value)
        } else if (observ.size() > 2){ // if the counter is betwen 3 and VIEW_SIZE we will calculate the average to define the factor
            factor = average();
        }
        //test if the aging is out of the range between the thresholds
        if (factor > threshold_high) { //test if we have a violation on the higher threshold after aply the aging
            high_alert = true; 
            low_alert = false; 
            return true;
        } else if (factor < threshold_low){ //test if we have a violation on the lower threshold after aply the aging
            high_alert = false;
            low_alert = true;
            return true; 
        }
        
        return false;
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
    
}
