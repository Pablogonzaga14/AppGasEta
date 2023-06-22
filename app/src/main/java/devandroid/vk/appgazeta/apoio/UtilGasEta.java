package devandroid.vk.appgazeta.apoio;

public class UtilGasEta {
    public static String calucularMelhorOpcao(double gasolina, double etanol){

        double precoIdeal = gasolina * 0.70;
        String mensagemRetorno;
        if(etanol <= precoIdeal){
            mensagemRetorno = "Abastecer com Etanol";
        }
        else{
            mensagemRetorno = "Abastecer com Gasolina";
        }
        return mensagemRetorno;
    }
}
