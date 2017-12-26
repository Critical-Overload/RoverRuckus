package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hadware.Servo;

/**
 * Created by mingch on 12/26/17.
 */

public class DriveControl{
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;
    private Servo leftServo;
    private Servo rightServo;
    private DcMotor liftMotor;
    
    public DriveControl(DcMotor motorFrontRight, DcMotor motorFrontLeft, DcMotor motorBackRight, DcMotor motorBackLeft, 
                        Servo leftServo, Servo rightServo, DcMotor liftMotor){
        this.motorFrontRight = motorFrontRight;
        this.motorFrontLeft = motorFrontLeft;
        this.motorBackRight = motorBackRight;
        this.motorBackLeft = motorBackLeft;
        this.leftServo = leftServo;
        this.rightServo = rightServo;
        this.liftMotor = liftMotor;
    }

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

    public void driveTime(double power, long seconds){
        motorBackLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(power);
        motorBackRight.setPower(power);
        String x = Long.toString(seconds);
        sleep(seconds * 1000);
    }

    public void completeStop(){
        motorBackLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
    }

    public void wait(long seconds){
        sleep(seconds * 1000);
    }
     
    public void closeClaw(){
        leftServo.setPosition(1);
        rightServo.setPosition(0);
    }

    public void openClaw(){
        leftServo.setPosition(0.5);
        rightServo.setPosition(0.2);
    }
    
    public void moveLift(double power, long seconds){
        liftMotor.setPower(power);
        sleep(seconds * 1000);
        liftMotor.setPower(0);
    }
    
    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}


