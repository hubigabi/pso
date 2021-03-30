package algorithm;

class Function {

    static double matyasFunction(double x, double y) {
        double p1 = 0.26 * (Math.pow(x, 2) + Math.pow(y, 2));
        double p2 = 0.48 * x * y;
        return p1 - p2;
    }

    static double ackleysFunction(double x, double y) {
        double p1 = -20 * Math.exp(-0.2 * Math.sqrt(0.5 * ((x * x) + (y * y))));
        double p2 = Math.exp(0.5 * (Math.cos(2 * Math.PI * x) + Math.cos(2 * Math.PI * y)));
        return p1 - p2 + Math.E + 20;
    }

    static double boothsFunction(double x, double y) {
        double p1 = Math.pow(x + 2 * y - 7, 2);
        double p2 = Math.pow(2 * x + y - 5, 2);
        return p1 + p2;
    }

    static double bealeFunction(double x, double y) {
        double p1 = Math.pow(1.5 - x + (x * y), 2);
        double p2 = Math.pow(2.25 - x + (x * y * y), 2);
        double p3 = Math.pow(2.625 - x + (x * y * y * y), 2);
        return p1 + p2 + p3;
    }

    static double goldsteinPriceFunction(double x, double y) {
        double p1 = 1 + Math.pow(x + y + 1, 2) * (19 - 14 * x + 3 * x * x - 14 * y + 6 * x * y + 3 * y * y);
        double p2 = 30 + Math.pow(2 * x - 3 * y, 2) * (18 - 32 * x + 12 * x * x + 48 * y - 36 * x * y + 27 * y * y);
        return p1 * p2;
    }

}