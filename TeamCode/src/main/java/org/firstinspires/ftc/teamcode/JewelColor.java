package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

@Autonomous(name = "JewelColor")
public class JewelColor{
    private LynxI2cColorRangeSensor colorSensor;

    private char jewelColor;
    
    public JewelColor(LynxI2cColorRangeSensor colorSensor){
        this.colorSensor = colorSensor;
    }

    public char getColor(){
        float[] hsvValues = new float[3];
        final float values[] = hsvValues;
        int x = 2;

        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        int color = colors.toColor();

        float max = Math.max(Math.max(Math.max(colors.red, colors.green), colors.blue), colors.alpha);
        colors.red   /= max;
        colors.green /= max;
        colors.blue  /= max;

        color = colors.toColor();

        Color.colorToHSV(colors.toColor(), hsvValues);

        x = Math.round(hsvValues[0]);

        if(180 < x && x < 255){
            jewelColor = 'b';
            telemetry.addLine("Color: Blue");
        }else if(351 < x){
            jewelColor = 'r';
            telemetry.addLine("Color: Red");
        }else{
            jewelColor = 'n';
            telemetry.addLine("Color: Null");
        }

        return jewelColor;
    }
}
