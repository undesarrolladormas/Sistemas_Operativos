package mx.uaemex.sistemas.replacement;

import javax.swing.*;

public class Fifo extends AbstractReplacementAlgorithm {
    public Fifo(String[] reference, int frames, JTable table) {
        super(reference,frames,table);
    }

    @Override
    protected void solve() {
        for(int i = 0; i < ref_len; i++)
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
                buffer[pointer] = reference[i];
                fault++;
                pointer++;
                if(pointer == frames)
                    pointer = 0;
                matches.add("⚠️");
            }
            else matches.add(" ");
            System.arraycopy(buffer, 0, mem_layout[i], 0, frames);
        }
    }
}
