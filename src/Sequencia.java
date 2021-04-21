
public class Sequencia 
{
	private String act;
	private String[] deep;
	private int abc;
	private boolean siga;
	private int cont;
	
	public Sequencia()
	{
		abc=26;
		deep= new String[abc];
		act="";
		siga=true;
		cont=0;
	}
	
	public String getNext()
	{
		String rta=next(cont);
		cont++;
		return rta;
	}
	
	public String next(int cont)
	{
		String rta="";
		if(cont<abc)
		{
			rta+=Character.toString ((char) 97+cont);
			deep[cont]=rta;
		}
		else
		{
			int deepAct=(cont/abc)-1;
			deep[deepAct]=Character.toString ((char) 97+deepAct);
			rta+=deep[deepAct];
			rta+=next(cont%abc);
		}
		return rta;
	}
	
	public boolean corriendo()
	{
		return siga;
	}
	
	public void parar()
	{
		siga=false;
	}
	
}
