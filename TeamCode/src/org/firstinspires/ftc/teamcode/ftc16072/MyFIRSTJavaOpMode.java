package org.firstinspires.ftc.teamcode.ftc16072;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class MyFIRSTJavaOpMode extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private Servo servoTest;


    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "front_left_motor");
        frontRight = hardwareMap.get(DcMotor.class, "front_right_motor");
        backLeft = hardwareMap.get(DcMotor.class, "back_left_motor");
        backRight = hardwareMap.get(DcMotor.class, "back_right_motor");
        servoTest = hardwareMap.get(Servo.class, "back_servo");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        double rightX = 0;
        double leftY = 0;
        double leftX = 0;

        while (opModeIsActive()) {
            //for Dc motor
            leftX = this.gamepad1.left_stick_x;
            rightX = -this.gamepad1.right_stick_x;
            leftY = -this.gamepad1.left_stick_y;
            frontLeft.setPower(rightX - leftY - leftX);
            backRight.setPower(rightX + leftY + leftX);
            frontRight.setPower(rightX + leftY - leftX);
            backLeft.setPower(rightX - leftY + leftX);

            //check to see if we have to move the servo motor
            if (gamepad1.y) {
                servoTest.setPosition(0);
            } else if (gamepad1.x || gamepad1.b) {
                servoTest.setPosition(0.5);
            } else if (gamepad1.a) {
                servoTest.setPosition(1);
            }
            telemetry.addData("Servo Position", servoTest.getPosition());
            telemetry.addData("Front Left Motor Power", frontLeft.getPower());
            telemetry.addData("Front Right Motor Power", frontRight.getPower());
            telemetry.addData("Back Left Motor Power", backLeft.getPower());
            telemetry.addData("Back Right Motor Power", backRight.getPower());
            telemetry.addData("left_stick_y", gamepad1.left_stick_y);
            telemetry.addData("right_stick_x", gamepad1.right_stick_x);

            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
