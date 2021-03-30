package algorithm;

import java.util.Random;

class Particle {

    private Vector position;
    private Vector velocity;
    private Vector bestPosition;
    private double bestValue;
    private FunctionType function;

    Particle(FunctionType function, int beginRange, int endRange) {
        if (beginRange >= endRange) {
            throw new IllegalArgumentException("Błędne dane!");
        }
        this.function = function;
        position = new Vector();
        velocity = new Vector();
        setRandomPosition(beginRange, endRange);
        bestPosition = velocity.clone();
        bestValue = evaluate();
    }

    private double evaluate() {
        if (function == FunctionType.MATYAS) {
            return Function.matyasFunction(position.getX(), position.getY());
        } else if (function == FunctionType.ACKLEY) {
            return Function.ackleysFunction(position.getX(), position.getY());
        } else if (function == FunctionType.BOOTH) {
            return Function.boothsFunction(position.getX(), position.getY());
        } else if (function == FunctionType.BEALE) {
            return Function.bealeFunction(position.getX(), position.getY());
        } else {
            return Function.goldsteinPriceFunction(position.getX(), position.getY());
        }
    }

    private void setRandomPosition(int beginRange, int endRange) {
        double x = Math.random() * (endRange - beginRange) + beginRange;
        double y = Math.random() * (endRange - beginRange) + beginRange;
        double z = Math.random() * (endRange - beginRange) + beginRange;
        position.set(x, y, z);
    }

    private static int rand(int beginRange, int endRange) {
        Random r = new Random();
        return r.nextInt(endRange - beginRange) + beginRange;
    }

    void updatePersonalBest() {
        double eval = evaluate();
        if (eval < bestValue) {
            bestPosition = position.clone();
            bestValue = eval;
        }
    }

    Vector getPosition() {
        return position.clone();
    }

    Vector getVelocity() {
        return velocity.clone();
    }

    Vector getBestPosition() {
        return bestPosition.clone();
    }

    double getBestValue() {
        return bestValue;
    }

    void updatePosition() {
        this.position.add(velocity);
    }

    void setVelocity(Vector velocity) {
        this.velocity = velocity.clone();
    }

}
