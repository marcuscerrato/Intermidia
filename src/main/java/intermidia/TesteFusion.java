package intermidia;

public class TesteFusion {
    public static void main( String[] args ) throws Exception
    {
    	double array[] = { 1, 2, 3, 4, 5 };
    	int tamanho = array.length;
    	double maximo = 0;
    	double normalizado[] = { 0, 0, 0, 0, 0 };

		for(int j = 0; j < tamanho; j++)
		{
			if (array[j] > maximo) {
				maximo = array[j];
			}
		}
		
		for(int j = 0; j < tamanho; j++)
		{
			double normalisedValue = array[j] / maximo * 100;
			normalizado[j] = normalisedValue; 
			System.out.printf("%.4f ", normalisedValue);
		}
    }
}
