/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mol;

/**
 *
 * @author ADMIN
 */
public class AverageStar {

    private int pid;
    private int count;
    private double avg;

    public AverageStar() {
    }

    public AverageStar(int pid, int count, double avg) {
        this.pid = pid;
        this.count = count;
        this.avg = avg;
    }

    public int getPid() {
        return pid;
    }

    public int getCount() {
        return count;
    }

    public double getAvg() {
        return avg;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    @Override
    public String toString() {
        return "AverageStar{" + "pid=" + pid + ", count=" + count + ", avg=" + avg + '}';
    }

}
