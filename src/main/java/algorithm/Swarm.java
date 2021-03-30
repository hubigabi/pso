package algorithm;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.util.Random;

import static controller.Controller.print;

public class Swarm {

    private int particlesNumber, epochs;
    private double inertiaCoefficient, cognitiveCoefficient, socialCoefficient;
    private Vector bestPosition;
    private double bestValue;
    private FunctionType function;

    public static final double DEFAULT_INERTIA = 0.7;
    public static final double DEFAULT_COGNITIVE = 2.0;
    public static final double DEFAULT_SOCIAL = 2.0;

    private int beginRange, endRange;
    private static final int DEFAULT_BEGIN_RANGE = -10;
    private static final int DEFAULT_END_RANGE = 11;

    public Swarm(FunctionType function, int particles, int epochs) {
        this(function, particles, epochs, DEFAULT_INERTIA, DEFAULT_COGNITIVE, DEFAULT_SOCIAL);
    }

    public Swarm(FunctionType function, int particles, int epochs, double inertia, double cognitive, double social) {
        this.particlesNumber = particles;
        this.epochs = epochs;
        this.inertiaCoefficient = inertia;
        this.cognitiveCoefficient = cognitive;
        this.socialCoefficient = social;
        this.function = function;
        double infinity = Double.POSITIVE_INFINITY;
        bestPosition = new Vector(infinity, infinity, infinity);
        bestValue = Double.POSITIVE_INFINITY;
        beginRange = DEFAULT_BEGIN_RANGE;
        endRange = DEFAULT_END_RANGE;
    }

    public void run(TextArea logTextArea) {
        Particle[] particles = initialize();
        double oldEval = bestValue;

        String separator = System.lineSeparator();
        final double val = bestValue;
        Platform.runLater(() -> {
            logTextArea.appendText("Parameters:" + separator);
            logTextArea.appendText("Inertia coefficient: " + String.format("%.2f", inertiaCoefficient) + separator);
            logTextArea.appendText("Cognitive coefficient: " + String.format("%.2f", cognitiveCoefficient) + separator);
            logTextArea.appendText("Social coefficient: " + String.format("%.2f", socialCoefficient) + separator + separator);

            logTextArea.appendText("Function: " + function.getName() + separator);
            logTextArea.appendText("Particles number: " + particlesNumber + separator);
            logTextArea.appendText("Iterations (epochs): " + epochs + separator + separator);
            logTextArea.appendText("START:" + separator);
            logTextArea.appendText("New best result (epoch " + 0 + "): " + print(val) + separator);
        });

        for (int i = 0; i < epochs; i++) {

            if (bestValue < oldEval) {
                oldEval = bestValue;

                final int iteration = i;
                final double value = bestValue;
                Platform.runLater(() -> {
                    logTextArea.appendText("New best result (epoch " + (iteration + 1) + "): " + print(value) + separator);
                });
            }

            for (Particle p : particles) {
                p.updatePersonalBest();
                updateGlobalBest(p);
            }

            for (Particle p : particles) {
                updateVelocity(p);
                p.updatePosition();
            }
        }

        Platform.runLater(() -> {
            logTextArea.appendText(separator + "FINAL RESULTS:" + separator);
            logTextArea.appendText("Value x = " + print(bestPosition.getX()) + separator);
            logTextArea.appendText("Value y = " + print(bestPosition.getY()) + separator);
            logTextArea.appendText("Best result: " + print(bestValue) + separator);
        });
    }


    private Particle[] initialize() {
        Particle[] particles = new Particle[particlesNumber];
        for (int i = 0; i < particlesNumber; i++) {
            Particle particle = new Particle(function, beginRange, endRange);
            particles[i] = particle;
            updateGlobalBest(particle);
        }
        return particles;
    }

    private void updateGlobalBest(Particle particle) {
        if (particle.getBestValue() < bestValue) {
            bestPosition = particle.getBestPosition();
            bestValue = particle.getBestValue();
        }
    }

    private void updateVelocity(Particle particle) {
        Vector oldVelocity = particle.getVelocity();
        Vector pBest = particle.getBestPosition();
        Vector gBest = bestPosition.clone();
        Vector pos = particle.getPosition();

        Random random = new Random();
        double r1 = random.nextDouble();
        double r2 = random.nextDouble();

        // INERTIA
        Vector newVelocity = oldVelocity.clone();
        newVelocity.multiply(inertiaCoefficient);

        // LOCAL
        pBest.subtract(pos);
        pBest.multiply(cognitiveCoefficient);
        pBest.multiply(r1);
        newVelocity.add(pBest);

        // GLOBAL
        gBest.subtract(pos);
        gBest.multiply(socialCoefficient);
        gBest.multiply(r2);
        newVelocity.add(gBest);

        particle.setVelocity(newVelocity);
    }

}
