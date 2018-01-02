package demo;


public class IP {
    private String min;
    private String max;
    private String loc;

    public IP(String min, String max, String loc) {
        this.min = min;
        this.max = max;
        this.loc = loc;
    }

    public String getMin() {

        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}