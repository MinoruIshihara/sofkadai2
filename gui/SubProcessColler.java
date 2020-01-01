import java.lang.Process;
import java.util.*;
import java.io.IOException;
import java.lang.InterruptedException;

public class SubProcessColler
{

    private ProcessBuilder pb;

    public SubProcessColler(String args[])
    {
        pb = new ProcessBuilder(args);
    }

    public ArrayList<String> runProcess()
    {
        ArrayList<String> retArray = new ArrayList<String>();
        try
        {
            Process process = pb.start();
            try
            { 
                int errCode = process.waitFor();
                Scanner padListScanner = new Scanner(process.getInputStream());
                while(padListScanner.hasNextLine())
                {
                    retArray.add(padListScanner.nextLine());
                }
            }
            catch(InterruptedException e){System.out.println("Cannot Start Quiz System");}
        }
        catch(IOException e){System.out.println("Cannot Open file");}

        return retArray;
    }
}

