/*************************************************************************
 *  Compilation:  javac Complex.java
 *  Execution:    java Complex
 *
 *  Data type for complex numbers.
 *
 *  The data type is "immutable" so once you create and initialize
 *  a Complex object, you cannot change it. The "final" keyword
 *  when declaring re and im enforces this rule, making it a
 *  compile-time error to change the .re or .im fields after
 *  they've been initialized.
 *
 *  % java Complex
 *  a            = 5.0 + 6.0i
 *  b            = -3.0 + 4.0i
 *  Re(a)        = 5.0
 *  Im(a)        = 6.0
 *  b + a        = 2.0 + 10.0i
 *  a - b        = 8.0 + 2.0i
 *  a * b        = -39.0 + 2.0i
 *  b * a        = -39.0 + 2.0i
 *  a / b        = 0.36 - 1.52i
 *  (a / b) * b  = 5.0 + 6.0i
 *  conj(a)      = 5.0 - 6.0i
 *  |a|          = 7.810249675906654
 *  tan(a)       = -6.685231390246571E-6 + 1.0000103108981198i
 *
 *************************************************************************/

public class PrincetonComplex {
    private final double re;   // the real part
    private final double im;   // the imaginary part

    // create a new object with the given real and imaginary parts
    public PrincetonComplex(double real, double imag) {
        re = real;
        im = imag;
    }

    // return a string representation of the invoking Complex object
    public String toString() {
        if (im == 0) return re + "";
        if (re == 0) return im + "i";
        if (im <  0) return re + " - " + (-im) + "i";
        return re + " + " + im + "i";
    }

    // return abs/modulus/magnitude and angle/phase/argument
    public double abs()   { return Math.hypot(re, im); }  // Math.sqrt(re*re + im*im)
    public double phase() { return Math.atan2(im, re); }  // between -pi and pi

    // return a new Complex object whose value is (this + b)
    public PrincetonComplex plus(PrincetonComplex b) {
        PrincetonComplex a = this;             // invoking object
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new PrincetonComplex(real, imag);
    }

    // return a new Complex object whose value is (this - b)
    public PrincetonComplex minus(PrincetonComplex b) {
        PrincetonComplex a = this;
        double real = a.re - b.re;
        double imag = a.im - b.im;
        return new PrincetonComplex(real, imag);
    }

    // return a new Complex object whose value is (this * b)
    public PrincetonComplex times(PrincetonComplex b) {
        PrincetonComplex a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new PrincetonComplex(real, imag);
    }

    // scalar multiplication
    // return a new object whose value is (this * alpha)
    public PrincetonComplex times(double alpha) {
        return new PrincetonComplex(alpha * re, alpha * im);
    }

    // return a new Complex object whose value is the conjugate of this
    public PrincetonComplex conjugate() {  return new PrincetonComplex(re, -im); }

    // return a new Complex object whose value is the reciprocal of this
    public PrincetonComplex reciprocal() {
        double scale = re*re + im*im;
        return new PrincetonComplex(re / scale, -im / scale);
    }

    // return the real or imaginary part
    public double re() { return re; }
    public double im() { return im; }

    // return a / b
    public PrincetonComplex divides(PrincetonComplex b) {
        PrincetonComplex a = this;
        return a.times(b.reciprocal());
    }

    // return a new Complex object whose value is the complex exponential of this
    public PrincetonComplex exp() {
        return new PrincetonComplex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    // return a new Complex object whose value is the complex sine of this
    public PrincetonComplex sin() {
        return new PrincetonComplex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex cosine of this
    public PrincetonComplex cos() {
        return new PrincetonComplex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex tangent of this
    public PrincetonComplex tan() {
        return sin().divides(cos());
    }
    


    // a static version of plus
    public static PrincetonComplex plus(PrincetonComplex a, PrincetonComplex b) {
        double real = a.re + b.re;
        double imag = a.im + b.im;
        PrincetonComplex sum = new PrincetonComplex(real, imag);
        return sum;
    }



    // sample client for testing
    public static void main(String[] args) {
        PrincetonComplex a = new PrincetonComplex(5.0, 6.0);
        PrincetonComplex b = new PrincetonComplex(-3.0, 4.0);

        System.out.println("a            = " + a);
        System.out.println("b            = " + b);
        System.out.println("Re(a)        = " + a.re());
        System.out.println("Im(a)        = " + a.im());
        System.out.println("b + a        = " + b.plus(a));
        System.out.println("a - b        = " + a.minus(b));
        System.out.println("a * b        = " + a.times(b));
        System.out.println("b * a        = " + b.times(a));
        System.out.println("a / b        = " + a.divides(b));
        System.out.println("(a / b) * b  = " + a.divides(b).times(b));
        System.out.println("conj(a)      = " + a.conjugate());
        System.out.println("|a|          = " + a.abs());
        System.out.println("tan(a)       = " + a.tan());
    }

}
