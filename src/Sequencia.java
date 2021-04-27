
public class Sequencia 
{
	private String act;
	private int[] conts;
	private int abc;
	private boolean siga;
	private int cont;
	private int pos;
	private int pos2;
	private int pos3;
	private int i;
	
	
	public Sequencia()
	{
		abc=26;
		conts=new int[7];
		for(int i=0;i<conts.length;i++)
		{
			conts[i]=97;
		}
		act="";
		siga=true;
		cont=0;
		pos=0;
		pos2=1;
		pos3=0;
		i=1;
	}
	
	public String getNext()
	{
		String rta=next();
		return rta;
	}
	
	
	public String next()
	{
		String rta="";
		for(int i=0; i<pos+1;i++)
		{
			rta+=Character.toString ((char) conts[i]);
		}	
		turn(pos);
		System.out.println(rta);
		return rta;
		
	}
	
	public void turn(int p)
	{
		if(conts[p]<'z')
		{
			conts[p]++;
		}
		else
		{
			conts[p]='a';
			if(p>0)
			{
				turn(p-1);
			}
			else
			{
				if(pos<7)
				{
					pos++;					
				}
				else
				{
					parar();
					System.out.println("No se encontro un valor v en el espacio de busqueda que cumpliera el numero de ceros solicitado");
				}
			}
		}
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
