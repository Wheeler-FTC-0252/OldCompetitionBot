package org.wheeler.robotics.OldCompetitionBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by lucien on 8/28/15.
 */
public class OldCompetitionBotDrive extends OpMode {
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    DcMotor armMotor;
    double armGain=0.25;
    Servo grabServo;
    Servo doorServo;
    Servo leftSpinServo;
    Servo rightSpinServo;
    TouchSensor armTouch;
    boolean buttonA;
    boolean oldButtonA=false;
    double armSpeed;
    double leftTrigger;
    double rightTrigger;
    double[] doorPositions = {0 , 0.6};
    double[] grabPositions = {0 ,0.6};
    double leftX;
    double leftY;
    double rightX;
    double rightY;
    double leftY2;
    boolean dpadUp;
    boolean dpadDown;
    boolean dpadLeft;
    boolean dpadRight;
    DcMotorController extraController;

    public void init(){
        frontLeftMotor=hardwareMap.dcMotor.get("fLeft");
        frontRightMotor=hardwareMap.dcMotor.get("fRight");
        backLeftMotor=hardwareMap.dcMotor.get("bLeft");
        backRightMotor=hardwareMap.dcMotor.get("bRight");
        armMotor=hardwareMap.dcMotor.get("arm");
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        grabServo=hardwareMap.servo.get("grab");
        grabServo.setDirection(Servo.Direction.REVERSE);
        doorServo=hardwareMap.servo.get("door");
        leftSpinServo=hardwareMap.servo.get("leftSpin");
        leftSpinServo.setDirection(Servo.Direction.REVERSE);
        rightSpinServo=hardwareMap.servo.get("rightSpin");
        doorServo.setPosition(doorPositions[0]);
        grabServo.setPosition(grabPositions[1]);
        extraController=hardwareMap.dcMotorController.get("extra");
        extraController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_WRITE);
        armTouch=hardwareMap.touchSensor.get("armTouch");
    }

    public void loop(){
        leftX=gamepad1.left_stick_x;
        leftY=gamepad1.left_stick_y;
        rightX=gamepad1.right_stick_x;
        rightY=gamepad1.right_stick_y;
        leftTrigger=gamepad2.left_trigger;
        rightTrigger=gamepad2.right_trigger;
        dpadUp=gamepad1.dpad_up;
        dpadDown=gamepad1.dpad_down;
        dpadLeft=gamepad1.dpad_left;
        dpadRight=gamepad1.dpad_right;
        buttonA=gamepad2.a;
        leftY2=gamepad2.left_stick_y;
        frontLeftMotor.setPower(leftY);
        backLeftMotor.setPower(leftY);
        frontRightMotor.setPower(rightY);
        backRightMotor.setPower(rightY);
        telemetry.addData("Grab Servo: ", grabServo.getPosition());
        telemetry.addData("Door Servo: ", doorServo.getPosition());
        //telemetry.addData("arm pos: ", armMotor.getCurrentPosition());
        leftSpinServo.setPosition(0.5+(leftTrigger/2));
        rightSpinServo.setPosition(0.5+(rightTrigger/2));

        if (buttonA && oldButtonA!=buttonA){
            if (doorServo.getPosition()==doorPositions[0]){
                doorServo.setPosition(doorPositions[1]);
            }
            else{
                doorServo.setPosition(doorPositions[0]);
            }
        }
        oldButtonA=buttonA;

        if (dpadUp){
            grabServo.setPosition(grabPositions[1]);
        }
        else if (dpadDown){
            grabServo.setPosition(grabPositions[0]);
        }

        armSpeed=leftY2*armGain;
        telemetry.addData("speed: ", armSpeed);
        telemetry.addData("touch: ", armTouch.isPressed());
        telemetry.addData("armEncoder: ", armMotor.getCurrentPosition());
        if (armTouch.isPressed() && armSpeed>0) armSpeed=0;
        armMotor.setPower(armSpeed);

    }
}
