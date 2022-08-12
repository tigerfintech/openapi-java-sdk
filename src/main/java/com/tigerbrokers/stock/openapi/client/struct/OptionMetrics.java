package com.tigerbrokers.stock.openapi.client.struct;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/08/12
 */
public class OptionMetrics {
    double delta;
    double gamma;
    double theta;
    double vega;
    double rho;

    public OptionMetrics(double delta, double gamma, double theta, double vega, double rho) {
        this.delta = delta;
        this.gamma = gamma;
        this.theta = theta;
        this.vega = vega;
        this.rho = rho;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getVega() {
        return vega;
    }

    public void setVega(double vega) {
        this.vega = vega;
    }

    public double getRho() {
        return rho;
    }

    public void setRho(double rho) {
        this.rho = rho;
    }
}
