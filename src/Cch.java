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
            ByteBuffer wrapped =ByteBuffer.wrap(dig);
            long aux = wrapped.getLong();
            int noBits=algo.equals("SHA256")?32:64;
            long ref= (long) Math.pow(2, noBits-ceros);
            if(aux>ref)
            {
            	Sequencia seq = new Sequencia();
            	long startTime = System.currentTimeMillis();
            	for(int i=0;i<numThreads;i++)
            	{
            		Seeker actSeeker= new Seeker(seq, algo, cadena, ref);
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







}
