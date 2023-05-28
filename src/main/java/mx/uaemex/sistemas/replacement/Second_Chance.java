package mx.uaemex.sistemas.replacement;

import javax.swing.*;
import java.util.ArrayList;

public class Second_Chance extends AbstractReplacementAlgorithm{

    public Second_Chance(String[] reference, int frames, JTable table) {
        super(reference, frames, table);
    }

    @Override
    protected void solve() {
        boolean isFull = false;
        ArrayList<String> stack = new ArrayList<>();
        for(int i = 0; i < ref_len; i++)
        {
            stack.remove(reference[i]);
            stack.add(reference[i]);
            int search = -1;
            for(int j = 0; j < frames; j++)
            {
                if(buffer[j].equals(reference[i]))
                {
                    search = j;
                    hit++;
                    break;
                }
            }
            if(search == -1)
            {
                if(isFull)
                {
                    int min_loc = ref_len;
                    for(int j = 0; j < frames; j++)
                    {
                        if(stack.contains(buffer[j]))
                        {
                            int temp = stack.indexOf(buffer[j]);
                            if(temp < min_loc)
                            {
                                min_loc = temp;
                                pointer = j;
                            }
                        }
                    }
                }
                buffer[pointer] = reference[i];
                fault++;
                pointer++;
                if(pointer == frames)
                {
                    pointer = 0;
                    isFull = true;
                }
                matches.add("⚠️");
            }
            else matches.add("");
            if (frames >= 0) System.arraycopy(buffer, 0, mem_layout[i], 0, frames);
        }
    }
}
