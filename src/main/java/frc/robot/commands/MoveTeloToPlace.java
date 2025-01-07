// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Telescope;

public class MoveTeloToPlace extends Command 
{
  private static Telescope telescope;
  private double point; 

  public MoveTeloToPlace() 
  {
    telescope = Telescope.getInstance();
    addRequirements(telescope);
  }

  @Override
  public void initialize() 
  {
    telescope.moveTelescope(point);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) 
  {
    telescope.stopMotor();
  }

  @Override
  public boolean isFinished() 
  {
    return telescope.isInPoint(point);
  }
}
