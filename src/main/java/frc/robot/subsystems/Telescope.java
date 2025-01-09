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
import frc.robot.Constants;

public class Telescope extends SubsystemBase 
{

  private static Telescope instance = null;

  private SparkFlex motor;
  private SparkFlexConfig motorConfig;
  private SparkClosedLoopController closedLoopController;
  private RelativeEncoder encoder;

  /** Creates a new Telescope. */
  private Telescope() 
  {
    motor = new SparkFlex(Constants.TELESCOPE_MOTOR_ID, MotorType.kBrushless);
    closedLoopController = motor.getClosedLoopController();
    encoder = motor.getEncoder();

    motorConfig = new SparkFlexConfig();

    motorConfig.encoder.positionConversionFactor(1);

    motorConfig.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .p(Constants.TELESCOPE_P)
        .i(Constants.TELESCOPE_I)
        .d(Constants.TELESCOPE_D)
        .outputRange(-1, 1);

    motorConfig.closedLoop.maxMotion
        .maxVelocity(Constants.TELESCOPE_MAX_VELO)
        .maxAcceleration(Constants.TELESCOPE_MAX_ACCELLERATION)
        .allowedClosedLoopError(1);

    motor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
  }

  public void resetPosition() 
  {
    encoder.setPosition(0);
  }

  @Override
  public void periodic() {}

  public static Telescope getInstance() 
  {
    if (instance == null) 
    {
      instance = new Telescope();
    }
    return instance;
  }

  /**
   *              moves the telescope to the specified location
   * @param point the specified location            
   */
  public void moveTelescope(double point)
  {
    closedLoopController.setReference(point, ControlType.kMAXMotionPositionControl,
        ClosedLoopSlot.kSlot0);
  }

  public void stopMotor() 
  {
    motor.stopMotor();
  }

  public void setPower(double power) 
  {
    motor.set(power);
  }

  /**
   *              Checks whether the telescope is approximately at the specified location.
   * @param point the specified location
   * @return True if the telescope is within the tolerance range of the specified location, 
   *         else false. 
   */
  public boolean isInPoint(double point) 
  {
    return ((encoder.getPosition() - point) <= Constants.TELESCOPE_POSITION_TOLERANCE);
  }

}
