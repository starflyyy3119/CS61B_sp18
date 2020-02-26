public class Planet {

    /* Gravitational constant */
    private static final double G = 6.67e-11;

    /* Its current x position */
    public double xxPos;

    /* Its current y position */
    public double yyPos;

    /* Its current velocity in the x direction */
    public double xxVel;

    /* Its current velocity in the y direction */
    public double yyVel;

    /* Its mass */
    public double mass;

    /* The name of the file that corresponds to the image that depicts the planet */
    public String imgFileName;

    /**
     * Constructor of the Planet Class
     */
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    /**
     * This constructor initialize an identical Planet Object (i.e. a copy)
     */
    public Planet(Planet p)
    {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    /**
     * Calculates the distance between two Planets
     * @param that denotes another planet
     * @return the distance between this Planet and that Planet
     */
    public double calcDistance(Planet that) {
        double dx = that.xxPos - this.xxPos;
        double dy = that.yyPos - this.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Calculates the Force Exerted By another Planets
     * @param that denotes another planet
     * @return the force exerted by another Planets
     */
    public double calcForceExertedBy(Planet that) {
        double r = calcDistance(that);
        return G * this.mass * that.mass / (r * r);
    }

    /**
     * Calculates the Force Exerted By another Planet in the X directions
     * @param that denotes another Planet
     * @return the force exerted by another planet in the X directions
     */
    public double calcForceExertedByX(Planet that) {
        double r = calcDistance(that);
        double dx = (that.xxPos - this.xxPos);
        double F = calcForceExertedBy(that);
        return F * dx / r;
    }

    /**
     * Calculates the Force Exerted By another Planet in the Y directions
     * @param that denotes another Planet
     * @return the force exerted by another planet in the Y directions
     */
    public double calcForceExertedByY(Planet that) {
        double r = calcDistance(that);
        double dy = (that.yyPos - this.yyPos);
        double F = calcForceExertedBy(that);
        return F * dy / r;
    }

    /**
     * Calculates the net X force exerted by all planets upon the current Planet
     * @param allPlanets an array of Planets
     * @return the net X force exerted by all planets upon the current Planet
     */
    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double sumForce = 0.0;
        for (Planet p : allPlanets) {
            if (this.equals(p)) continue;
            sumForce += calcForceExertedByX(p);
        }
        return sumForce;
    }

    /**
     * Calculates the net Y force exerted by all planets upon the current Planet
     * @param allPlanets an array of Planets
     * @return the net Y force exerted by all planets upon the current Planet
     */
    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double sumForce = 0.0;
        for (Planet p : allPlanets) {
            if (this.equals(p)) continue;
            sumForce += calcForceExertedByY(p);
        }
        return sumForce;
    }

    /**
     * update the position and velocity of this planet in a small period of time
     * @param dt a small period of time
     * @param fX net force in the X direction
     * @param fY net force in the Y direction
     */

    public void update(double dt, double fX, double fY) {

        /* compute the acceleration */
        double ax = fX / this.mass;
        double ay = fY / this.mass;

        /* update the velocity */
        this.xxVel += dt * ax;
        this.yyVel += dt * ay;

        /* update the position */
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }

    public void draw()
    {
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }
}
