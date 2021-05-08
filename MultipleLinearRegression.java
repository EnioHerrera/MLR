package examples.bookTrading;
import java.text.DecimalFormat;

public class MultipleLinearRegression{
	
	public static void main(String[] args) {
		double p1=80;
		double p2=33.5;
		//calculation(p1,p2);
		calculation2(p1,p2);
	}

	public static void calculation(double P1, double P2) {
		
		double[] x1 = new double[] {41.9,43.4,43.9,44.5,47.3,47.5,47.9,50.2,52.8,53.2,56.7,57.0,63.5,65.3,71.1,77.0,77.8};
		double[] x2 = new double[] {29.1,29.3,29.5,29.7,29.9,30.3,30.5,30.7,30.8,30.9,31.5,31.7,31.9,32.0,32.1,32.5,32.9};
      		double[] y = new double[] {251.3,251.3,248.3,267.5,273.0,276.5,270.3,274.9,285.0,290.0,297.0,302.5,304.5,309.3,321.7,330.7,349.0};

		int n;
		double SUMX1=0,SUMX2=0,SUM2X1=0,SUM2X2=0,SUMX1X2=0;
		double SUMY=0,SUMYX1=0,SUMYX2=0;
		n=x1.length;
		for(int i=0; i < n; i++) {
			SUMX1= SUMX1 + x1[i];
			SUM2X1= SUM2X1 + x1[i]*x1[i];
			SUMX2= SUMX2 + x2[i];
			SUM2X2= SUM2X2 + x2[i]*x2[i];
			SUMX1X2= SUMX1X2 + x1[i]*x2[i];
			SUMY= SUMY + y[i];
			SUMYX1= SUMYX1 + x1[i]*y[i];
			SUMYX2= SUMYX2 + x2[i]*y[i];
		}

		double B0,B1,B2,D;
		D=n*SUM2X1*SUM2X2+SUMX1*SUMX1X2*SUMX2+SUMX2*SUMX1*SUMX1X2 -SUMX2*SUM2X1*SUMX2-n*SUMX1X2*SUMX1X2-SUM2X2*SUMX1*SUMX1;
		B0=(SUMY*SUM2X1*SUM2X2+SUMYX1*SUMX1X2*SUMX2+SUMYX2*SUMX1*SUMX1X2 -SUMX2*SUM2X1*SUMYX2-SUMX1X2*SUMX1X2*SUMY-SUM2X2*SUMX1*SUMYX1)/D;
		B1=(n*SUMYX1*SUM2X2+SUMX1*SUMYX2*SUMX2+SUMX2*SUMY*SUMX1X2 -SUMX2*SUMYX1*SUMX2-n*SUMYX2*SUMX1X2-SUM2X2*SUMY*SUMX1)/D;
		B2=(n*SUM2X1*SUMYX2+SUMX1*SUMX1X2*SUMY+SUMX2*SUMX1*SUMYX1 -SUMY*SUM2X1*SUMX2-SUMYX1*SUMX1X2*n-SUMYX2*SUMX1*SUMX1)/D;
		String sValue = (String) String.format("%.2f", B0);
		Double newValue = Double.parseDouble(sValue);
		String sValue1 = (String) String.format("%.2f", B1);
		Double newValue1 = Double.parseDouble(sValue1);
		String sValue2 = (String) String.format("%.2f", B2);
		Double newValue2 = Double.parseDouble(sValue2);
		result(newValue,newValue1,newValue2);

		double prediction=0;
		prediction=B0+B1*P1+B2*P2;
		System.out.println("\n"+prediction);
	}

