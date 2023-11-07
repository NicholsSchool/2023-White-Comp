package org.firstinspires.ftc.teamcode;

public class LogisticController {

    private double a, h, c, k;
    
    public PIDController(double slopeSteepness, double xTranslation, double topAsymptote, double bottomAsymptote) {
        a = slopeSteepness;
        h = xTranslation;
        c = topAsymptote;
        k = bottomAsymptote;
    }

    public double calcPIDF(double error) {
        double output;
        //TODO: add jameses logistic curve function
        return output;
    }

}
