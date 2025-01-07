// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkFlexConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Telescope extends SubsystemBase {

  private static Telescope instance = null;

  private SparkFlex motor;
  private SparkFlexConfig motorConfig;
  private SparkClosedLoopController closedLoopController;
  private RelativeEncoder encoder;
  private double tolerance = 0.5;

  /** Creates a new Telescope. */
  private Telescope() {
    motor = new SparkFlex(0, MotorType.kBrushless);
    closedLoopController = motor.getClosedLoopController();
    encoder = motor.getEncoder();

    motorConfig = new SparkFlexConfig();

    motorConfig.encoder.positionConversionFactor(1);

    motorConfig.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .p(0.4)
        .i(0)
        .d(0)
        .outputRange(-1, 1);

    motorConfig.closedLoop.maxMotion
        .maxVelocity(1000)
        .maxAcceleration(1000)
        .allowedClosedLoopError(1);

    motor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
  }

  public void resetPosition() {
    encoder.setPosition(0);
  }

  @Override
  public void periodic() {
  }

  public static Telescope getInstance() {
    if (instance == null) {
      instance = new Telescope();
    }
    return instance;
  }

  /**
   * @param point the specified location
   *              the function moves the telescope to the specified location
   */
  public void moveTelescope(double point) {
    closedLoopController.setReference(point, ControlType.kMAXMotionPositionControl,
        ClosedLoopSlot.kSlot0);
  }

  public void stopMotor() {
    motor.stopMotor();
  }

  public void setPower(double power) {
    motor.set(power);
  }

  /**
   * @param point the specified location
   * @return the function returns true if the telescope got to the specified
   *         location
   *         else return false
   */
  public boolean isInPoint(double point) {
    if (encoder.getPosition() <= (point + tolerance) && point <= (encoder.getPosition() + tolerance))
      return true;

    return false;
  }

}
