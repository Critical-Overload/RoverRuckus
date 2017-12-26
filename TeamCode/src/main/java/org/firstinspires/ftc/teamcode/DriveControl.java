package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by mingch on 12/26/17.
 */

public class DriveControl{
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;

    public void drive(double power, double inches) {
        power = power*0.5;
        motorBackLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(power);
        motorBackRight.setPower(power);
        double w = (inches/23)*1000;
        int y = (int) Math.rint(w);
        String x = Integer.toString(y);

        sleep(y);

        completeStop();
    }

    public void fourWheelTurn(double power, double degrees) {
        motorBackLeft.setPower(-power);
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(-power);
        motorBackRight.setPower(power);
        double w = (degrees/180)*1000;
        int y = (int) Math.rint(w);
        String x = Integer.toString(y);
        sleep(y);
        completeStop();
    }

    public void driveTime(double power, long time){
        motorBackLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(power);
        motorBackRight.setPower(power);
        String x = Long.toString(time);
        sleep(time * 1000);
    }

    public void completeStop(){
        motorBackLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
    }

    public void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}

