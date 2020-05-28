package lab14;

import javafx.animation.Animation;
import lab14lib.*;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		/** Your code here. */
//		Generator generator = new SineWaveGenerator(200);
//		GeneratorPlayer gp = new GeneratorPlayer(generator);
//		GeneratorDrawer gd = new GeneratorDrawer(generator);
//		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
//		GeneratorAudioAnimator gaa = new GeneratorAudioAnimator(generator);
//		gaa.drawAndPlay(4096, 100000);

//		Generator g1 = new SineWaveGenerator(60);
//		Generator g2 = new SineWaveGenerator(61);
//
//		ArrayList<Generator> generators = new ArrayList<>();
//		generators.add(g1);
//		generators.add(g2);
//		MultiGenerator mg = new MultiGenerator(generators);
//
//		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(mg);
//		gav.drawAndPlay(500000, 1000000);

		Generator generator = new SawToothGenerator(512);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
		gav.drawAndPlay(4096, 100000);

	}
} 