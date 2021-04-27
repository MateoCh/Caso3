import java.nio.ByteBuffer;

public class Seeker extends Thread
{
	private Sequencia seq;
	private String algo;
	private String og;
	private Cch  cch;
	private int ceros;
	
	public Seeker(Sequencia pSeq, String pAlgo, String pOg, int pCeros)
	{
		seq=pSeq;
		algo=pAlgo;
		og=pOg;
		cch=new Cch();
		ceros=pCeros;
	}
	
	public void run()
	{
		byte [] dig = cch.getDigest(algo, og.getBytes());
		ByteBuffer wrapped =ByteBuffer.wrap(dig);
        long aux = wrapped.getLong();
        while(seq.corriendo())
        {
        	String conc="";
        	synchronized(seq)
        	{
        		conc+=seq.getNext();
        	}
        	String toTry=og+conc;
        	dig = cch.getDigest(algo, toTry.getBytes());
            if(cch.cumple(dig, ceros, algo))
            {
            	System.out.println("El valor v encontrado fue:"+conc);
            	System.out.println("El hash en hexa:");
            	cch.imprimirHexa(dig);
            	System.out.println("El hash en binario:");
            	cch.imprimirBin(dig);
            	synchronized(seq)
            	{
            		seq.parar();
            	}
            }
        }
		
	}
}
