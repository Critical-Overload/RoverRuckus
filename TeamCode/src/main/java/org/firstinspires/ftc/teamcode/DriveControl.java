package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/*
Our Main Driving/Control Class
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
    //Drive Forward or Backward in Inches
    public void drive(double power, double inches) {
        power = power*0.5;
        motorBackLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(power);
        motorBackRight.setPower(power);
        double w = (inches/23)*1000;
        int y = (int) Math.rint(w);
        String x = Integer.toString(y);
        //telemetry.addLine((power > 0 ? "Forward" : "Backward") + x + "Inches");
        //telemetry.update();
        sleep(y);
        completeStop();
    }
    // Four Wheel Turn Counter Clockwise in Degrees
    public void fourWheelTurn(double power, double degrees) {
        //CounterClockwise
        motorBackLeft.setPower(-power);
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(-power);
        motorBackRight.setPower(power);
        double a = power;
        double w = (degrees/180)*1000;
        int y = (int) Math.rint(w);
        String x = Integer.toString(y);
        //telemetry.addLine((power > 0 ? "Counterclockwise" : "Clockwise") + x + "Degrees");
        //telemetry.update();
        sleep(y);
        completeStop();
    }
    //Drive Forward or Backward in Seconds
    public void driveTime(double power, long seconds){
        motorBackLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(power);
        motorBackRight.setPower(power);
        long y = (int) Math.rint(seconds*1000);
        //telemetry.addLine((power > 0 ? "Forwards for" : "Backwards for") + x + "seconds");
        //telemetry.update();
        sleep(y);
        completeStop();
    }
    //Stop
    public void completeStop(){
        motorBackLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
    }
    //Wait Seconds
    public void waitFor(long seconds){
        sleep(seconds * 1000);
    }
     
    public void closeClaw(){
        leftServo.setPosition(1);
        rightServo.setPosition(0);
        //telemetry.addLine("Claw closed");
        //telemetry.update();
    }

    public void openClaw(){
        leftServo.setPosition(0.5);
        rightServo.setPosition(0.2);
        //telemetry.addLine("Claw opened");
        //telemetry.update();
    }
    
    public void moveLift(double power, double seconds){
        liftMotor.setPower(power);
        long y = (int) Math.rint(seconds*1000);
        //telemetry.addLine((power > 0 ? "Lift moves down for" : "Lift moves up for") + x + "seconds");
        //telemetry.update();
        sleep(y);
        liftMotor.setPower(0);
    }
    //Wait milliseconds
    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}


