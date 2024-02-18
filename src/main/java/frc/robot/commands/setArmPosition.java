// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public class setArmPosition extends Command {
  /** Creates a new setArmPosition. */

  private final Arm m_arm;
  private double position;
  private double initalHeight;
  private boolean isDone = false;

  public setArmPosition(Arm m_arm, double position) {

    this.m_arm = m_arm;
    this.position = position; // Position is the Pot Value

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.isDone = false;
    this.initalHeight = m_arm.getPotValue();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    




  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
