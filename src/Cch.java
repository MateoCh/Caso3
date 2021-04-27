import java.security.MessageDigest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

public class Cch 
{
	private static int numThreads=1000;

	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("ingrese el algoritmo que desea usar (SHA256 o SHA512)");
        String algo = br.readLine();
        System.out.print("Ingrese la cadena a la que se le quiere encontrar el hash:");
        String cadena = br.readLine();
        System.out.println("Ingrese el numero de ceros buscado:");
        try 
        {
            int ceros = Integer.parseInt(br.readLine());
            byte [] dig = getDigest(algo, cadena.getBytes());
            System.out.println("La cadena usada fue: "+cadena);
            if(!cumple(dig, ceros, algo))
            {
            	Sequencia seq = new Sequencia();
            	long startTime = System.currentTimeMillis();
            	for(int i=0;i<numThreads;i++)
            	{
            		Seeker actSeeker= new Seeker(seq, algo, cadena, ceros);
            		actSeeker.start();
            		actSeeker.join();
            	}
            	long estimatedTime = System.currentTimeMillis() - startTime;
            	System.out.println("El tiempo de busqueda fue de: " + estimatedTime + "ms");
            }
            else
            {
            	System.out.println("La cadena por defecto resultó en un hash con los ceros requeridos por lo que no se tuvo que realizar busqueda.");
            	System.out.println("Hash en hexa:");
            	imprimirHexa(dig);
            	System.out.println("Hash en binario:");
            	imprimirBin(dig);
            }
            
        } 
        catch(Exception e)
        {
            e.printStackTrace();;
        }
	}

	public static byte[] getDigest(String algo, byte[] buffer)
	{
		try 
		{
			MessageDigest digest=MessageDigest.getInstance(algo);
			digest.update(buffer);
			return digest.digest();
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public static void imprimirHexa(byte[] byteArray)
	{
		String out="";
		for(int i=0; i<byteArray.length;i++)
		{
			if((byteArray[i]& 0xff)<=0xf)
			{
				out+="0";
			}
			out+=Integer.toHexString(byteArray[i] & 0xff).toLowerCase();
		}
		System.out.println(out);
	}
	
	public static void imprimirBin(byte[] byteArray)
	{
		String out="";
		for(int i=0; i<byteArray.length;i++)
		{
			out+=String.format("%8s", Integer.toBinaryString(byteArray[i])).replace(' ', '0');
		}
		System.out.println(out);
	}
	
	public static boolean cumple(byte[] l1, int ceros, String algo)
	{
		boolean rta=true;
		int iters=ceros/8;
		int extra=ceros-(iters*8);
		int power=8-extra;
		int factor=0;
		for(int i=0;i<power;i++)
		{
			factor+=Math.pow(2, i);
		}
		int cont=0;
		while(cont<iters&&rta)
		{
			if((l1[cont]& 0xff)==0)
			{				
				cont++;
			}
			else
			{
				rta=false;
			}
		}
		if(rta)
		{			
			Byte act=l1[cont];
			int comp=Byte.toUnsignedInt(act);
//			System.out.println("Comp:"+comp+"Factor:"+factor);
			if(extra==0||comp<=factor)
			{
				
			}
			else
			{
				rta=false;
			}
		}
		return rta;
	}







}
