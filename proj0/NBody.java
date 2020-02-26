public class NBody {

    /**
     * read the radius of a given file
     * @param filePath the file path
     * @return the radius
     */
    public static double readRadius(String filePath) {
        In in = new In(filePath);
        in.readInt();
        return in.readDouble();
    }

    /**
     * read the planets in a file
     * @param filePath the file path
     * @return an array of planets
     */
    public static Planet[] readPlanets(String filePath) {
        In in = new In(filePath);
        int planetNum = in.readInt();
        in.readDouble();
        Planet[] planets = new Planet[planetNum];
        for (int i = 0; i < planetNum; i++) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double mass = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xP, yP, xV, yV, mass, img);
        }
        return planets;
    }

    public static void main(String[] args) {

        /* Collected all needed input */
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        double uniRadius = readRadius(filename);

        /* Drawing the Background */
        String imgToDraw = "images/starfield.jpg";
        StdDraw.setXscale(-uniRadius, uniRadius);
        StdDraw.setYscale(-uniRadius, uniRadius);
        StdDraw.clear();
        StdDraw.picture(0, 0, imgToDraw);

        /* Drawing the planets*/
        for (Planet p : planets) {
            p.draw();
        }

        /* A graphic technique to prevent flicking in the animation */
        StdDraw.enableDoubleBuffering();

        double t = 0.0;
        while (t < T) {

            /* Calculate the net x and y forces for each planet */
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            /* Update on each of the planets, this will update each planet's position, velocity and acceleration */
            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            /* Draw the background image and all of the planets */
            StdDraw.picture(0, 0, imgToDraw);
            for (Planet p : planets) {
                p.draw();
            }

            /* Show the offscreen buffer */
            StdDraw.show();

            /* Pause the animation for 10 milliseconds */
            StdDraw.pause(10);

            /* Increase the time by dt */
            t += dt;
        }

        /* Printing the Universe */
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", uniRadius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
