package mx.uaemex.sistemas.replacement;

import javax.swing.*;
public class Clock extends AbstractReplacementAlgorithm {
    public Clock(String[] reference, int frames, JTable table) {
        super(reference, frames, table);
    }

    @Override
    protected void solve() {
        String[][] buffer2 = new String[frames][2];
        String[][] used_layout = new String[ref_len][frames];
        for(int j = 0; j < frames; j++)
        {
            buffer2[j][0] = "-";
            buffer2[j][1] = "=";
        }

        // - es -1, = es 0, ^ es 1

        for(int i = 0; i < ref_len; i++)
        {
            int search = -1;
            for(int j = 0; j < frames; j++)
            {
                if(buffer2[j][0].equals(reference[i]))
                {
                    search = j;
                    hit++;
                    buffer2[j][1] = "^";
                    break;
                }
            }
            if(search == -1)
            {

                while(buffer2[pointer][1].equals("^"))
                {
                    buffer2[pointer][1] = "=";
                    pointer++;
                    if(pointer == frames)
                        pointer = 0;
                }
                buffer2[pointer][0] = reference[i];
                buffer2[pointer][1] = "^";
                fault++;
                pointer++;
                if(pointer == frames)
                    pointer = 0;
                matches.add("⚠️");
            }
            else matches.add("");

            for(int j = 0; j < frames; j++)
            {
                mem_layout[i][j] = buffer2[j][0];
                used_layout[i][j] = buffer2[j][1];
            }
        }

    }
}
