// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Pivot;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class PivotMoveToAngle extends Command {
  public Pivot myPivot;
  private double myAngle;

  public PivotMoveToAngle(double myAngle)
  {
    this.myAngle = myAngle;
    myPivot = Pivot.GetInstance();
    addRequirements(myPivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    myPivot.movePivotToAngle(myAngle);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    myPivot.StopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return myPivot.isAtAngle(myAngle);
  }
}
