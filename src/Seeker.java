import java.nio.ByteBuffer;

public class Seeker extends Thread
{
	private Sequencia seq;
	private String algo;
	private String og;
	private Cch  cch;
	private long ref;
	
	public Seeker(Sequencia pSeq, String pAlgo, String pOg, long pRef)
	{
		seq=pSeq;
		algo=pAlgo;
		og=pOg;
		cch=new Cch();
		ref=pRef;
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
    		wrapped =ByteBuffer.wrap(dig);
            aux = wrapped.getLong();
            if(aux<ref)
            {
            	System.out.println("El valor v="+conc);
            	synchronized(seq)
            	{
            		seq.parar();
            	}
            }
        }
		
	}
}
