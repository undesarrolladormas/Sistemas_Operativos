package mx.uaemex.sistemas.replacement;

import javax.swing.*;

public class Clairvoyant extends AbstractReplacementAlgorithm {

    public Clairvoyant(String[] reference, int frames, JTable table)
    {
        super(reference,frames,table);
    }

    protected void solve() {
        boolean isFull = false;
        for(int i = 0; i < super.ref_len; i++)
        {
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
                    int[] index = new int[frames];
                    boolean[] index_flag = new boolean[frames];
                    for(int j = i + 1; j < ref_len; j++)
                    {
                        for(int k = 0; k < frames; k++)
                        {
                            if((reference[j].equals(buffer[k])) && (!index_flag[k]))
                            {
                                index[k] = j;
                                index_flag[k] = true;
                                break;
                            }
                        }
                    }
                    int max = index[0];
                    pointer = 0;
                    if(max == 0)
                        max = 200;
                    for(int j = 0; j < frames; j++)
                    {
                        if(index[j] == 0)
                            index[j] = 200;
                        if(index[j] > max)
                        {
                            max = index[j];
                            pointer = j;
                        }
                    }
                }
                buffer[pointer] = reference[i];
                fault++;
                if(!isFull)
                {
                    pointer++;
                    if(pointer == frames)
                    {
                        pointer = 0;
                        isFull = true;
                    }

                }
                matches.add("⚠️");
            }
            else matches.add(" ");
            System.arraycopy(buffer, 0, mem_layout[i], 0, frames);
        }
    }
}
