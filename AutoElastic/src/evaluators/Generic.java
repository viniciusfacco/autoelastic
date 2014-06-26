/*
 * 24/04/2014
 * Generic
 * - Classe destinada a fazer a avaliação genérica de carga de CPU;
 * - Avaliação consiste em examinar um histórico de leituras de CPU e concluir se há necessidade de elasticidade ou não
 */

package evaluators;

import static autoelastic.AutoElastic.gera_log;

/**
 *
 * @author Vinicius
 */
public class Generic {

    protected static String objname = "evaluators.Generic";;//name of the class to be used in the logs
    protected int VIEW_SIZE;        //quantidade de dados históricos que serão avaliados
    protected float threshold_high; //limite superior em %
    protected float threshold_low;  //limite inferior em %
    protected byte count_violation; //quantidade de vezes que o limite superior ou inferior foi atingido consecutivamente
    protected boolean high_alert;   //flag para avisar se o limite superior foi atingido VIEW_SIZE vezes consecutivas
    protected boolean low_alert;    //flag para avisar se o limite inferior foi atingido VIEW_SIZE vezes consecutivas
    
    public Generic(int viewsize, float th_max, float th_min){
        threshold_high = th_max;
        threshold_low = th_min;
        count_violation = 0;
        high_alert = false;
        low_alert = false;
        VIEW_SIZE = viewsize;
    }
    
    //24/04/2014: método de avaliação que devolve true se houve uma sequência de VIEW_SIZE quebras seguidas em algum dos thresholds
    public boolean evaluate(float load){
        //----------------------------------------------------------------------
        //para realizar a avaliação primeiramente comparo a cpu com os thresholds
        if (load > threshold_high){         //verifico se violamos o limite superior
            if (count_violation >= 0){          //se sim, então verifico se o contador de violações está zerado ou já possui alguma contagem consecutiva de violações superiores
                count_violation++;              //se sim então incremento o contador
            } else count_violation = 1;         //se não, isso quer dizer que violações inferiores tinham ocorrido, então realizo a primeira contagem consecutiva de violação superior
        } else if (load < threshold_low){   //se não, então verifico se violamos o limite inferior
            if (count_violation <= 0){          //se sim, então verifico se o contador de violações está zerado ou já possui alguma contagem consecutiva de violações inferiores
                count_violation--;              //se sim então incremento o contador
            } else count_violation = -1;        //se não, isso quer dizer que violações superiores tinham ocorrido, então realizo a primeira contagem consecutiva de violação inferior
        } else count_violation = 0;             //se não, quer dizer que não violamos nenhum limite e posso zerar o contador de violações
        //----------------------------------------------------------------------
        //agora vamos verificar se ocorreram VIEW_SIZE violações seguidas
        if ((count_violation >= VIEW_SIZE)){                    //se ocorreram VIEW_SIZE violações superiores
            count_violation = 0;                                    //zeramos o contador
            high_alert = true;                                      //setamos para true o alerta de violações superiores consecutivas
            low_alert = false;                                      //setamos para false o alerta de violações inferiores consecutivas
            return true;                                            //retornamos true para avisar que há necessidade de ações
        } else if ((count_violation <= (-1 * VIEW_SIZE))){      //se não, se ocorreram VIEW_SIZE violações inferiores
            count_violation = 0;                                    //zeramos o contador
            high_alert = false;                                     //setamos para false o alerta de violações superiores consecutivas
            low_alert = true;                                       //setamos para true o alerta de violações inferiores consecutivas
            return true;                                             //retornamos true para avisar que há necessidade de ações
        }
        //----------------------------------------------------------------------
        //alternativa ao código acima, porém faz sempre 3 comparações e não mantém o histórico da última violação na próxima avaliação
        //high_alert = (count_violation >= VIEW_SIZE);
        //low_alert = (count_violation <= (-1 * VIEW_SIZE));
        //if (low_alert || low_alert){
        //    count_violation = 0;
        //    return true;
        //}
        //----------------------------------------------------------------------
        return false;
    }
    
    //25/04/2014:método para retornar se há necessidade de realizar alguma ação para violação superior
    public boolean isHighAction(){
        return high_alert;
    }
    
    //25/04/2014:método para retornar se há necessidade de realizar alguma ação para violação inferior
    public boolean isLowAction(){
        return low_alert;
    }
    
    //25/04/2014:método para retornar qual ação deve ser realizada: 1-superior, -1-inferior e 0-nenhuma
    public byte whichAction(){
        if (high_alert) 
            return 1;
        else if (low_alert) 
            return -1;
        else return 0;
    }
    
    //23/05/2014: reset the counters
    public void reset(){
        count_violation = 0;
        high_alert = false;
        low_alert = false;
    }
    
}
