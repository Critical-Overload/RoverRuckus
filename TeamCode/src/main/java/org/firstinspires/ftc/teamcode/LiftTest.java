package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "LiftTest")
public class SemiAutonomousDrive extends LinearOpMode {
  
  private DcMotor liftMotor;
  
  private Servo clawServo;
  
  private static final double CLAW_RETRACTED = 0;
  private static final double CLAW_EXTENDED = 1;
  
  @Override
  
  public void runOpMode() throws Interrupted Exception {
  
    liftMotor = hardwareMap.dcMotor.get("");
    clawServo = hardwareMap.servo.get("");
    
    waitForStart();
    
    while(opModeIsActive()){
    
      if(gamepad2.x){
        clawServo.setPosition(CLAW_RETRACTED);
      }
      
      if(gamepad2.y){
        clawServo.setPosition(CLAW_EXTENDED);
      }
      
      /* 
      I think we have two options. We could do this:
      (continuous)
      (more flexible, less accurate)
      
      while(gamepad2.dpad_up){
        liftMotor.setPower(-0.5);
      }
      
      while(gamepad2.dpad_down){
        liftMotor.setPower(0.5);
      }
      */
      
      /* 
      Or this:
      (moves to set positions)
      (less flexible, more accurate, needs more testing)
     
      if(gamepad2.dpad_up){
        liftMotor.setPower(-0.5);
        sleep(1000)
      }
      
      if(gamepad2.dpad_down){
        liftMotor.setPower(0.5);
        sleep(1000)
      }
      */
      
      idle();
    }
  }
}
