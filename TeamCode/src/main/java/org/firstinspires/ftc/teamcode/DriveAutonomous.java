package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


public class DriveAutonomous{

    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;
    private DcMotor liftMotor;
    private DcMotor bucketMotor;
    private DigitalChannel touchSensor;
    
    public DriveAutonomous (DcMotor motorFrontRight, DcMotor motorFrontLeft, 
    DcMotor motorBackRight, DcMotor motorBackLeft, DcMotor liftMotor, DcMotor 
    bucketMotor, DigitalChannel touchSensor){
        this.motorFrontRight = motorFrontRight;
        this.motorFrontLeft = motorFrontLeft;
        this.motorBackRight = motorBackRight;
        this.motorBackLeft = motorBackLeft;
        this.liftMotor = liftMotor;
        this.bucketMotor = bucketMotor;
        this.touchSensor = touchSensor;
    }
    
    public void reverseMotor(DcMotor motor){
        motor.setDirection(DcMotor.Direction.REVERSE);
    }
    
    public void moveTime(double power, double seconds) throws InterruptedException{
        //Parameters: power to run at, seconds to run
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(power);
        motorBackRight.setPower(power);
        motorBackLeft.setPower(power);
        
        //uses seconds param to calculate milliseconds
        long y = (int) Math.rint(seconds * 1000);
        
        //waits for y milliseconds
        Thread.sleep(y);
        
        completeStop();
    }
    public void moveSideTime(double power, double seconds) throws InterruptedException{
        //Parameters: power (positive for left, negative for right), seconds
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(-power);
        motorBackRight.setPower(-power);
        motorBackLeft.setPower(power);
        
        //uses seconds param to calculate milliseconds
        long y = (int) Math.rint(seconds * 1000);
        
        //waits for y milliseconds
        Thread.sleep(y);
        
        completeStop();
    }
    public void turnTime(double power, double seconds) throws InterruptedException{
        //Parameters: power (positive for clockwise,
        // negative for counterclockwise), seconds
        motorFrontLeft.setPower(power);
        motorBackLeft.setPower(power);
        motorFrontRight.setPower(-power);
        motorBackRight.setPower(-power);
        
        long y = (int) Math.rint(seconds * 1000);
        Thread.sleep(y);
        
        completeStop();
    }
    public void land() throws InterruptedException{
        //lands the robot
        liftMotor.setPower(-0.5);
        
        Thread.sleep(3000);
        
        completeStop();
    }
    public void resetBucket(){
        //resets the bucket
        //moves bucket back until it hits touch sensor
        while(touchSensor.getState() == true){
            bucketMotor.setPower(-0.1);
        }
    }
    public void completeStop(){
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);
        liftMotor.setPower(0);
        bucketMotor.setPower(0);
        //completely stops all motors
    }
    
    
}
