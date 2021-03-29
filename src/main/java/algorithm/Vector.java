package algorithm;

class Vector {

    private double x, y, z;

    Vector() {
        this(0, 0, 0);
    }

    Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    double getZ() {
        return z;
    }

    void set(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    private void setX(double x) {
        this.x = x;
    }

    private void setY(double y) {
        this.y = y;
    }

    private void setZ(double z) {
        this.z = z;
    }

    void add(Vector v) {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    void subtract(Vector v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    void multiply(double m) {
        x *= m;
        y *= m;
        z *= m;
    }

    public Vector clone() {
        return new Vector(x, y, z);
    }

    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

}