	public static void calculation2(double P1, double P2) {

		double[] x1 = new double[] {41.9,43.4,43.9,44.5,47.3,47.5,47.9,50.2,52.8,53.2,56.7,57.0,63.5,65.3,71.1,77.0,77.8};
		double[] x2 = new double[] {29.1,29.3,29.5,29.7,29.9,30.3,30.5,30.7,30.8,30.9,31.5,31.7,31.9,32.0,32.1,32.5,32.9};
      		double[] y = new double[] {251.3,251.3,248.3,267.5,273.0,276.5,270.3,274.9,285.0,290.0,297.0,302.5,304.5,309.3,321.7,330.7,349.0};
		
		int n;
		n=y.length;
		double[][] X=new double[n][3];
		double[][] XT=new double[3][n];
		
		//Obtener X
		for(int i=0;i<n;i++){
			for(int j=0;j<3;j++){
				if(j==0){
				X[i][j]=1;
				}else if(j==1){
			 		X[i][j]=x1[i];
				}else {
					X[i][j]=x2[i];
				}
			}
		}
		//Obtener X'
		for(int i=0;i<3;i++){
			for(int j=0;j<n;j++){
				if(i==0){
					XT[i][j]=1;
				}else if(i==1){
					XT[i][j]=x1[j];
				}else{
					XT[i][j]=x2[j];
				}
			}
		}
		//Multiplicacion de X'X
		double[][] R=new double[3][3];
		System.out.print("\n X'X: \n");
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				for(int k=0;k<n;k++){
					R[i][j]=R[i][j] + XT[i][k]*X[k][j];
				}
				System.out.print(R[i][j]+"\t");
			}
			System.out.print("\n");
		}
		double[][] I=invert(R);

		//Multiplicacion X'Y
		double[] R2=new double[3];
		for(int i=0;i<3;i++){
			for(int j=0;j<n;j++){
				R2[i]=R2[i] + XT[i][j]*y[j];
			}
		}
		
		//Imprime inversa
		System.out.print("\n\n (X'X)^-1: \n");
		for (int i=0; i<3; ++i) {
                	for (int j=0; j<3; ++j){
				System.out.print(I[i][j]+"\t");
			}
		System.out.print("\n");
           	}

		System.out.print("\n\nX'Y: \n");
		//Imprime X'Y
		for(int i=0;i<3;i++){
			System.out.print(R2[i]+"\t");
		}

		System.out.print("\n\nYhat: ");
		//Multiplicacion INV(X'X)X'Y = I*R2
		double[] MLR=new double[3];
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				MLR[i]=MLR[i] + I[i][j]*R2[j];
			}
			if(i==0)System.out.print(MLR[i]+"\t");
			if(i==1)System.out.print(MLR[i]+"x1\t");
			if(i==2)System.out.print(MLR[i]+"x2 \n");
		}
		
		System.out.print("\n\nPrediccion: ");
		//Yhat con x1 y x2
		double prediccion=0;
		for(int i=0;i<3;i++){
			if(i==0)prediccion=MLR[i];
			if(i==1)prediccion=prediccion+MLR[i]*P1;
			if(i==2)prediccion=prediccion+MLR[i]*P2;
		}
		System.out.print(prediccion+"\n\n");
	}

	public static void result(double B0,double B1, double B2) {
		System.out.println("yhat= "+B0+" + "+B1+"x1 + "+B2+"x2");
	}

	public static double[][] invert(double a[][]){
		int n = a.length;
            	double x[][] = new double[n][n];
            	double b[][] = new double[n][n];
            	int index[] = new int[n];
            	for (int i=0; i<n; ++i)
                	b[i][i] = 1;
		
		gaussian(a, index);

		// Update the matrix b[i][j] with the ratios stored
            	for (int i=0; i<n-1; ++i)
                	for (int j=i+1; j<n; ++j)
                    		for (int k=0; k<n; ++k)
                        		b[index[j]][k]-= a[index[j]][i]*b[index[i]][k];
		
		// Perform backward substitutions
            	for (int i=0; i<n; ++i)             {
                	x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
                	for (int j=n-2; j>=0; --j){
                    		x[j][i] = b[index[j]][i];
                    		for (int k=j+1; k<n; ++k){			
                        		x[j][i] -= a[index[j]][k]*x[k][i];
                    		}
				x[j][i] /= a[index[j]][j];
                	}
            	}
     		return x;
	}

	public static void gaussian(double a[][], int index[]){
		int n = index.length;
            	double c[] = new double[n];

     		// Initialize the index
            	for (int i=0; i<n; ++i)
                	index[i] = i;
	
		// Find the rescaling factors, one from each row
            	for (int i=0; i<n; ++i) {
                	double c1 = 0;
                	for (int j=0; j<n; ++j) {
                    		double c0 = Math.abs(a[i][j]);
                    		if (c0 > c1) c1 = c0;
                	}
                	c[i] = c1;
            	}	

		// Search the pivoting element from each column
            	int k = 0;
            	for (int j=0; j<n-1; ++j) {
                	double pi1 = 0;
                	for (int i=j; i<n; ++i)  {
	            	    	double pi0 = Math.abs(a[index[i]][j]);
                    		pi0 /= c[index[i]];
                    		if (pi0 > pi1) {
                        		pi1 = pi0;
                        		k = i;
                    		}
                	}
			// Interchange rows according to the pivoting order	
			int itmp = index[j];
                	index[j] = index[k];
                	index[k] = itmp;
                	for (int i=j+1; i<n; ++i) {
                    		double pj = a[index[i]][j]/a[index[j]][j];
				// Record pivoting ratios below the diagonal
                    		a[index[i]][j] = pj;	
				// Modify other elements accordingly
                    		for (int l=j+1; l<n; ++l)
                        		a[index[i]][l] -= pj*a[index[j]][l];
                	}

		}
	}//gaussiana

}


