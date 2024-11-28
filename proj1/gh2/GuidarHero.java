package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuidarHero {
    private static double calculateFrequency(int index) {
        return 440 * Math.pow(2, ((index - 24) / 12.0));
    }

    public static void main(String[] args) {
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        GuitarString[] strings = new GuitarString[37];

        for(int i = 0; i < strings.length; i++) {
            strings[i] = new GuitarString(calculateFrequency(i));
        }

        while(true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if(keyboard.indexOf(key) != -1) {
                    strings[keyboard.indexOf(key)].pluck();
                }
            }

            double sample = 0.0;
            for(GuitarString string : strings) {
                sample += string.sample();
            }

            StdAudio.play(sample);

            for(GuitarString string : strings) {
                string.tic();
            }
        }

    }
}
