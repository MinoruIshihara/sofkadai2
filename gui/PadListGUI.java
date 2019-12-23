import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Process;
import java.util.*;
import java.io.IOException;
import java.lang.InterruptedException;

public class PadListGUI
{
    public PadListGUI()
    {
        ArrayList<String> padArray = new ArrayList<String>(); 
        ProcessBuilder getPadListPB = new ProcessBuilder("python3","../quizsystem/GetPadList.py");
        try
        {
            Process getPadListProcess = getPadListPB.start();
            try
            {
                int errCode = getPadListProcess.waitFor();
                Scanner padListScanner = new Scanner(getPadListProcess.getInputStream());
                while(padListScanner.hasNextLine())
                {
                    padArray.add(padListScanner.nextLine());
                }
            }
            catch(InterruptedException e){System.out.println("Cannot Start Quiz System");}
        }
        catch(IOException e){System.out.println("Cannot Open file");}
        System.out.println(padArray.get(0));
        System.out.println(padArray.get(1));
    }
}

